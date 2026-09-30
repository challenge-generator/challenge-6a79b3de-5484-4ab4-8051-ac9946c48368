# Comprensión y comparación de protocolos de comunicación avanzados

En el dominio de la banca y las fintech, la comunicación eficiente y segura entre servicios es crucial. Este reto se enfoca en la comprensión y comparación de diferentes protocolos de comunicación no convencionales como RSocket, gRPC, AMQP y GraphQL, y su aplicación en arquitecturas de microservicios. Los candidatos deben explorar cómo cada protocolo difiere de los estándares HTTP/1, HTTP/2 y WebSocket, y analizar sus ventajas y desventajas en términos de rendimiento, flexibilidad y manejo de mensajes.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | Protocolos de comunicación no convencionales |
| **Nivel** | advanced-l2 |
| **Tipo** | theoretical |
| **Tiempo estimado** | 4 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Exploración de RSocket y gRPC

**Objetivo:** Comprender las diferencias y ventajas de RSocket y gRPC sobre HTTP/1, HTTP/2 y WebSocket.

**Tiempo estimado:** 1 hora

**Instrucciones:**

- Investiga y compara las características de RSocket y gRPC con HTTP/1, HTTP/2 y WebSocket.
- Identifica los casos de uso ideales para cada protocolo.
- Analiza cómo gRPC facilita la comunicación entre servicios en una arquitectura de microservicios.

**Entregable:** Documento comparativo que detalla las diferencias, ventajas y casos de uso de RSocket y gRPC.

<details>
<summary>Pistas de conocimiento</summary>

- Considera la latencia, el rendimiento y la complejidad de implementación de cada protocolo.
- Piensa en cómo cada protocolo maneja la comunicación en tiempo real y la transmisión de datos bidireccional.

</details>

### Fase 2: Análisis de AMQP y GraphQL

**Objetivo:** Entender el manejo de mensajes y colas en AMQP y la flexibilidad de GraphQL en comparación con REST.

**Tiempo estimado:** 1 hora

**Instrucciones:**

- Investiga cómo AMQP maneja los mensajes y las colas en sistemas distribuidos.
- Compara el rendimiento y la flexibilidad de GraphQL con REST.
- Identifica los beneficios y desafíos de usar GraphQL en lugar de REST para la comunicación entre servicios.

**Entregable:** Documento analítico que detalla el manejo de mensajes en AMQP y la comparación entre GraphQL y REST.

<details>
<summary>Pistas de conocimiento</summary>

- Considera la confiabilidad y la escalabilidad de AMQP en sistemas de mensajería.
- Piensa en cómo GraphQL permite consultas más eficientes y flexibles en comparación con REST.

</details>

### Fase 3: Síntesis y aplicación práctica

**Objetivo:** Aplicar el conocimiento adquirido para elegir el protocolo adecuado en diferentes escenarios de microservicios.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Evalúa diferentes escenarios de microservicios y elige el protocolo de comunicación más adecuado.
- Justifica tu elección basándote en las características y ventajas de cada protocolo.
- Proporciona recomendaciones para la implementación y uso de los protocolos seleccionados en un entorno de banca y fintech.

**Entregable:** Documento de recomendaciones que detalla la elección de protocolos para diferentes escenarios de microservicios.

<details>
<summary>Pistas de conocimiento</summary>

- Considera la latencia, el rendimiento, la escalabilidad y la complejidad de implementación de cada protocolo.
- Piensa en cómo cada protocolo se adapta a las necesidades específicas de comunicación en el dominio de la banca y las fintech.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es RSocket y cómo se diferencia de HTTP/1, HTTP/2 y WebSocket?
- **paraQueSirve**: ¿Para qué sirve gRPC en una arquitectura de microservicios?
- **comoSeUsa**: ¿Cómo se manejan los mensajes y la cola en AMQP?
- **erroresComunes**: ¿Cuáles son los errores comunes al implementar GraphQL en lugar de REST?
- **queDecisionesImplica**: ¿Qué decisiones implica la elección de un protocolo de comunicación en diferentes escenarios de microservicios?

## Criterios de Evaluacion

- Comprensión y comparación de RSocket, gRPC, AMQP y GraphQL con HTTP/1, HTTP/2 y WebSocket.
- Identificación de los casos de uso ideales para cada protocolo.
- Análisis del rendimiento y flexibilidad de GraphQL en comparación con REST.
- Elección y justificación del protocolo adecuado en diferentes escenarios de microservicios.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
npx --yes @redocly/cli lint openapi.yaml
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
