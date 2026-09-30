# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Comprensión y comparación de protocolos de comunicación avanzados**.

| | |
|---|---|
| Tema | Protocolos de comunicación no convencionales |
| Nivel | advanced-l2 |
| Chapter | Integración — Desarrollo |
| Especialidad | Api |
| Stack | Java / Spring Boot 3.5 |
| Patron arquitectonico | microservicio reactivo con patrones EIP (Enterprise Integration Patterns) |
| Tiempo estimado | 4 horas |

## Receta del stack

Esqueleto obligatorio:

- `openapi.yaml con paths, schemas y responses completos`
- `policies/ con las politicas del gateway (rate limit, auth, transformacion)`
- `environments/ con la configuracion por ambiente`
- `tests/ con la coleccion de contrato`
- `README.md con el contrato y los codigos de error`

Dependencias:

- org.springframework.boot:spring-boot-starter-webflux 3.5.6
- org.springframework.boot:spring-boot-starter-rsocket 3.5.6
- io.rsocket:rsocket-core 1.1.3
- io.grpc:grpc-protobuf 1.62.2
- io.grpc:grpc-stub 1.62.2
- io.grpc:grpc-netty 1.62.2
- net.devh:grpc-server-spring-boot-starter 2.15.0.RELEASE
- org.springframework.boot:spring-boot-starter-amqp 3.5.6
- org.springframework.graphql:spring-graphql 1.2.6
- io.projectreactor:reactor-core 3.6.3
- org.springframework.boot:spring-boot-starter-actuator 3.5.6
- org.springframework.boot:spring-boot-starter-test 3.5.6
- org.junit.jupiter:junit-jupiter-api 5.10.2
- io.projectreactor:reactor-test 3.6.3
- org.mockito:mockito-core 5.11.0
- org.apache.maven.plugins:maven-compiler-plugin 3.13.0
- org.xolstice.maven.plugins:protobuf-maven-plugin 0.6.1

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `npx --yes @redocly/cli lint openapi.yaml` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `npx --yes @redocly/cli lint openapi.yaml` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Exploración de RSocket y gRPC**: Documento comparativo que detalla las diferencias, ventajas y casos de uso de RSocket y gRPC.
- **Fase 2 — Análisis de AMQP y GraphQL**: Documento analítico que detalla el manejo de mensajes en AMQP y la comparación entre GraphQL y REST.
- **Fase 3 — Síntesis y aplicación práctica**: Documento de recomendaciones que detalla la elección de protocolos para diferentes escenarios de microservicios.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Lo que falta y tenes que completar

### 1. Archivos que la arquitectura declara (1 de 16)

La propuesta arquitectonica del reto los lista y no llegaron al repo. Crealos con implementacion real, respetando la capa en la que viven:

- [ ] `src/test/java/com/pragma/integracion/protocolos/infrastructure/rsocket/TransaccionRSocketControllerTest.java`

