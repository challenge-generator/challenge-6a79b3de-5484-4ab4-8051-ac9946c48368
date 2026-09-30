# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Archivos que la arquitectura del reto declara y no estan

Creálos con implementacion real, en la capa que les corresponde:

- `src/test/java/com/pragma/integracion/protocolos/infrastructure/rsocket/TransaccionRSocketControllerTest.java`

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/pragma/integracion/protocolos/ProtocolosApplication.java` — `reactor.core.publisher`: El import reactor.core.publisher.Hooks pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/grpc/TransaccionGrpcService.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/rsocket/TransaccionRSocketController.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/rsocket/TransaccionRSocketController.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/amqp/TransaccionAmqpProducer.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/amqp/TransaccionAmqpConsumer.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/graphql/TransaccionGraphQLController.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/integracion/protocolos/config/ProtocolConfig.java` — `io.netty.channel`: El import io.netty.channel.ChannelOption pertenece a io.netty.channel, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/integracion/protocolos/config/ProtocolConfig.java` — `io.netty.handler`: El import io.netty.handler.timeout.ReadTimeoutHandler pertenece a io.netty.handler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/integracion/protocolos/config/ProtocolConfig.java` — `reactor.netty.http`: El import reactor.netty.http.client.HttpClient pertenece a reactor.netty.http, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/integracion/protocolos/config/ProtocolConfig.java` — `reactor.netty.resources`: El import reactor.netty.resources.ConnectionProvider pertenece a reactor.netty.resources, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/pragma/integracion/protocolos/infrastructure/grpc/TransaccionGrpcServiceTest.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/rest/TransaccionController.java` — `Transaccion.isPresent`: Se invoca `isPresent` sobre `Transaccion`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/rest/TransaccionController.java` — `Transaccion.get`: Se invoca `get` sobre `Transaccion`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/rest/TransaccionController.java` — `Transaccion.isEmpty`: Se invoca `isEmpty` sobre `Transaccion`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/grpc/TransaccionGrpcService.java` — `Transaccion.getMessage`: Se invoca `getMessage` sobre `Transaccion`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/graphql/TransaccionGraphQLController.java` — `TransaccionInput.idempotencyKey`: Se invoca `idempotencyKey` sobre `TransaccionInput`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/graphql/TransaccionGraphQLController.java` — `TransaccionInput.monto`: Se invoca `monto` sobre `TransaccionInput`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/graphql/TransaccionGraphQLController.java` — `TransaccionInput.cuentaOrigen`: Se invoca `cuentaOrigen` sobre `TransaccionInput`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/graphql/TransaccionGraphQLController.java` — `TransaccionInput.cuentaDestino`: Se invoca `cuentaDestino` sobre `TransaccionInput`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/integracion/protocolos/infrastructure/graphql/TransaccionGraphQLController.java` — `TransaccionInput.descripcion`: Se invoca `descripcion` sobre `TransaccionInput`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/integracion/protocolos/infrastructure/grpc/TransaccionGrpcServiceTest.java` — `TransaccionGrpcService.procesarTransaccion`: Se invoca `procesarTransaccion` sobre `TransaccionGrpcService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
npx --yes @redocly/cli lint openapi.yaml
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Integración, Especialidad Desarrollador, Tecnología API, Advanced

### Brecha de conocimiento
Mostrar cómo RSocket se diferencia de HTTP/1, HTTP/2 y WebSocket. Aplicado el protocolo gRPC facilita la comunicación entre servicios en una arquitectura de microservicios. ¿Cómo se manejan los mensajes y la cola en AMQP? Explicar el rendimiento y flexibilidad al usar GraphQL en comparación con REST?

### Misión / candidato
Candidato con experiencia avanzada en APIs e integración de servicios

