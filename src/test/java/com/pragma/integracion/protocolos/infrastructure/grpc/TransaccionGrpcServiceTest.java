package com.pragma.integracion.protocolos.infrastructure.grpc;


import com.pragma.integracion.protocolos.infrastructure.rsocket.TransaccionRSocketController;
import com.pragma.integracion.protocolos.domain.Transaccion;
import com.pragma.integracion.protocolos.domain.Transaccion.EstadoTransaccion;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TransaccionGrpcServiceTest - Pruebas unitarias del servicio gRPC")
class TransaccionGrpcServiceTest {

    @Mock
    private StreamObserver<TransaccionProto.TransaccionResponse> responseObserver;

    private TransaccionGrpcService grpcService;

    @BeforeEach
    void setUp() {
        grpcService = new TransaccionGrpcService();
    }

    @Test
    @DisplayName("Debe procesar correctamente una transacción exitosa")
    void debeProcesarTransaccionExitosa() {
        TransaccionProto.TransaccionRequest request = TransaccionProto.TransaccionRequest.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setMonto("1500.50")
                .setCuentaOrigen("CUENTA_ORIGEN_001")
                .setCuentaDestino("CUENTA_DESTINO_001")
                .setIdempotencyKey("idem-001")
                .setDescripcion("Transferencia de prueba")
                .build();

        AtomicReference<TransaccionProto.TransaccionResponse> capturedResponse = new AtomicReference<>();
        doAnswer(invocation -> {
            capturedResponse.set(invocation.getArgument(0));
            return null;
        }).when(responseObserver).onNext(any(TransaccionProto.TransaccionResponse.class));

        grpcService.procesarTransaccion(request, responseObserver);

        verify(responseObserver).onNext(any(TransaccionProto.TransaccionResponse.class));
        verify(responseObserver).onCompleted();
        verify(responseObserver, never()).onError(any(Throwable.class));

        TransaccionProto.TransaccionResponse response = capturedResponse.get();
        assertNotNull(response);
        assertTrue(response.getExitosa());
    }

    @Test
    @DisplayName("Debe manejar error cuando la cuenta origen es inválida")
    void debeManejarErrorCuentaOrigenInvalida() {
        TransaccionProto.TransaccionRequest request = TransaccionProto.TransaccionRequest.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setMonto("100.00")
                .setCuentaOrigen("")
                .setCuentaDestino("CUENTA_VALIDA")
                .setIdempotencyKey("idem-002")
                .setDescripcion("Transferencia inválida")
                .build();

        grpcService.procesarTransaccion(request, responseObserver);

        ArgumentCaptor<TransaccionProto.TransaccionResponse> captor = ArgumentCaptor.forClass(TransaccionProto.TransaccionResponse.class);
        verify(responseObserver).onNext(captor.capture());

        TransaccionProto.TransaccionResponse response = captor.getValue();
        assertFalse(response.getExitosa());
        assertTrue(response.getMensajeError().contains("cuenta origen"));
        verify(responseObserver).onCompleted();
    }

    @Test
    @DisplayName("Debe rechazar monto negativo")
    void debeRechazarMontoNegativo() {
        TransaccionProto.TransaccionRequest request = TransaccionProto.TransaccionRequest.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setMonto("-500.00")
                .setCuentaOrigen("CUENTA_ORIGEN")
                .setCuentaDestino("CUENTA_DESTINO")
                .setIdempotencyKey("idem-003")
                .setDescripcion("Monto negativo")
                .build();

        grpcService.procesarTransaccion(request, responseObserver);

        ArgumentCaptor<TransaccionProto.TransaccionResponse> captor = ArgumentCaptor.forClass(TransaccionProto.TransaccionResponse.class);
        verify(responseObserver).onNext(captor.capture());

        TransaccionProto.TransaccionResponse response = captor.getValue();
        assertFalse(response.getExitosa());
    }

    @Test
    @DisplayName("Debe manejar excepción interna y propagar error por onError")
    void debeManejarExcepcionInterna() {
        TransaccionProto.TransaccionRequest request = TransaccionProto.TransaccionRequest.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setMonto("200.00")
                .setCuentaOrigen("CUENTA_ORIGEN")
                .setCuentaDestino("CUENTA_DESTINO")
                .setIdempotencyKey(null)
                .setDescripcion("Sin clave de idempotencia")
                .build();

        doThrow(new RuntimeException("Error de base de datos simulada"))
                .when(responseObserver).onNext(any(TransaccionProto.TransaccionResponse.class));

        grpcService.procesarTransaccion(request, responseObserver);

        verify(responseObserver).onError(any(Throwable.class));
    }

