package com.pragma.integracion.protocolos.infrastructure.rsocket;


import com.pragma.integracion.protocolos.domain.EstadoTransaccion;
import com.pragma.integracion.protocolos.domain.Transaccion;
import com.pragma.integracion.protocolos.domain.TransaccionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class TransaccionRSocketController {

    private static final Logger log = LoggerFactory.getLogger(TransaccionRSocketController.class);
    private final Map<String, Transaccion> transacciones = new ConcurrentHashMap<>();
    private final Map<String, Transaccion> idempotencyStore = new ConcurrentHashMap<>();

    @MessageMapping("transaccion.request-response")
    public Mono<TransaccionDto> procesarTransaccionRequestResponse(@Payload TransaccionDto dto) {
        log.info("RSocket request-response: procesando transacción {}", dto);
        Transaccion transaccion = dto.toDomain();
        Transaccion procesada = transaccion.withEstado(Transaccion.EstadoTransaccion.PROCESADA);
        transacciones.put(procesada.getId().toString(), procesada);
        return Mono.just(TransaccionDto.fromDomain(procesada));
    }

    @MessageMapping("transaccion.fire-and-forget")
    public Mono<Void> procesarTransaccionFireAndForget(@Payload TransaccionDto dto) {
        log.info("RSocket fire-and-forget: recibiendo transacción {}", dto);
        Transaccion transaccion = dto.toDomain();
        if (transaccion.getIdempotencyKey() != null && idempotencyStore.containsKey(transaccion.getIdempotencyKey())) {
            log.warn("Transacción duplicada detectada por clave de idempotencia: {}", transaccion.getIdempotencyKey());
            return Mono.empty();
        }
        Transaccion procesada = transaccion.withEstado(Transaccion.EstadoTransaccion.EN_PROCESO);
        transacciones.put(procesada.getId().toString(), procesada);
        if (transaccion.getIdempotencyKey() != null) {
            idempotencyStore.put(transaccion.getIdempotencyKey(), procesada);
        }
        return Mono.empty();
    }

    @MessageMapping("transaccion.{cuentaId}")
    public Flux<TransaccionDto> obtenerTransaccionesPorCuenta(@DestinationVariable String cuentaId) {
        log.info("RSocket stream: consultando transacciones para cuenta {}", cuentaId);
        return Flux.fromIterable(transacciones.values())
                .filter(t -> t.getCuentaOrigen().equals(cuentaId) || t.getCuentaDestino().equals(cuentaId))
                .map(TransaccionDto::fromDomain);
    }

    @MessageMapping("transaccion.consulta.{id}")
    public Mono<TransaccionDto> consultarTransaccion(@DestinationVariable String id) {
        log.info("RSocket request-response: consultando transacción {}", id);
        Transaccion transaccion = transacciones.get(id);
        if (transaccion == null) {
            return Mono.empty();
        }
        return Mono.just(TransaccionDto.fromDomain(transaccion));
    }

    @MessageMapping("transaccion.error.simulado")
    public Mono<TransaccionDto> simularError(@Payload TransaccionDto dto) {
        log.error("RSocket: simulando error de procesamiento para {}", dto);
        return Mono.error(new RuntimeException("Error simulado en RSocket"));
    }

    public Transaccion crearTransaccionPrueba(BigDecimal monto, String origen, String destino) {
        return new Transaccion(
                UUID.randomUUID(),
                monto,
                origen,
                destino,
                LocalDateTime.now(),
                Transaccion.EstadoTransaccion.PENDIENTE,
                UUID.randomUUID().toString(),
                "Transacción de prueba RSocket"
        );
    }
}