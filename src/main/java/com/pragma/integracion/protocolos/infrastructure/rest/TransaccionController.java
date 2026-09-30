package com.pragma.integracion.protocolos.infrastructure.rest;


import com.pragma.integracion.protocolos.domain.EstadoTransaccion;
import com.pragma.integracion.protocolos.domain.Transaccion;
import com.pragma.integracion.protocolos.domain.TransaccionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/transacciones")
public class TransaccionController {

    private final Map<UUID, Transaccion> transaccionStore = new ConcurrentHashMap<>();

    public TransaccionController() {
        inicializarDatosDemo();
    }

    private void inicializarDatosDemo() {
        Transaccion t1 = new Transaccion(
            UUID.randomUUID(),
            new BigDecimal("15000.50"),
            "CUENTA-001",
            "CUENTA-002",
            LocalDateTime.now().minusHours(2),
            Transaccion.EstadoTransaccion.COMPLETADA,
            UUID.randomUUID().toString(),
            "Transferencia programada"
        );
        Transaccion t2 = new Transaccion(
            UUID.randomUUID(),
            new BigDecimal("8500.00"),
            "CUENTA-003",
            "CUENTA-001",
            LocalDateTime.now().minusHours(5),
            Transaccion.EstadoTransaccion.PENDIENTE,
            UUID.randomUUID().toString(),
            "Depósito pendiente de confirmación"
        );
        transaccionStore.put(t1.getId(), t1);
        transaccionStore.put(t2.getId(), t2);
    }

    @GetMapping
    public ResponseEntity<List<TransaccionDto>> listarTodas(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String cuentaOrigen) {
        
        var transacciones = transaccionStore.values().stream();
        
        if (estado != null && !estado.isBlank()) {
            try {
                Transaccion.EstadoTransaccion estadoEnum = Transaccion.EstadoTransaccion.valueOf(estado.toUpperCase());
                transacciones = transacciones.filter(t -> t.getEstado() == estadoEnum);
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado inválido: " + estado);
            }
        }
        
        if (cuentaOrigen != null && !cuentaOrigen.isBlank()) {
            transacciones = transacciones.filter(t -> t.getCuentaOrigen().equals(cuentaOrigen));
        }
        
        List<TransaccionDto> resultado = transacciones
            .sorted(Comparator.comparing(Transaccion::getTimestamp).reversed())
            .map(TransaccionDto::fromDomain)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransaccionDto> obtenerPorId(@PathVariable UUID id) {
        Transaccion transaccion = transaccionStore.get(id);
        if (transaccion == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transacción no encontrada: " + id);
        }
        return ResponseEntity.ok(TransaccionDto.fromDomain(transaccion));
    }

    @PostMapping
    public ResponseEntity<TransaccionDto> crear(@RequestBody TransaccionDto dto) {
        Transaccion transaccion = dto.toDomain();
        
        if (transaccion.getIdempotencyKey() != null) {
            Optional<Transaccion> existente = transaccionStore.values().stream()
                .filter(t -> transaccion.getIdempotencyKey().equals(t.getIdempotencyKey()))
                .findFirst();
            if (existente.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(TransaccionDto.fromDomain(existente.get()));
            }
        }
        
        Transaccion nueva = new Transaccion(
            UUID.randomUUID(),
            transaccion.getMonto(),
            transaccion.getCuentaOrigen(),
            transaccion.getCuentaDestino(),
            LocalDateTime.now(),
            Transaccion.EstadoTransaccion.PENDIENTE,
            transaccion.getIdempotencyKey(),
            transaccion.getDescripcion()
        );
        
        transaccionStore.put(nueva.getId(), nueva);
        return ResponseEntity.status(HttpStatus.CREATED).body(TransaccionDto.fromDomain(nueva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransaccionDto> actualizar(@PathVariable UUID id, @RequestBody TransaccionDto dto) {
        Transaccion existente = transaccionStore.get(id);
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transacción no encontrada: " + id);
        }
        
        Transaccion actualizada = dto.toDomain();
        Transaccion resultado = existente.withEstado(actualizada.getEstado());
        transaccionStore.put(id, resultado);
        
        return ResponseEntity.ok(TransaccionDto.fromDomain(resultado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        Transaccion eliminada = transaccionStore.remove(id);
        if (eliminada == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transacción no encontrada: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cuenta/{cuenta}")
    public ResponseEntity<List<TransaccionDto>> listarPorCuenta(@PathVariable String cuenta) {
        List<TransaccionDto> transacciones = transaccionStore.values().stream()
            .filter(t -> t.getCuentaOrigen().equals(cuenta) || t.getCuentaDestino().equals(cuenta))
            .sorted(Comparator.comparing(Transaccion::getTimestamp).reversed())
            .map(TransaccionDto::fromDomain)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(transacciones);
    }

    @GetMapping("/ idempotency/{key}")
    public ResponseEntity<TransaccionDto> buscarPorIdempotencyKey(@PathVariable String key) {
        Optional<Transaccion> transaccion = transaccionStore.values().stream()
            .filter(t -> key.equals(t.getIdempotencyKey()))
            .findFirst();
        
        if (transaccion.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No encontrada con key: " + key);
        }
        return ResponseEntity.ok(TransaccionDto.fromDomain(transaccion.get()));
    }
}