    @Test
    @DisplayName("Debe construir dominio Transaccion desde request gRPC")
    void debeConstruirDominioTransaccion() {
        UUID id = UUID.randomUUID();
        BigDecimal monto = new BigDecimal("2500.75");
        String cuentaOrigen = "CTA_ORIGEN_TEST";
        String cuentaDestino = "CTA_DESTINO_TEST";
        String idempotencyKey = "idem-key-123";
        String descripcion = "Test de construcción de dominio";

        Transaccion transaccion = new Transaccion(
                id,
                monto,
                cuentaOrigen,
                cuentaDestino,
                LocalDateTime.now(),
                EstadoTransaccion.PENDIENTE,
                idempotencyKey,
                descripcion
        );

        assertNotNull(transaccion);
        assertEquals(id, transaccion.getId());
        assertEquals(monto, transaccion.getMonto());
        assertEquals(cuentaOrigen, transaccion.getCuentaOrigen());
        assertEquals(cuentaDestino, transaccion.getCuentaDestino());
        assertEquals(EstadoTransaccion.PENDIENTE, transaccion.getEstado());
        assertEquals(idempotencyKey, transaccion.getIdempotencyKey());
        assertEquals(descripcion, transaccion.getDescripcion());
    }

    @Test
    @DisplayName("Debe validar que monto no sea nulo o vacío en request")
    void debeValidarMontoNoVacio() {
        TransaccionProto.TransaccionRequest request = TransaccionProto.TransaccionRequest.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setMonto("")
                .setCuentaOrigen("CUENTA_ORIGEN")
                .setCuentaDestino("CUENTA_DESTINO")
                .setIdempotencyKey("idem-004")
                .setDescripcion("Monto vacío")
                .build();

        grpcService.procesarTransaccion(request, responseObserver);

        ArgumentCaptor<TransaccionProto.TransaccionResponse> captor = ArgumentCaptor.forClass(TransaccionProto.TransaccionResponse.class);
        verify(responseObserver).onNext(captor.capture());

        TransaccionProto.TransaccionResponse response = captor.getValue();
        assertFalse(response.getExitosa());
    }
}// === ARCHIVO: src/test/java/com/pragma/integracion/protocolos/infrastructure/rsocket/TransaccionRSocketControllerTest.java ===
package com.pragma.integracion.protocolos.infrastructure.rsocket;

import com.pragma.integracion.protocolos.domain.Transaccion;
import com.pragma.integracion.protocolos.domain.Transaccion.EstadoTransaccion;
import com.pragma.integracion.protocolos.domain.TransaccionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("TransaccionRSocketControllerTest - Pruebas del controlador RSocket")
class TransaccionRSocketControllerTest {

    @Mock
    private TransaccionRSocketController transaccionRSocketController;

    private TransaccionDto transaccionDtoValida;
    private Transaccion transaccionDominio;

    @BeforeEach
    void setUp() {
        transaccionDominio = new Transaccion(
                UUID.randomUUID(),
                new BigDecimal("5000.00"),
                "CUENTA_ORIGEN_001",
                "CUENTA_DESTINO_001",
                LocalDateTime.now(),
                EstadoTransaccion.PROCESADA,
                "idem-key-001",
                "Transferencia RSocket exitosa"
        );
        transaccionDtoValida = TransaccionDto.fromDomain(transaccionDominio);
        transaccionRSocketController = new TransaccionRSocketController();
    }

    @Test
    @DisplayName("Debe procesar request-response exitosamente")
    void debeProcesarRequestResponseExitoso() {
        TransaccionDto requestDto = TransaccionDto.fromDomain(new Transaccion(
                UUID.randomUUID(),
                new BigDecimal("1000.00"),
                "CTA_ORIGEN",
                "CTA_DESTINO",
                LocalDateTime.now(),
                EstadoTransaccion.PENDIENTE,
                "idem-req-resp",
                "Request-Response test"
        ));

        Mono<TransaccionDto> resultado = transaccionRSocketController.procesarTransaccionRequestResponse(requestDto);

        StepVerifier.create(resultado)
                .expectNextMatches(dto -> dto.toDomain().getEstado().equals(EstadoTransaccion.PROCESADA))
                .verifyComplete();
    }

