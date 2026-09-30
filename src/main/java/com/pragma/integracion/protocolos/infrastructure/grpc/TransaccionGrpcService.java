package com.pragma.integracion.protocolos.infrastructure.grpc;


import com.pragma.integracion.protocolos.domain.EstadoTransaccion;
import com.pragma.integracion.protocolos.domain.Transaccion;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@GrpcService
public class TransaccionGrpcService extends TransaccionServiceGrpc.TransaccionServiceImplBase {

    private final Map<UUID, Transaccion> transaccionStore = new ConcurrentHashMap<>();

    public TransaccionGrpcService() {
        inicializarDatosDemo();
    }

    private void inicializarDatosDemo() {
        Transaccion t1 = new Transaccion(
            UUID.randomUUID(),
            new BigDecimal("25000.75"),
            "GRPC-CUENTA-001",
            "GRPC-CUENTA-002",
            LocalDateTime.now().minusHours(3),
            Transaccion.EstadoTransaccion.COMPLETADA,
            UUID.randomUUID().toString(),
            "Pago de servicios vía gRPC"
        );
        Transaccion t2 = new Transaccion(
            UUID.randomUUID(),
            new BigDecimal("12000.00"),
            "GRPC-CUENTA-003",
            "GRPC-CUENTA-001",
            LocalDateTime.now().minusHours(1),
            Transaccion.EstadoTransaccion.PENDIENTE,
            UUID.randomUUID().toString(),
            "Transferencia pendiente"
        );
        transaccionStore.put(t1.getId(), t1);
        transaccionStore.put(t2.getId(), t2);
    }

    @Override
    public void crearTransaccion(TransaccionRequest request, StreamObserver<TransaccionResponse> responseObserver) {
        try {
            String idempotencyKey = request.getIdempotencyKey();
            if (idempotencyKey != null && !idempotencyKey.isBlank()) {
                Optional<Transaccion> existente = transaccionStore.values().stream()
                    .filter(t -> idempotencyKey.equals(t.getIdempotencyKey()))
                    .findFirst();
                if (existente.isPresent()) {
                    responseObserver.onNext(mapToResponse(existente.get()));
                    responseObserver.onCompleted();
                    return;
                }
            }

            Transaccion transaccion = new Transaccion(
                UUID.randomUUID(),
                new BigDecimal(request.getMonto()),
                request.getCuentaOrigen(),
                request.getCuentaDestino(),
                LocalDateTime.now(),
                mapEstado(request.getEstado()),
                idempotencyKey,
                request.getDescripcion()
            );

            transaccionStore.put(transaccion.getId(), transaccion);
            responseObserver.onNext(mapToResponse(transaccion));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error al crear transacción: " + e.getMessage())
                .asRuntimeException());
        }
    }

    @Override
    public void obtenerTransaccion(TransaccionByIdRequest request, StreamObserver<TransaccionResponse> responseObserver) {
        try {
            UUID id = UUID.fromString(request.getId());
            Transaccion transaccion = transaccionStore.get(id);
            if (transaccion == null) {
                responseObserver.onError(io.grpc.Status.NOT_FOUND
                    .withDescription("Transacción no encontrada: " + request.getId())
                    .asRuntimeException());
                return;
            }
            responseObserver.onNext(mapToResponse(transaccion));
            responseObserver.onCompleted();
        } catch (IllegalArgumentException e) {
            responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT
                .withDescription("ID inválido: " + request.getId())
                .asRuntimeException());
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error al obtener transacción: " + e.getMessage())
                .asRuntimeException());
        }
    }

    @Override
    public void listarTransacciones(ListarTransaccionesRequest request, StreamObserver<TransaccionResponse> responseObserver) {
        try {
            var stream = transaccionStore.values().stream();
            
            if (request.hasFiltroEstado() && !request.getFiltroEstado().isBlank()) {
                Transaccion.EstadoTransaccion estado = mapEstado(request.getFiltroEstado());
                stream = stream.filter(t -> t.getEstado() == estado);
            }
            
            if (request.hasFiltroCuenta() && !request.getFiltroCuenta().isBlank()) {
                String cuenta = request.getFiltroCuenta();
                stream = stream.filter(t -> t.getCuentaOrigen().equals(cuenta) || t.getCuentaDestino().equals(cuenta));
            }
            
            List<Transaccion> ordenadas = stream
                .sorted(Comparator.comparing(Transaccion::getTimestamp).reversed())
                .collect(Collectors.toList());
            
            for (Transaccion t : ordenadas) {
                responseObserver.onNext(mapToResponse(t));
            }
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error al listar transacciones: " + e.getMessage())
                .asRuntimeException());
        }
    }

    @Override
    public StreamObserver<TransaccionRequest> crearTransaccionesStreaming(StreamObserver<TransaccionResponse> responseObserver) {
        return new StreamObserver<TransaccionRequest>() {
            private final List<TransaccionResponse> respuestas = Collections.synchronizedList(new ArrayList<>());
            
            @Override
            public void onNext(TransaccionRequest request) {
                try {
                    Transaccion transaccion = new Transaccion(
                        UUID.randomUUID(),
                        new BigDecimal(request.getMonto()),
                        request.getCuentaOrigen(),
                        request.getCuentaDestino(),
                        LocalDateTime.now(),
                        mapEstado(request.getEstado()),
                        request.getIdempotencyKey(),
                        request.getDescripcion()
                    );
                    transaccionStore.put(transaccion.getId(), transaccion);
                    respuestas.add(mapToResponse(transaccion));
                } catch (Exception e) {
                    responseObserver.onError(io.grpc.Status.INTERNAL
                        .withDescription("Error en procesamiento streaming: " + e.getMessage())
                        .asRuntimeException());
                }
            }
            
            @Override
            public void onError(Throwable t) {
                responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription("Error en stream: " + t.getMessage())
                    .asRuntimeException());
            }
            
            @Override
            public void onCompleted() {
                for (TransaccionResponse r : respuestas) {
                    responseObserver.onNext(r);
                }
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void transaccionesPorCuenta(TransaccionPorCuentaRequest request, StreamObserver<TransaccionResponse> responseObserver) {
        try {
            String cuenta = request.getCuenta();
            transaccionStore.values().stream()
                .filter(t -> t.getCuentaOrigen().equals(cuenta) || t.getCuentaDestino().equals(cuenta))
                .sorted(Comparator.comparing(Transaccion::getTimestamp).reversed())
                .forEach(t -> responseObserver.onNext(mapToResponse(t)));
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                .withDescription("Error al filtrar por cuenta: " + e.getMessage())
                .asRuntimeException());
        }
    }

    private TransaccionResponse mapToResponse(Transaccion t) {
        return TransaccionResponse.newBuilder()
            .setId(t.getId().toString())
            .setMonto(t.getMonto().toString())
            .setCuentaOrigen(t.getCuentaOrigen())
            .setCuentaDestino(t.getCuentaDestino())
            .setTimestamp(t.getTimestamp().toString())
            .setEstado(t.getEstado().name())
            .setIdempotencyKey(t.getIdempotencyKey() != null ? t.getIdempotencyKey() : "")
            .setDescripcion(t.getDescripcion() != null ? t.getDescripcion() : "")
            .build();
    }

    private Transaccion.EstadoTransaccion mapEstado(String estado) {
        if (estado == null || estado.isBlank()) {
            return Transaccion.EstadoTransaccion.PENDIENTE;
        }
        try {
            return Transaccion.EstadoTransaccion.valueOf(estado.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Transaccion.EstadoTransaccion.PENDIENTE;
        }
    }
}