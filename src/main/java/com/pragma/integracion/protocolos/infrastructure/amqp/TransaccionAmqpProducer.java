package com.pragma.integracion.protocolos.infrastructure.amqp;

import com.pragma.integracion.protocolos.domain.Transaccion;
import com.pragma.integracion.protocolos.domain.TransaccionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
public class TransaccionAmqpProducer {

    private static final Logger log = LoggerFactory.getLogger(TransaccionAmqpProducer.class);
    private static final String EXCHANGE_NAME = "transaccion.exchange";
    private static final String ROUTING_KEY = "transaccion.routing.key";
    private static final String DLQ_NAME = "transaccion.dlq";
    private static final String DLX_NAME = "transaccion.dlx";

    private final RabbitTemplate rabbitTemplate;

    public TransaccionAmqpProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        configurarInfraestructura();
    }

    private void configurarInfraestructura() {
        DirectExchange exchange = ExchangeBuilder.directExchange(EXCHANGE_NAME)
                .durable(true)
                .build();
        Queue queue = QueueBuilder.durable("transaccion.queue")
                .withArgument("x-dead-letter-exchange", DLX_NAME)
                .withArgument("x-dead-letter-routing-key", DLQ_NAME)
                .build();
        DirectExchange dlxExchange = ExchangeBuilder.directExchange(DLX_NAME)
                .durable(true)
                .build();
        Queue dlq = QueueBuilder.durable(DLQ_NAME).build();
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
        Binding dlqBinding = BindingBuilder.bind(dlq).to(dlxExchange).with(DLQ_NAME);
    }

    public void enviarTransaccion(Transaccion transaccion) {
        TransaccionDto dto = TransaccionDto.fromDomain(transaccion);
        String correlationId = UUID.randomUUID().toString();
        CorrelationData correlationData = new CorrelationData(correlationId);
        correlationData.getFuture().whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Error al enviar transacción {}: {}", correlationId, ex.getMessage());
            } else {
                log.info("Transacción {} confirmada por RabbitMQ", correlationId);
            }
        });
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, dto, correlationData);
        log.info("Transacción enviada a la cola: id={}, correlationId={}", transaccion.getId(), correlationId);
    }

    public void enviarTransaccionConRetraso(Transaccion transaccion, long retrasoMs) {
        TransaccionDto dto = TransaccionDto.fromDomain(transaccion);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, dto, message -> {
            message.getMessageProperties().setExpiration(String.valueOf(retrasoMs));
            return message;
        });
        log.info("Transacción enviada con retraso de {}ms: {}", retrasoMs, transaccion.getId());
    }

    public void enviarTransaccionConPrioridad(Transaccion transaccion, int prioridad) {
        TransaccionDto dto = TransaccionDto.fromDomain(transaccion);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, dto, message -> {
            message.getMessageProperties().setPriority((long) prioridad);
            return message;
        });
        log.info("Transacción enviada con prioridad {}: {}", prioridad, transaccion.getId());
    }

    public CompletableFuture<Boolean> enviarTransaccionAsync(Transaccion transaccion) {
        TransaccionDto dto = TransaccionDto.fromDomain(transaccion);
        String correlationId = UUID.randomUUID().toString();
        CorrelationData correlationData = new CorrelationData(correlationId);
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        correlationData.getFuture().whenComplete((result, ex) -> {
            if (ex != null) {
                future.complete(false);
            } else {
                future.complete(true);
            }
        });
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, dto, correlationData);
        return future;
    }

    public void enviarADLQ(Transaccion transaccion, String razon) {
        log.warn("Enviando transacción {} a DLQ por: {}", transaccion.getId(), razon);
        TransaccionDto dto = TransaccionDto.fromDomain(transaccion);
        rabbitTemplate.convertAndSend(DLX_NAME, DLQ_NAME, dto, message -> {
            message.getMessageProperties().setHeader("x-dlq-razon", razon);
            message.getMessageProperties().setHeader("x-original-id", transaccion.getId().toString());
            return message;
        });
    }
}