    @Test
    @DisplayName("Debe manejar error en request-response con monto inválido")
    void debeManejarErrorRequestResponseMontoInvalido() {
        TransaccionDto requestDto = TransaccionDto.fromDomain(new Transaccion(
                UUID.randomUUID(),
                BigDecimal.ZERO,
                "CTA_ORIGEN",
                "CTA_DESTINO",
                LocalDateTime.now(),
                EstadoTransaccion.PENDIENTE,
                "idem-error",
                "Monto inválido"
        ));

        Mono<TransaccionDto> resultado = transaccionRSocketController.procesarTransaccionRequestResponse(requestDto);

        StepVerifier.create(resultado)
                .expectErrorMatches(throwable -> throwable.getMessage().contains("monto"))
                .verify();
    }

    @Test
    @DisplayName("Debe ejecutar fire-and-forget correctamente")
    void debeEjecutarFireAndForget() {
        TransaccionDto requestDto = TransaccionDto.fromDomain(new Transaccion(
                UUID.randomUUID(),
                new BigDecimal("750.50"),
                "CTA_ORIGEN_FAF",
                "CTA_DESTINO_FAF",
                LocalDateTime.now(),
                EstadoTransaccion.PENDIENTE,
                "idem-faf",
                "Fire and Forget test"
        ));

        Mono<Void> resultado = transaccionRSocketController.procesarTransaccionFireAndForget(requestDto);

        StepVerifier.create(resultado)
                .verifyComplete();
    }

    @Test
    @DisplayName("Debe validar cuenta origen vacía en fire-and-forget")
    void debeValidarCuentaOrigenVaciaFireAndForget() {
        TransaccionDto requestDto = TransaccionDto.fromDomain(new Transaccion(
                UUID.randomUUID(),
                new BigDecimal("200.00"),
                "",
                "CTA_DESTINO",
                LocalDateTime.now(),
                EstadoTransaccion.PENDIENTE,
                "idem-cuenta-vacia",
                "Cuenta origen vacía"
        ));

        Mono<Void> resultado = transaccionRSocketController.procesarTransaccionFireAndForget(requestDto);

        StepVerifier.create(resultado)
                .expectErrorMatches(throwable -> throwable.getMessage().contains("cuenta origen"))
                .verify();
    }

    @Test
    @DisplayName("Debe convertir correctamente DTO a dominio y viceversa")
    void debeConvertirDtoDominioCorrectamente() {
        TransaccionDto dto = TransaccionDto.fromDomain(transaccionDominio);
        Transaccion dominio = dto.toDomain();

        assertEquals(transaccionDominio.getId(), dominio.getId());
        assertEquals(transaccionDominio.getMonto(), dominio.getMonto());
        assertEquals(transaccionDominio.getCuentaOrigen(), dominio.getCuentaOrigen());
        assertEquals(transaccionDominio.getCuentaDestino(), dominio.getCuentaDestino());
        assertEquals(transaccionDominio.getEstado(), dominio.getEstado());
        assertEquals(transaccionDominio.getIdempotencyKey(), dominio.getIdempotencyKey());
    }

    @Test
    @DisplayName("Debe rechazar transacción con clave de idempotencia duplicada")
    void debeRechazarIdempotenciaDuplicada() {
        String claveRepetida = "idem-duplicada-001";

        TransaccionDto request1 = TransaccionDto.fromDomain(new Transaccion(
                UUID.randomUUID(),
                new BigDecimal("300.00"),
                "CTA_ORIGEN",
                "CTA_DESTINO",
                LocalDateTime.now(),
                EstadoTransaccion.PENDIENTE,
                claveRepetida,
                "Primera transacción"
        ));

        TransaccionDto request2 = TransaccionDto.fromDomain(new Transaccion(
                UUID.randomUUID(),
                new BigDecimal("300.00"),
                "CTA_ORIGEN",
                "CTA_DESTINO",
                LocalDateTime.now(),
                EstadoTransaccion.PENDIENTE,
                claveRepetida,
                "Segunda transacción con misma clave"
        ));

        Mono<TransaccionDto> resultado1 = transaccionRSocketController.procesarTransaccionRequestResponse(request1);
        Mono<TransaccionDto> resultado2 = transaccionRSocketController.procesarTransaccionRequestResponse(request2);

        StepVerifier.create(resultado1)
                .expectNextMatches(dto -> dto.toDomain().getEstado().equals(EstadoTransaccion.PROCESADA))
                .verifyComplete();
    }
}