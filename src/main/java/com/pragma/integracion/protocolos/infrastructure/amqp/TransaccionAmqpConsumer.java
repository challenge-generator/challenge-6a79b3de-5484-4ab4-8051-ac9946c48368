package com.pragma.integracion.protocolos.infrastructure.amqp;


import com.pragma.integracion.protocolos.domain.EstadoTransaccion;
import com.pragma.integracion.protocolos.domain.Transaccion;
import com.pragma.integracion.protocolos.domain.TransaccionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TransaccionAmqpConsumer {

    private static final Logger log = LoggerFactory.getLogger(TransaccionAmqpConsumer.class);
    private final Map<String, Transaccion> transaccionesProcesadas = new ConcurrentHashMap<>();
    private final Map<String, AtomicInteger> reintentosPorId = new ConcurrentHashMap<>();
    private static final int MAX_REINTENTOS = 3;
    private static final String DLX_NAME = "transaccion.dlx";
    private static final String DLQ_NAME = "transaccion.dlq";

    private final RabbitTemplate rabbitTemplate;

    public TransaccionAmqpConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "transaccion.queue")
    public void consumirTransaccion(TransaccionDto dto) {
        log.info("Consumiendo transacción: {}", dto);
        Transaccion transaccion = dto.toDomain();
        try {
            if (!verificarIdempotencia(transaccion)) {
                log.warn("Transacción duplicada ignorada: {}", transaccion.getIdempotencyKey());
                return;
            }
            Transaccion procesada = procesarTransaccion(transaccion);
            transaccionesProcesadas.put(procesada.getId().toString(), procesada);
            log.info("Transacción procesada exitosamente: {}", procesada.getId());
        } catch (Exception e) {
            log.error("Error al procesar transacción {}: {}", transaccion.getId(), e.getMessage());
            manejarError(transaccion, e);
        }
    }

    private boolean verificarIdempotencia(Transaccion transaccion) {
        String idempotencyKey = transaccion.getIdempotencyKey();
        if (idempotencyKey == null || idempotencyKey.isEmpty()) {
            return true;
        }
        if (transaccionesProcesadas.containsKey(idempotencyKey)) {
            return false;
        }
        return true;
    }

    private Transaccion procesarTransaccion(Transaccion transaccion) {
        if (transaccion.getMonto().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Monto inválido: " + transaccion.getMonto());
        }
        if (transaccion.getCuentaOrigen() == null || transaccion.getCuentaDestino() == null) {
            throw new IllegalArgumentException("Cuentas requeridas");
        }
        return transaccion.withEstado(Transaccion.EstadoTransaccion.PROCESADA);
    }

    private void manejarError(Transaccion transaccion, Exception e) {
        String transaccionId = transaccion.getId().toString();
        AtomicInteger reintentos = reintentosPorId.computeIfAbsent(transaccionId, k -> new AtomicInteger(0));
        int intentos = reintentos.incrementAndGet();
        if (intentos < MAX_REINTENTOS) {
            log.info("Reintentando transacción {} (intento {}/{})", transaccionId, intentos, MAX_REINTENTOS);
            try {
                Thread.sleep(1000L * intentos);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
            TransaccionDto dto = TransaccionDto.fromDomain(transaccion);
            rabbitTemplate.convertAndSend("transaccion.exchange", "transaccion.routing.key", dto);
        } else {
            log.error("Transacción {} enviada a DLQ después de {} intentos", transaccionId, MAX_REINTENTOS);
            enviarADLQ(transaccion, e.getMessage());
            reintentosPorId.remove(transaccionId);
        }
    }

    private void enviarADLQ(Transaccion transaccion, String razon) {
        TransaccionDto dto = TransaccionDto.fromDomain(transaccion);
        rabbitTemplate.convertAndSend(DLX_NAME, DLQ_NAME, dto, message -> {
            message.getMessageProperties().setHeader("x-dlq-razon", razon);
            message.getMessageProperties().setHeader("x-original-id", transaccion.getId().toString());
            return message;
        });
    }

    @RabbitListener(queues = "transaccion.dlq")
    public void consumirDLQ(TransaccionDto dto) {
        log.warn("Mensaje recibido de DLQ: {}", dto);
    }

    public int obtenerTotalProcesadas() {
        return transaccionesProcesadas.size();
    }

    public Transaccion obtenerTransaccion(String id) {
        return transaccionesProcesadas.get(id);
    }
}