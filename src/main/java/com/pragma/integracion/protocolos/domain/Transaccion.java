package com.pragma.integracion.protocolos.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Transaccion {
    private final UUID id;
    private final BigDecimal monto;
    private final String cuentaOrigen;
    private final String cuentaDestino;
    private final LocalDateTime timestamp;
    private final EstadoTransaccion estado;
    private final String idempotencyKey;
    private final String descripcion;

    public enum EstadoTransaccion {
        PENDIENTE, PROCESADA, RECHAZADA, FALLIDA
    }

    public Transaccion(UUID id, BigDecimal monto, String cuentaOrigen, String cuentaDestino,
                      LocalDateTime timestamp, EstadoTransaccion estado, String idempotencyKey,
                      String descripcion) {
        this.id = Objects.requireNonNull(id, "ID no puede ser nulo");
        this.monto = Objects.requireNonNull(monto, "Monto no puede ser nulo");
        this.cuentaOrigen = Objects.requireNonNull(cuentaOrigen, "Cuenta origen no puede ser nula");
        this.cuentaDestino = Objects.requireNonNull(cuentaDestino, "Cuenta destino no puede ser nula");
        this.timestamp = Objects.requireNonNull(timestamp, "Timestamp no puede ser nulo");
        this.estado = Objects.requireNonNull(estado, "Estado no puede ser nulo");
        this.idempotencyKey = Objects.requireNonNull(idempotencyKey, "Clave de idempotencia no puede ser nula");
        this.descripcion = descripcion;
        
        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        if (cuentaOrigen.equals(cuentaDestino)) {
            throw new IllegalArgumentException("La cuenta origen y destino no pueden ser iguales");
        }
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public String getCuentaDestino() {
        return cuentaDestino;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public EstadoTransaccion getEstado() {
        return estado;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Transaccion withEstado(EstadoTransaccion nuevoEstado) {
        return new Transaccion(this.id, this.monto, this.cuentaOrigen, this.cuentaDestino,
                             this.timestamp, nuevoEstado, this.idempotencyKey, this.descripcion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaccion that = (Transaccion) o;
        return id.equals(that.id) && idempotencyKey.equals(that.idempotencyKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idempotencyKey);
    }

    @Override
    public String toString() {
        return "Transaccion{" +
                "id=" + id +
                ", monto=" + monto +
                ", cuentaOrigen='" + cuentaOrigen + '\'' +
                ", cuentaDestino='" + cuentaDestino + '\'' +
                ", timestamp=" + timestamp +
                ", estado=" + estado +
                ", idempotencyKey='" + idempotencyKey + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}