### Reto
- Tema: Protocolos de comunicación no convencionales
- Seniority: advanced-l2
- Tipo: theoretical
- Título: Comprensión y comparación de protocolos de comunicación avanzados
- Tiempo estimado: 4 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Exploración de RSocket y gRPC — objetivo: Comprender las diferencias y ventajas de RSocket y gRPC sobre HTTP/1, HTTP/2 y WebSocket. — entregable (NO resolver): Documento comparativo que detalla las diferencias, ventajas y casos de uso de RSocket y gRPC.
- Fase 2: Análisis de AMQP y GraphQL — objetivo: Entender el manejo de mensajes y colas en AMQP y la flexibilidad de GraphQL en comparación con REST. — entregable (NO resolver): Documento analítico que detalla el manejo de mensajes en AMQP y la comparación entre GraphQL y REST.
- Fase 3: Síntesis y aplicación práctica — objetivo: Aplicar el conocimiento adquirido para elegir el protocolo adecuado en diferentes escenarios de microservicios. — entregable (NO resolver): Documento de recomendaciones que detalla la elección de protocolos para diferentes escenarios de microservicios.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.pragma</groupId>
    <artifactId>integracion-protocolos</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>integracion-protocolos</name>
    <description>Microservicio reactivo para comparación de protocolos de comunicación</description>

    <properties>
        <java.version>21</java.version>
        <grpc.version>1.62.2</grpc.version>
        <rsocket.version>1.1.3</rsocket.version>
        <protobuf.version>3.25.1</protobuf.version>
        <spring-graphql.version>1.2.6</spring-graphql.version>
        <reactor.version>3.6.3</reactor.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-rsocket</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.graphql</groupId>
            <artifactId>spring-graphql</artifactId>
            <version>${spring-graphql.version}</version>
        </dependency>

        <!-- gRPC -->
        <dependency>
            <groupId>io.grpc</groupId>
            <artifactId>grpc-protobuf</artifactId>
            <version>${grpc.version}</version>
        </dependency>
        <dependency>
            <groupId>io.grpc</groupId>
            <artifactId>grpc-stub</artifactId>
            <version>${grpc.version}</version>
        </dependency>
        <dependency>
            <groupId>io.grpc</groupId>
            <artifactId>grpc-netty</artifactId>
            <version>${grpc.version}</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>net.devh</groupId>
            <artifactId>grpc-server-spring-boot-starter</artifactId>
            <version>2.15.0.RELEASE</version>
        </dependency>

        <!-- RSocket -->
        <dependency>
            <groupId>io.rsocket</groupId>
            <artifactId>rsocket-core</artifactId>
            <version>${rsocket.version}</version>
        </dependency>

        <!-- Reactor -->
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-core</artifactId>
            <version>${reactor.version}</version>
        </dependency>

        <!-- Test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.10.2</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-test</artifactId>
            <version>${reactor.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>5.11.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.xolstice.maven.plugins</groupId>
                <artifactId>protobuf-maven-plugin</artifactId>
                <version>0.6.1</version>
                <configuration>
                    <protocArtifact>com.google.protobuf:protoc:${protobuf.version}:exe:${os.detected.classifier}</protocArtifact>
                    <pluginId>grpc-java</pluginId>
                    <pluginArtifact>io.grpc:protoc-gen-grpc-java:${grpc.version}:exe:${os.detected.classifier}</pluginArtifact>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>compile</goal>
                            <goal>compile-custom</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>3.5.6</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
</project>

// === ARCHIVO: src/main/java/com/pragma/integracion/protocolos/ProtocolosApplication.java ===
package com.pragma.integracion.protocolos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import reactor.core.publisher.Hooks;

@SpringBootApplication
@EnableAsync
public class ProtocolosApplication {

    public static void main(String[] args) {
        Hooks.onOperatorDebug();
        SpringApplication.run(ProtocolosApplication.class, args);
    }

    @Bean
    public String applicationInfo(Environment env) {
        String appName = env.getProperty("spring.application.name", "integracion-protocolos");
        String version = env.getProperty("build.version", "0.0.1-SNAPSHOT");
        System.out.printf("Starting %s v%s%n", appName, version);
        return appName;
    }
}

// === ARCHIVO: src/main/resources/application.yml ===
server:
  port: 8080

spring:
  application:
    name: integracion-protocolos
  main:
    allow-bean-definition-overriding: true
  rsocket:
    server:
      port: 7000
      transport: tcp
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
    virtual-host: /
    listener:
      simple:
        acknowledge-mode: manual
        prefetch: 10
        retry:
          enabled: true
          max-attempts: 3
          initial-interval: 1000
          multiplier: 2.0
          max-interval: 10000
  graphql:
    graphiql:
      enabled: true
    schema:
      printer:
        enabled: true

grpc:
  server:
    port: 9090
    address: 0.0.0.0
    enable-reflection: true

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
  metrics:
    export:
      prometheus:
        enabled: true

protocolos:
  endpoints:
    core-banking:
      base-url: http://localhost:8081
      timeout: 5000
      retry:
        max-attempts: 3
        backoff:
          initial-interval: 500
          max-interval: 2000
          multiplier: 2.0
    fraud-detection:
      base-url: http://localhost:8082
      timeout: 3000
      retry:
        max-attempts: 2
        backoff:
          initial-interval: 300
          max-interval: 1000
          multiplier: 1.5
  amqp:
    exchange:
      transacciones: transacciones.exchange
    queue:
      transacciones: transacciones.queue
      transacciones.dlq: transacciones.dlq
    routing-key:
      transacciones: transacciones.routingkey
    ttl:
      message: 30000
      dlq: 60000
  rsocket:
    payload:
      metadata:
        mime-type: application/json
    responder:
      idle-timeout: 30000
  grpc:
    client:
      coreBanking:
        address: static://localhost:9091
        negotiation-type: plaintext
      fraudDetection:
        address: static://localhost:9092
        negotiation-type: plaintext

// === ARCHIVO: src/main/java/com/pragma/integracion/protocolos/domain/Transaccion.java ===
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

// === ARCHIVO: src/main/java/com/pragma/integracion/protocolos/domain/TransaccionDto.java ===
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

// === ARCHIVO: openapi.yaml ===
openapi: 3.1.0
info:
  title: API de Transacciones - Protocolos de Comunicación
  description: >
    API REST para gestión de transacciones bancarias con soporte para múltiples protocolos
    de comunicación (REST, gRPC, RSocket, AMQP, GraphQL). Este contrato define la interfaz
    para el microservicio de transacciones del sistema financiero.
  version: 1.0.0
  contact:
    name: Equipo de Desarrollo Pragma
    email: desarrollo@pragma.com.co
  license:
    name: Apache 2.0
    url: https://www.apache.org/licenses/LICENSE-2.0.html

servers:
  - url: http://localhost:8080
    description: Servidor de desarrollo local
    variables:
      scheme:
        default: http
        enum:
          - http
          - https
  - url: https://api.pragma.com.co/v1
    description: Servidor de producción
    variables:
      scheme:
        default: https
        enum:
          - https

tags:
  - name: Transacciones
    description: >
      Operaciones CRUD sobre transacciones financieras. Cada transacción representa
      una transferencia entre cuentas bancarias con soporte para idempotencia.
  - name: Salud
    description: Endpoints de monitoreo y salud del servicio
  - name: Métricas
    description: Endpoints de métricas y observabilidad

paths:
  /api/v1/transacciones:
    post:
      tags:
        - Transacciones
      summary: Crear una nueva transacción
      description: >
        Crea una nueva transacción bancaria entre dos cuentas. El sistema genera
        un ID único y un idempotencyKey para garantizar la idempotencia de la operación.
        Soporta los protocolos: REST (HTTP), gRPC, RSocket, AMQP y GraphQL.
      operationId: crearTransaccion
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/TransaccionRequest'
            example:
              monto: 150000.50
              cuentaOrigen: "CUENTA_001"
              cuentaDestino: "CUENTA_002"
              descripcion: "Pago de servicios"
      responses:
        '201':
          description: Transacción creada exitosamente
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/TransaccionResponse'
          headers:
            X-Idempotency-Key:
              description: Clave de idempotencia para reintentos
              schema:
                type: string
              example: "idem_abc123def456"
        '400':
          description: Solicitud inválida - datos de entrada incorrectos
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
              examples:
                monto_invalido:
                  summary: Monto inválido
                  value:
                    codigo: "VALIDATION_ERROR"
                    mensaje: "El monto debe ser mayor a cero"
                    detalles:
                      - campo: "monto"
                        problema: "MONTO_MENOR_IGUAL_CERO"
                cuenta_invalida:
                  summary: Cuenta de origen inválida
                  value:
                    codigo: "VALIDATION_ERROR"
                    mensaje: "La cuenta de origen no puede ser igual a la cuenta destino"
                    detalles:
                      - campo: "cuentaDestino"
                        problema: "CUENTAS_IGUALES"
        '409':
          description: Conflicto - transacción duplicada
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
              example:
                codigo: "DUPLICATE_TRANSACTION"
                mensaje: "Ya existe una transacción con esta clave de idempotencia"
                detalles:
                  - campo: "idempotencyKey"
                    problema: "DUPLICATE_KEY"
                    transactionId: "tx_abc123"
        '500':
          description: Error interno del servidor
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
              example:
                codigo: "INTERNAL_ERROR"
                mensaje: "Error al procesar la transacción"
                detalles: []

    get:
      tags:
        - Transacciones
      summary: Listar todas las transacciones
      description: >
        Obtiene un listado paginado de todas las transacciones. Soporta filtros
        por estado, rango de fechas y cuenta.
      operationId: listarTransacciones
      parameters:
        - name: page
          in: query
          description: Número de página (0-indexed)
          schema:
            type: integer
            default: 0
            minimum: 0
        - name: size
          in: query
          description: Tamaño de página
          schema:
            type: integer
            default: 20
            minimum: 1
            maximum: 100
        - name: estado
          in: query
          description: Filtrar por estado de transacción
          schema:
            $ref: '#/components/schemas/EstadoTransaccion'
        - name: cuentaOrigen
          in: query
          description: Filtrar por cuenta de origen
          schema:
            type: string
            pattern: "^[A-Z_]+[0-9]+$"
        - name: fechaInicio
          in: query
          description: Fecha inicial del rango (ISO 8601)
          schema:
            type: string
            format: date-time
        - name: fechaFin
          in: query
          description: Fecha final del rango (ISO 8601)
          schema:
            type: string
            format: date-time
      responses:
        '200':
          description: Lista de transacciones obtenida exitosamente
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/PaginatedTransacciones'
        '400':
          description: Parámetros de consulta inválidos
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
        '500':
          description: Error interno del servidor
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'

  /api/v1/transacciones/{id}:
    get:
      tags:
        - Transacciones
      summary: Obtener una transacción por ID
      description: >
        Recupera los detalles de una transacción específica mediante su identificador único.
      operationId: obtenerTransaccion
      parameters:
        - name: id
          in: path
          required: true
          description: Identificador único de la transacción (UUID)
          schema:
            type: string
            format: uuid
            example: "550e8400-e29b-41d4-a716-446655440000"
      responses:
        '200':
          description: Transacción encontrada
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/TransaccionResponse'
        '404':
          description: Transacción no encontrada
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
              example:
                codigo: "NOT_FOUND"
                mensaje: "Transacción no encontrada"
                detalles:
                  - campo: "id"
                    problema: "TRANSACTION_NOT_FOUND"
        '500':
          description: Error interno del servidor
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'

    patch:
      tags:
        - Transacciones
      summary: Actualizar estado de una transacción
      description: >
        Actualiza el estado de una transacción existente. Útil para retry manual
        o actualización de estado en casos de falla.
      operationId: actualizarEstadoTransaccion
      parameters:
        - name: id
          in: path
          required: true
          description: Identificador único de la transacción
          schema:
            type: string
            format: uuid
      requestBody:
        required: true
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/ActualizacionEstadoRequest'
            example:
              nuevoEstado: "COMPLETADA"
              razon: "Confirmación de destino"
      responses:
        '200':
          description: Estado actualizado exitosamente
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/TransaccionResponse'
        '400':
          description: Solicitud inválida
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
        '404':
          description: Transacción no encontrada
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
        '409':
          description: Conflicto de estado
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
              example:
                codigo: "STATE_CONFLICT"
                mensaje: "No se puede actualizar desde el estado actual"
                detalles:
                  - campo: "estado"
                    problema: "INVALID_STATE_TRANSITION"
        '500':
          description: Error interno del servidor
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'

  /api/v1/transacciones/{id}/idempotency-key:
    post:
      tags:
        - Transacciones
      summary: Generar nueva clave de idempotencia
      description: >
        Genera una nueva clave de idempotencia para una transacción existente,
        útil cuando se necesita reintentar una operación sin crear duplicados.
      operationId: generarClaveIdempotencia
      parameters:
        - name: id
          in: path
          required: true
          description: Identificador de la transacción
          schema:
            type: string
            format: uuid
      responses:
        '200':
          description: Clave de idempotencia generada
          content:
            application/json:
              schema:
                type: object
                properties:
                  idempotencyKey:
                    type: string
                    description: Nueva clave de idempotencia
                    example: "idem_nueva_clave_123"
                  transactionId:
                    type: string
                    format: uuid
        '404':
          description: Transacción no encontrada
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'
        '500':
          description: Error interno del servidor
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/ErrorResponse'

  /actuator/health:
    get:
      tags:
        - Salud
      summary: Verificar salud del servicio
      description: >
        Endpoint de health check para monitoreo. Returns el estado de salud
        del servicio incluyendo componentes críticos (RabbitMQ, gRPC, RSocket).
      operationId: healthCheck
      responses:
        '200':
          description: Servicio saludable
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/HealthResponse'
        '503':
          description: Servicio no saludable
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/HealthResponse'

  /actuator/info:
    get:
      tags:
        - Salud
      summary: Información de la aplicación
      description: >
        Retorna información de la aplicación incluyendo versión, entorno
        y configuración activa.
      operationId: appInfo
      responses:
        '200':
          description: Información de la aplicación
          content:
            application/json:
              schema:
                type: object
                properties:
                  app:
                    type: object
                    properties:
                      name:
                        type: string
                        example: "integracion-protocolos"
                      version:
                        type: string
                        example: "0.0.1-SNAPSHOT"
                  java:
                    type: object
                    properties:
                      version:
                        type: string
                        example: "21"
                  spring:
                    type: object
                    properties:
                      version:
                        type: string
                        example: "3.5.6"

components:
  schemas:
    TransaccionRequest:
      type: object
      required:
        - monto
        - cuentaOrigen
        - cuentaDestino
      properties:
        monto:
          type: number
          format: decimal
          description: Monto de la transacción (mayor a cero)
          minimum: 0.01
          maximum: 999999999.99
          example: 150000.50
        cuentaOrigen:
          type: string
          description: Cuenta bancaria de origen
          pattern: "^[A-Z_]+[0-9]+$"
          example: "CUENTA_001"
        cuentaDestino:
          type: string
          description: Cuenta bancaria de destino
          pattern: "^[A-Z_]+[0-9]+$"
          example: "CUENTA_002"
        descripcion:
          type: string
          description: Descripción de la transacción
          maxLength: 500
          example: "Pago de servicios mensuales"
        idempotencyKey:
          type: string
          description: >
            Clave de idempotencia opcional. Si no se provee, el sistema
            genera una automáticamente.
          maxLength: 100
          example: "idem_pago_servicios_001"

    TransaccionResponse:
      type: object
      properties:
        id:
          type: string
          format: uuid
          description: Identificador único de la transacción
          example: "550e8400-e29b-41d4-a716-446655440000"
        monto:
          type: number
          format: decimal
          example: 150000.50
        cuentaOrigen:
          type: string
          example: "CUENTA_001"
        cuentaDestino:
          type: string
          example: "CUENTA_002"
        timestamp:
          type: string
          format: date-time
          description: Timestamp de creación de la transacción
          example: "2024-01-15T10:30:00Z"
        estado:
          $ref: '#/components/schemas/EstadoTransaccion'
        idempotencyKey:
          type: string
          example: "idem_pago_servicios_001"
        descripcion:
          type: string
          example: "Pago de servicios mensuales"

    EstadoTransaccion:
      type: string
      enum:
        - PENDIENTE
        - PROCESANDO
        - COMPLETADA
        - FALLIDA
        - CANCELADA
      description: Estados posibles de una transacción
      example: "PENDIENTE"

    ActualizacionEstadoRequest:
      type: object
      required:
        - nuevoEstado
      properties:
        nuevoEstado:
          $ref: '#/components/schemas/EstadoTransaccion'
        razon:
          type: string
          description: Razón del cambio de estado
          maxLength: 500
          example: "Confirmación manual del destino"

    PaginatedTransacciones:
      type: object
      properties:
        content:
          type: array
          items:
            $ref: '#/components/schemas/TransaccionResponse'
        page:
          type: integer
          example: 0
        size:
          type: integer
          example: 20
        totalElements:
          type: integer
          description: Total de transacciones que coinciden con los filtros
          example: 150
        totalPages:
          type: integer
          example: 8

    ErrorResponse:
      type: object
      properties:
        codigo:
          type: string
          description: Código de error categorizado
          example: "VALIDATION_ERROR"
        mensaje:
          type: string
          description: Mensaje legible para el consumidor
          example: "La solicitud contiene datos inválidos"
        detalles:
          type: array
          description: Lista de errores específicos de campo
          items:
            type: object
            properties:
              campo:
                type: string
                description: Campo que contiene el error
                example: "monto"
              problema:
                type: string
                description: Código del problema específico
                example: "MONTO_MENOR_IGUAL_CERO"
              transactionId:
                type: string
                description: ID de transacción relacionada (para errores de duplicado)
                format: uuid

    HealthResponse:
      type: object
      properties:
        status:
          type: string
          enum:
            - UP
            - DOWN
            - DEGRADED
          description: Estado general del servicio
          example: "UP"
        components:
          type: object
          description: Estado de componentes individuales
          properties:
            rabbitmq:
              $ref: '#/components/schemas/ComponentHealth'
            grpc:
              $ref: '#/components/schemas/ComponentHealth'
            rsocket:
              $ref: '#/components/schemas/ComponentHealth'

    ComponentHealth:
      type: object
      properties:
        status:
          type: string
          enum:
            - UP
            - DOWN
          example: "UP"
        details:
          type: object
          description: Detalles específicos del componente
          properties:
            host:
              type: string
              example: "localhost"
            port:
              type: integer
              example: 9090

  securitySchemes:
    ApiKeyAuth:
      type: apiKey
      in: header
      name: X-API-Key
      description: >
        Clave de API para autenticación. Obtener del portal de desarrolladores.
    BearerAuth:
      type: http
      scheme: bearer
      bearerFormat: JWT
      description: >
        Token JWT para autenticación Bearer. Obtener del endpoint de login.

security:
  - ApiKeyAuth: []
  - BearerAuth: []

// === ARCHIVO: README.md ===
# Microservicio de Integración de Protocolos

Proyecto Spring Boot 3.5 con Java 21 que implementa un microservicio de transacciones
bancarias expuesto a través de múltiples protocolos de comunicación: REST, gRPC, RSocket,
AMQP (RabbitMQ) y GraphQL.

## Propósito

Este proyecto sirve como base para el estudio y comparación de protocolos de comunicación
avanzados en arquitecturas de microservicios. Los candidatos explorarán las diferencias
técnicas entre cada protocolo y aprenderán a seleccionar el más apropiado según el escenario.

## Requisitos Previos

- **Java Development Kit (JDK) 21** - Descargar de https://adoptium.net/ o mediante SDKMAN
- **Apache Maven 3.9+** - Gestor de dependencias y construcción
- **Docker y Docker Compose** - Para ejecutar servicios de infraestructura (RabbitMQ)
- **Protocol Buffer Compiler (protoc)** - Para compilar archivos .proto de gRPC

Verificar instalación:

```bash
java -version  # Debe mostrar 21.x
mvn -version   # Debe mostrar 3.9+
docker --version
protoc --version
```

## Estructura del Proyecto

```
integracion-protocolos/
├── pom.xml                          # Configuración Maven con todas las dependencias
├── src/main/java/com/pragma/integracion/protocolos/
│   ├── ProtocolosApplication.java   # Punto de entrada Spring Boot
│   ├── domain/                      # Modelo de dominio (DDD)
│   │   ├── Transaccion.java         # Entidad de dominio
│   │   └── TransaccionDto.java      # DTO inmutable (record)
│   ├── application/                 # Casos de uso y servicios de aplicación
│   ├── infrastructure/              # Adaptadores y configuraciones
│   │   ├── rest/                    # Controlador REST
│   │   ├── grpc/                    # Servicio gRPC
│   │   ├── rsocket/                 # Controlador RSocket
│   │   ├── amqp/                    # Productor y consumidor AMQP
│   │   └── graphql/                 # Controlador GraphQL
│   └── config/                      # Configuración de beans
├── src/main/proto/
│   └── transaccion.proto            # Definición de servicios gRPC
├── src/main/resources/
│   ├── application.yml              # Configuración principal
│   └── graphql/
│       └── schema.graphqls          # Schema GraphQL
└── src/test/java/                   # Pruebas unitarias y de integración
```

## Configuración de Servicios de Infraestructura

### RabbitMQ (para AMQP)

El proyecto utiliza RabbitMQ como broker de mensajes. Ejecutar con Docker:

```bash
# Iniciar contenedor RabbitMQ
docker run -d \
  --name rabbitmq \
  -p 5672:5672 \
  -p 15672:15672 \
  -e RABBITMQ_DEFAULT_USER=guest \
  -e RABBITMQ_DEFAULT_PASS=guest \
  rabbitmq:3.13-management

# Verificar que está corriendo
docker logs rabbitmq
# Acceder a UI: http://localhost:15672 (guest/guest)
```

O usar Docker Compose para un setup más completo:

```yaml
# docker-compose.yml
services:
  rabbitmq:
    image: rabbitmq:3.13-management
    ports:
      - "5672:5672"
      - "15672:15672"
    environment:
      RABBITMQ_DEFAULT_USER: guest
      RABBITMQ_DEFAULT_PASS: guest
    healthcheck:
      test: ["CMD", "rabbitmq-diagnostics", "ping"]
      interval: 30s
      timeout: 10s
      retries: 5
```

## Compilación y Ejecución

### Compilar el proyecto

```bash
mvn clean compile
```

Este comando:
1. Descarga todas las dependencias
2. Compila el código Java
3. Genera clases Java desde los archivos .proto (gRPC)
4. Verifica que no haya errores de compilación

### Ejecutar la aplicación

```bash
mvn spring-boot:run
```

La aplicación arrancará en http://localhost:8080 con los siguientes endpoints activos:

| Protocolo | Puerto | Endpoint base |
|-----------|--------|---------------|
| REST | 8080 | /api/v1/transacciones |
| gRPC | 9090 | localhost:9090 |
| RSocket | 7000 | ws://localhost:7000/rsocket |
| GraphQL | 8080 | /graphql |
| RabbitMQ | 5672 | cola: transacciones |

### Verificar que está funcionando

```bash
# Health check REST
curl http://localhost:8080/actuator/health

# Información de la app
curl http://localhost:8080/actuator/info

# Crear transacción via REST
curl -X POST http://localhost:8080/api/v1/transacciones \
  -H "Content-Type: application/json" \
  -d '{
    "monto": 100000,
    "cuentaOrigen": "CUENTA_001",
    "cuentaDestino": "CUENTA_002",
    "descripcion": "Prueba de transacción"
  }'

# Listar transacciones
curl http://localhost:8080/api/v1/transacciones
```

## Configuración

El archivo `src/main/resources/application.yml` contiene la configuración principal:

```yaml
server:
  port: 8080

spring:
  application:
    name: integracion-protocolos
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
  rsocket:
    server:
      port: 7000

grpc:
  server:
    port: 9090

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
```

## Uso de los Diferentes Protocolos

### REST (HTTP/1 - HTTP/2)

El endpoint REST sigue el diseño convencional de microservicios Spring Boot con WebFlux
para soporte reactivo completo.

### gRPC

Requiere el archivo `.proto` compilado. El servicio expone operaciones unary y streaming.

### RSocket

Protocolo binario orientado a mensajes que soporta interacción request-response, fire-and-forget,
request-stream y channel. Conecta via WebSocket en el puerto 7000.

### AMQP (RabbitMQ)

La aplicación configura:
- **Productor**: Envía transacciones a la cola `transacciones`
- **Consumidor**: Recibe y procesa mensajes de la cola
- **DLQ**: Cola de mensajes muertos para mensajes que no se pudieron procesar

### GraphQL

Schema definido en `src/main/resources/graphql/schema.graphqls`. Permite consultas
flexibles sobre las transacciones con la estructura exactas que el cliente necesita.

## Pruebas

```bash
# Ejecutar todas las pruebas
mvn test

# Ejecutar pruebas con reporte de coverage
mvn test jacoco:report

# Ejecutar una clase de prueba específica
mvn test -Dtest=TransaccionGrpcServiceTest
```

## Construcción para Producción

```bash
# Generar JAR executable
mvn clean package -DskipTests

# Ejecutar JAR generado
java -jar target/integracion-protocolos-0.0.1-SNAPSHOT.jar
```

## Troubleshooting

### Error de conexión a RabbitMQ

Verificar que el contenedor esté corriendo y los puertos expuestos:

```bash
docker ps | grep rabbitmq
docker logs rabbitmq
```

### Error de compilación de proto

Asegurarse de tener `protoc` instalado y en el PATH:

```bash
protoc --version
# Si no está, instalar: brew install protobuf (macOS) o apt-get install protobuf-compiler (Linux)
```

### Puerto en uso

Si los puertos 8080, 9090 o 7000 están en uso, modificar en `application.yml`:

```yaml
server:
  port: 8081  # Cambiar si 8080 está ocupado

grpc:
  server:
    port: 9091  # Cambiar si 9090 está ocupado
```

## Dependencias Principales

| Librería | Versión | Propósito |
|----------|---------|-----------|
| Spring Boot | 3.5.6 | Framework principal |
| Spring WebFlux | 3.5.6 | Programación reactiva |
| Spring RSocket | 3.5.6 | Protocolo RSocket |
| Spring AMQP | 3.5.6 | Integración RabbitMQ |
| Spring GraphQL | 1.2.6 | API GraphQL |
| gRPC | 1.62.2 | Protocolo gRPC |
| Reactor | 3.6.3 | Reactive streams |

## Licencia

Copyright © 2024 Pragma. Licensed under Apache License 2.0.

// === ARCHIVO: src/main/java/com/pragma/integracion/protocolos/infrastructure/rest/TransaccionController.java ===
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

// === ARCHIVO: src/main/java/com/pragma/integracion/protocolos/infrastructure/grpc/TransaccionGrpcService.java ===
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

// === ARCHIVO: src/main/proto/transaccion.proto ===
syntax = "proto3";

option java_package = "com.pragma.integracion.protocolos.infrastructure.grpc";
option java_multiple_files = true;

package com.pragma.integracion.protocolos;

service TransaccionService {
    rpc crearTransaccion (TransaccionRequest) returns (TransaccionResponse);
    rpc obtenerTransaccion (TransaccionByIdRequest) returns (TransaccionResponse);
    rpc listarTransacciones (ListarTransaccionesRequest) returns (stream TransaccionResponse);
    rpc crearTransaccionesStreaming (stream TransaccionRequest) returns (stream TransaccionResponse);
    rpc transaccionesPorCuenta (TransaccionPorCuentaRequest) returns (stream TransaccionResponse);
}

message TransaccionRequest {
    string monto = 1;
    string cuenta_origen = 2;
    string cuenta_destino = 3;
    string estado = 4;
    string idempotency_key = 5;
    string descripcion = 6;
}

message TransaccionResponse {
    string id = 1;
    string monto = 2;
    string cuenta_origen = 3;
    string cuenta_destino = 4;
    string timestamp = 5;
    string estado = 6;
    string idempotency_key = 7;
    string descripcion = 8;
}

message TransaccionByIdRequest {
    string id = 1;
}

message ListarTransaccionesRequest {
    string filtro_estado = 1;
    string filtro_cuenta = 2;
    int32 limite = 3;
    int32 offset = 4;
}

message TransaccionPorCuentaRequest {
    string cuenta = 1;
    bool incluir_origen = 2;
    bool incluir_destino = 3;
}

// === ARCHIVO: src/main/java/com/pragma/integracion/protocolos/infrastructure/rsocket/TransaccionRSocketController.java ===
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

// === ARCHIVO: src/main/java/com/pragma/integracion/protocolos/infrastructure/amqp/TransaccionAmqpProducer.java ===
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

// === ARCHIVO: src/main/java/com/pragma/integracion/protocolos/infrastructure/amqp/TransaccionAmqpConsumer.java ===
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

// === ARCHIVO: src/main/java/com/pragma/integracion/protocolos/infrastructure/graphql/TransaccionGraphQLController.java ===
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

// === ARCHIVO: src/main/resources/graphql/schema.graphqls ===
scalar DateTime

type Query {
    transacciones: [Transaccion!]!
    transaccion(id: ID!): Transaccion
    transaccionesPorCuenta(cuenta: String!): [Transaccion!]!
    transaccionesPorEstado(estado: String!): [Transaccion!]!
    transaccionesPorMontoRango(montoMin: Float!, montoMax: Float!): [Transaccion!]!
    estadisticas: Estadisticas!
}

type Mutation {
    crearTransaccion(input: TransaccionInput!): Transaccion!
    actualizarEstado(id: ID!, nuevoEstado: String!): Transaccion!
}

type Transaccion {
    id: ID!
    monto: Float!
    cuentaOrigen: String!
    cuentaDestino: String!
    timestamp: String!
    estado: String!
    idempotencyKey: String!
    descripcion: String!
}

input TransaccionInput {
    monto: Float!
    cuentaOrigen: String!
    cuentaDestino: String!
    descripcion: String
    idempotencyKey: String
}

type Estadisticas {
    totalTransacciones: Int!
    montoTotal: Float!
    completadas: Int!
    pendientes: Int!
    fallidas: Int!
    promedioMonto: Float!
}

// === ARCHIVO: src/main/java/com/pragma/integracion/protocolos/config/ProtocolConfig.java ===
package com.pragma.integracion.protocolos.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.client.WebClientGraphQLClient;
import org.springframework.graphql.client.reactive.ReactorNettyWebSocketGraphQLClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class ProtocolConfig {

    @Bean
    public WebClient httpClient() {
        ConnectionProvider provider = ConnectionProvider.builder("protocol-http-pool")
            .maxConnections(500)
            .maxIdleTime(Duration.ofSeconds(30))
            .maxLifeTime(Duration.ofMinutes(5))
            .pendingAcquireTimeout(Duration.ofSeconds(30))
            .build();

        HttpClient httpClient = HttpClient.create(provider)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
            .responseTimeout(Duration.ofSeconds(30))
            .doOnConnected(conn -> conn
                .addHandlerLast(new ReadTimeoutHandler(30, TimeUnit.SECONDS))
                .addHandlerLast(new WriteTimeoutHandler(30, TimeUnit.SECONDS)));

        return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .defaultHeader("Content-Type", "application/json")
            .defaultHeader("Accept", "application/json")
            .build();
    }

    @Bean
    public WebClient grpcWebClient() {
        ConnectionProvider provider = ConnectionProvider.builder("grpc-http-pool")
            .maxConnections(200)
            .maxIdleTime(Duration.ofSeconds(60))
            .maxLifeTime(Duration.ofMinutes(10))
            .pendingAcquireTimeout(Duration.ofSeconds(45))
            .build();

        HttpClient httpClient = HttpClient.create(provider)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 15000)
            .responseTimeout(Duration.ofSeconds(60));

        return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .defaultHeader("Content-Type", "application/grpc")
            .defaultHeader("Accept", "application/grpc+proto")
            .build();
    }

    @Bean
    public WebClient amqpManagementClient() {
        ConnectionProvider provider = ConnectionProvider.builder("amqp-mgmt-pool")
            .maxConnections(50)
            .maxIdleTime(Duration.ofSeconds(20))
            .maxLifeTime(Duration.ofMinutes(3))
            .pendingAcquireTimeout(Duration.ofSeconds(20))
            .build();

        HttpClient httpClient = HttpClient.create(provider)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .responseTimeout(Duration.ofSeconds(15));

        return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .defaultHeader("Content-Type", "application/json")
            .build();
    }

    @Bean
    public WebClientCustomizer graphqlWebClientCustomizer() {
        return webClientBuilder -> {
            WebClient graphqlWebClient = webClientBuilder
                .baseUrl("http://localhost:8080/graphql")
                .defaultHeader("Content-Type", "application/json")
                .build();
        };
    }

    @Bean
    public ConnectionProvider rsocketConnectionProvider() {
        return ConnectionProvider.builder("rsocket-pool")
            .maxConnections(100)
            .maxIdleTime(Duration.ofSeconds(45))
            .maxLifeTime(Duration.ofMinutes(8))
            .pendingAcquireTimeout(Duration.ofSeconds(25))
            .build();
    }

    @Bean
    public ProtocolProperties protocolProperties() {
        return new ProtocolProperties();
    }

    public static class ProtocolProperties {
        private int grpcPort = 9090;
        private int rsocketPort = 7000;
        private String amqpHost = "localhost";
        private int amqpPort = 5672;
        private String amqpUsername = "guest";
        private String amqpPassword = "guest";
        private int connectionTimeout = 10000;
        private int requestTimeout = 30000;
        private int maxRetries = 3;
        private long retryDelayMs = 1000;

        public int getGrpcPort() {
            return grpcPort;
        }

        public void setGrpcPort(int grpcPort) {
            this.grpcPort = grpcPort;
        }

        public int getRsocketPort() {
            return rsocketPort;
        }

        public void setRsocketPort(int rsocketPort) {
            this.rsocketPort = rsocketPort;
        }

        public String getAmqpHost() {
            return amqpHost;
        }

        public void setAmqpHost(String amqpHost) {
            this.amqpHost = amqpHost;
        }

        public int getAmqpPort() {
            return amqpPort;
        }

        public void setAmqpPort(int amqpPort) {
            this.amqpPort = amqpPort;
        }

        public String getAmqpUsername() {
            return amqpUsername;
        }

        public void setAmqpUsername(String amqpUsername) {
            this.amqpUsername = amqpUsername;
        }

        public String getAmqpPassword() {
            return amqpPassword;
        }

        public void setAmqpPassword(String amqpPassword) {
            this.amqpPassword = amqpPassword;
        }

        public int getConnectionTimeout() {
            return connectionTimeout;
        }

        public void setConnectionTimeout(int connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
        }

        public int getRequestTimeout() {
            return requestTimeout;
        }

        public void setRequestTimeout(int requestTimeout) {
            this.requestTimeout = requestTimeout;
        }

        public int getMaxRetries() {
            return maxRetries;
        }

        public void setMaxRetries(int maxRetries) {
            this.maxRetries = maxRetries;
        }

        public long getRetryDelayMs() {
            return retryDelayMs;
        }

        public void setRetryDelayMs(long retryDelayMs) {
            this.retryDelayMs = retryDelayMs;
        }
    }
}

// === ARCHIVO: src/test/java/com/pragma/integracion/protocolos/infrastructure/grpc/TransaccionGrpcServiceTest.java ===
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
```
