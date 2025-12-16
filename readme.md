## Modules

- api-gateway (public)
- movie service: purpose is internal microservice
- comment service: purpose is internal microservice

Api Gateway performs authentication and authorization. It has no business logic.
Microservices are using OAUTH2 authorization as resource servers to validate access token.

## Technologies

- Spring Boot 4
- Spring WebMVC/WebFlux
- Spring Cloud Gateway
- Resilience4j Circuit Breaker
- Spring Security
- OAUTH2 Client
- OAUTH2 Resource Server
- Spring Data MongoDB
- MongoDB Atlas (with search index)
- Elastic APM Observability (logs, metrics, traces)

## Observability

OTLP protocol is used to send logs, metrics and traces to Elastic APM.

Adding dependency:
`implementation("co.elastic.otel:elastic-otel-runtime-attach:1.8.0")`

Then, attaching the instrumentation agent by calling:
`RuntimeAttach.attachJavaagentToCurrentJvm()`

Add environment variables, like:

```
OTEL_EXPORTER_OTLP_ENDPOINT=https://c6aba808ecb146aca0703f6925e693ed.ingest.europe-west3.gcp.elastic.cloud:443;
OTEL_EXPORTER_OTLP_HEADERS=Authorization=ApiKey <apikey>;
OTEL_RESOURCE_ATTRIBUTES=service.name=api-gateway-app,service.version=0.1,deployment.environment=production
```

Open Elastic APM UI: https://my-observability-project-c6aba8.kb.europe-west3.gcp.elastic.cloud