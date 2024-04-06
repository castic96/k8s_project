# Kubernetes projekt
## Zadání
- vytvořit aplikaci ve `Spring Boot` s jedním endpointem,
- při zavolání endpointu simulovat vytížení (např. CPU, paměť, ...),
- endpoint provolávat např. přes `h2load` (https://nghttp2.org/documentation/h2load-howto.html),
- podle vytížení škálovat pomocí `Kubernetes` počet Podů,
- na metriky možnost použít `Spring Boot Actuator`.

## Řešení
### Návrh postupu
1. vytvořit template přes `Spring Initializr` (https://start.spring.io) včetně `Spring Boot Actuator`,
2. vytvořit endpoint pro provolávání aplikace,
3. přidat spuštění výpočtu, který vytíží CPU,
4. provolávat endpoint přes `h2load` a zkontrolovat, zda dochází k požadovanému vytížení CPU (přes `API Actuatoru`),
5. vytvořit `Dockerfile` pro aplikaci a `Image` pushnout ho do `Docker Hub`,
6. vyzkoušet běh aplikace v `Kubernetes` přes `CLI`,
7. vytvořit `config yaml` pro deploy přes `Kubernetes`:
   - definovat `Deployment` pro aplikaci,
   - definovat `Service` (typ `LoadBalancer`) pro aplikaci,
   - definovat `HorizontalPodAutoscaler` pro aplikaci,
   - opt: definovat `Deployment` a `Service` pro `Kubernetes dashboard`,
   - opt: definovat `Deployment` a `Service` pro `h2load`.
8. opt: integrovat monitorování metrik přes `Prometheus` a porovnat s `dashboardem Kubernetes` vytížení CPU.