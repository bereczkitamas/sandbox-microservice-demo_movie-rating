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