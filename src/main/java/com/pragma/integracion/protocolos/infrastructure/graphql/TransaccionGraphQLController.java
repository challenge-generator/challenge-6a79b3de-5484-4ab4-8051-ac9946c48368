package com.pragma.integracion.protocolos.infrastructure.graphql;


import com.pragma.integracion.protocolos.domain.EstadoTransaccion;
import com.pragma.integracion.protocolos.domain.Transaccion;
import com.pragma.integracion.protocolos.domain.TransaccionDto;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class TransaccionGraphQLController {

    private final Map<String, Transaccion> transaccionStore = new ConcurrentHashMap<>();

    public TransaccionGraphQLController() {
        inicializarDatosDemo();
    }

    private void inicializarDatosDemo() {
        Transaccion t1 = new Transaccion(
            UUID.randomUUID(),
            new BigDecimal("1500.00"),
            "CUENTA-001",
            "CUENTA-002",
            LocalDateTime.now().minusHours(2),
            Transaccion.EstadoTransaccion.COMPLETADA,
            "idem-001",
            "Pago de servicios"
        );
        Transaccion t2 = new Transaccion(
            UUID.randomUUID(),
            new BigDecimal("2300.50"),
            "CUENTA-003",
            "CUENTA-001",
            LocalDateTime.now().minusHours(5),
            Transaccion.EstadoTransaccion.PENDIENTE,
            "idem-002",
            "Transferencia diferida"
        );
        Transaccion t3 = new Transaccion(
            UUID.randomUUID(),
            new BigDecimal("500.00"),
            "CUENTA-002",
            "CUENTA-004",
            LocalDateTime.now().minusDays(1),
            Transaccion.EstadoTransaccion.FALLIDA,
            "idem-003",
            "Pago rechazado"
        );
        transaccionStore.put(t1.getId().toString(), t1);
        transaccionStore.put(t2.getId().toString(), t2);
        transaccionStore.put(t3.getId().toString(), t3);
    }

    @QueryMapping
    public Flux<TransaccionDto> transacciones() {
        return Flux.fromIterable(transaccionStore.values())
            .map(TransaccionDto::fromDomain);
    }

    @QueryMapping
    public Mono<TransaccionDto> transaccion(@Argument String id) {
        Transaccion transaccion = transaccionStore.get(id);
        if (transaccion == null) {
            return Mono.empty();
        }
        return Mono.just(TransaccionDto.fromDomain(transaccion));
    }

    @QueryMapping
    public Flux<TransaccionDto> transaccionesPorCuenta(@Argument String cuenta) {
        return Flux.fromIterable(transaccionStore.values())
            .filter(t -> t.getCuentaOrigen().equals(cuenta) || t.getCuentaDestino().equals(cuenta))
            .map(TransaccionDto::fromDomain);
    }

    @QueryMapping
    public Flux<TransaccionDto> transaccionesPorEstado(@Argument String estado) {
        try {
            Transaccion.EstadoTransaccion estadoEnum = Transaccion.EstadoTransaccion.valueOf(estado.toUpperCase());
            return Flux.fromIterable(transaccionStore.values())
                .filter(t -> t.getEstado() == estadoEnum)
                .map(TransaccionDto::fromDomain);
        } catch (IllegalArgumentException e) {
            return Flux.empty();
        }
    }

    @QueryMapping
    public Flux<TransaccionDto> transaccionesPorMontoRango(@Argument BigDecimal montoMin, @Argument BigDecimal montoMax) {
        return Flux.fromIterable(transaccionStore.values())
            .filter(t -> t.getMonto().compareTo(montoMin) >= 0 && t.getMonto().compareTo(montoMax) <= 0)
            .map(TransaccionDto::fromDomain);
    }

    @QueryMapping
    public Mono<Map<String, Object>> estadisticas() {
        var transacciones = transaccionStore.values();
        long total = transacciones.size();
        BigDecimal montoTotal = transacciones.stream()
            .map(Transaccion::getMonto)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        long completadas = transacciones.stream()
            .filter(t -> t.getEstado() == Transaccion.EstadoTransaccion.COMPLETADA)
            .count();
        long pendientes = transacciones.stream()
            .filter(t -> t.getEstado() == Transaccion.EstadoTransaccion.PENDIENTE)
            .count();
        long fallidas = transacciones.stream()
            .filter(t -> t.getEstado() == Transaccion.EstadoTransaccion.FALLIDA)
            .count();

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalTransacciones", total);
        stats.put("montoTotal", montoTotal);
        stats.put("completadas", completadas);
        stats.put("pendientes", pendientes);
        stats.put("fallidas", fallidas);
        stats.put("promedioMonto", total > 0 ? montoTotal.divide(BigDecimal.valueOf(total), 2, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO);

        return Mono.just(stats);
    }

    @MutationMapping
    public Mono<TransaccionDto> crearTransaccion(@Argument TransaccionInput input) {
        String idempotencyKey = input.idempotencyKey() != null ? input.idempotencyKey() : UUID.randomUUID().toString();
        
        boolean existe = transaccionStore.values().stream()
            .anyMatch(t -> t.getIdempotencyKey().equals(idempotencyKey));
        if (existe) {
            return Mono.error(new RuntimeException("Transacción duplicada: clave de idempotencia ya existe"));
        }

        Transaccion nueva = new Transaccion(
            UUID.randomUUID(),
            input.monto(),
            input.cuentaOrigen(),
            input.cuentaDestino(),
            LocalDateTime.now(),
            Transaccion.EstadoTransaccion.PENDIENTE,
            idempotencyKey,
            input.descripcion()
        );

        transaccionStore.put(nueva.getId().toString(), nueva);
        return Mono.just(TransaccionDto.fromDomain(nueva));
    }

    @MutationMapping
    public Mono<TransaccionDto> actualizarEstado(@Argument String id, @Argument String nuevoEstado) {
        Transaccion transaccion = transaccionStore.get(id);
        if (transaccion == null) {
            return Mono.empty();
        }

        try {
            Transaccion.EstadoTransaccion estadoEnum = Transaccion.EstadoTransaccion.valueOf(nuevoEstado.toUpperCase());
            Transaccion actualizada = transaccion.withEstado(estadoEnum);
            transaccionStore.put(id, actualizada);
            return Mono.just(TransaccionDto.fromDomain(actualizada));
        } catch (IllegalArgumentException e) {
            return Mono.error(new RuntimeException("Estado inválido: " + nuevoEstado));
        }
    }

    public record TransaccionInput(
        BigDecimal monto,
        String cuentaOrigen,
        String cuentaDestino,
        String descripcion,
        String idempotencyKey
    ) {}
}