### 2. Referencias colgando (22)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/pragma/integracion/protocolos/ProtocolosApplication.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Hooks pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/integracion/protocolos/infrastructure/grpc/TransaccionGrpcService.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/integracion/protocolos/infrastructure/rsocket/TransaccionRSocketController.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/integracion/protocolos/infrastructure/rsocket/TransaccionRSocketController.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/integracion/protocolos/infrastructure/amqp/TransaccionAmqpProducer.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/integracion/protocolos/infrastructure/amqp/TransaccionAmqpConsumer.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/integracion/protocolos/infrastructure/graphql/TransaccionGraphQLController.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/integracion/protocolos/config/ProtocolConfig.java` — `io.netty.channel`
      El import io.netty.channel.ChannelOption pertenece a io.netty.channel, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/integracion/protocolos/config/ProtocolConfig.java` — `io.netty.handler`
      El import io.netty.handler.timeout.ReadTimeoutHandler pertenece a io.netty.handler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/integracion/protocolos/config/ProtocolConfig.java` — `reactor.netty.http`
      El import reactor.netty.http.client.HttpClient pertenece a reactor.netty.http, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/integracion/protocolos/config/ProtocolConfig.java` — `reactor.netty.resources`
      El import reactor.netty.resources.ConnectionProvider pertenece a reactor.netty.resources, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/pragma/integracion/protocolos/infrastructure/grpc/TransaccionGrpcServiceTest.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/integracion/protocolos/infrastructure/rest/TransaccionController.java` — `Transaccion.isPresent`
      Se invoca `isPresent` sobre `Transaccion`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/integracion/protocolos/infrastructure/rest/TransaccionController.java` — `Transaccion.get`
      Se invoca `get` sobre `Transaccion`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/integracion/protocolos/infrastructure/rest/TransaccionController.java` — `Transaccion.isEmpty`
      Se invoca `isEmpty` sobre `Transaccion`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/integracion/protocolos/infrastructure/grpc/TransaccionGrpcService.java` — `Transaccion.getMessage`
      Se invoca `getMessage` sobre `Transaccion`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/integracion/protocolos/infrastructure/graphql/TransaccionGraphQLController.java` — `TransaccionInput.idempotencyKey`
      Se invoca `idempotencyKey` sobre `TransaccionInput`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/integracion/protocolos/infrastructure/graphql/TransaccionGraphQLController.java` — `TransaccionInput.monto`
      Se invoca `monto` sobre `TransaccionInput`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/integracion/protocolos/infrastructure/graphql/TransaccionGraphQLController.java` — `TransaccionInput.cuentaOrigen`
      Se invoca `cuentaOrigen` sobre `TransaccionInput`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/integracion/protocolos/infrastructure/graphql/TransaccionGraphQLController.java` — `TransaccionInput.cuentaDestino`
      Se invoca `cuentaDestino` sobre `TransaccionInput`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/integracion/protocolos/infrastructure/graphql/TransaccionGraphQLController.java` — `TransaccionInput.descripcion`
      Se invoca `descripcion` sobre `TransaccionInput`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/integracion/protocolos/infrastructure/grpc/TransaccionGrpcServiceTest.java` — `TransaccionGrpcService.procesarTransaccion`
      Se invoca `procesarTransaccion` sobre `TransaccionGrpcService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (17)

- `pom.xml`
- `src/main/java/com/pragma/integracion/protocolos/ProtocolosApplication.java`
- `src/main/resources/application.yml`
- `src/main/java/com/pragma/integracion/protocolos/domain/Transaccion.java`
- `src/main/java/com/pragma/integracion/protocolos/domain/TransaccionDto.java`
- `openapi.yaml`
- `README.md`
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/rest/TransaccionController.java`
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/grpc/TransaccionGrpcService.java`
- `src/main/proto/transaccion.proto`
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/rsocket/TransaccionRSocketController.java`
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/amqp/TransaccionAmqpProducer.java`
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/amqp/TransaccionAmqpConsumer.java`
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/graphql/TransaccionGraphQLController.java`
- `src/main/resources/graphql/schema.graphqls`
- `src/main/java/com/pragma/integracion/protocolos/config/ProtocolConfig.java`
- `src/test/java/com/pragma/integracion/protocolos/infrastructure/grpc/TransaccionGrpcServiceTest.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/pragma/integracion/protocolos`
- `src/main/java/com/pragma/integracion/protocolos/config`
- `src/main/java/com/pragma/integracion/protocolos/domain`
- `src/main/java/com/pragma/integracion/protocolos/infrastructure`
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/grpc`
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/rsocket`
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/amqp`
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/graphql`
- `src/main/resources`
- `src/test/java/com/pragma/integracion/protocolos`

## Verificacion

```bash
npx --yes @redocly/cli lint openapi.yaml
```

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **microservicio reactivo con patrones EIP (Enterprise Integration Patterns)**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Integración, Especialidad Desarrollador, Tecnología API, Advanced
- Brecha que el reto ataca: Mostrar cómo RSocket se diferencia de HTTP/1, HTTP/2 y WebSocket. Aplicado el protocolo gRPC facilita la comunicación entre servicios en una arquitectura de microservicios. ¿Cómo se manejan los mensajes y la cola en AMQP? Explicar el rendimiento y flexibilidad al usar GraphQL en comparación con REST?
- Mision: Candidato con experiencia avanzada en APIs e integración de servicios

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
