package com.pragma.integracion.protocolos.domain;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransaccionDto(
        @NotNull(message = "ID es obligatorio")
        UUID id,
        
        @NotNull(message = "Monto es obligatorio")
        @DecimalMin(value = "0.01", message = "El monto debe ser positivo")
        BigDecimal monto,
        
        @NotBlank(message = "Cuenta origen es obligatoria")
        @Pattern(regexp = "^[A-Za-z0-9]{8,20}$", message = "Formato de cuenta origen inválido")
        String cuentaOrigen,
        
        @NotBlank(message = "Cuenta destino es obligatoria")
        @Pattern(regexp = "^[A-Za-z0-9]{8,20}$", message = "Formato de cuenta destino inválido")
        String cuentaDestino,
        
        @NotNull(message = "Timestamp es obligatorio")
        LocalDateTime timestamp,
        
        String idempotencyKey,
        
        String descripcion) {

    public TransaccionDto {
        if (cuentaOrigen != null && cuentaDestino != null && cuentaOrigen.equals(cuentaDestino)) {
            throw new IllegalArgumentException("La cuenta origen y destino no pueden ser iguales");
        }
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            idempotencyKey = UUID.randomUUID().toString();
        }
    }

    public Transaccion toDomain() {
        return new Transaccion(
                this.id,
                this.monto,
                this.cuentaOrigen,
                this.cuentaDestino,
                this.timestamp != null ? this.timestamp : LocalDateTime.now(),
                Transaccion.EstadoTransaccion.PENDIENTE,
                this.idempotencyKey,
                this.descripcion
        );
    }

    public static TransaccionDto fromDomain(Transaccion transaccion) {
        return new TransaccionDto(
                transaccion.getId(),
                transaccion.getMonto(),
                transaccion.getCuentaOrigen(),
                transaccion.getCuentaDestino(),
                transaccion.getTimestamp(),
                transaccion.getIdempotencyKey(),
                transaccion.getDescripcion()
        );
    }
}