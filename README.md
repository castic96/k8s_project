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

### Realizovaný postup
1. vytvořena aplikace:
      - endpoint `{basePath}/calculate`, např. `localhost:8082/calculate` (port `8082` - by default),
      - přidán výpočet faktoriálu pro číslo `20`,
      - vystaveny všechny endpointy `Actuatoru` - vytížení CPU pro proces `JVM`: `{basePath}/actuator/metrics/process.cpu.usage` např. `http://localhost:8082/actuator/metrics/process.cpu.usage`
      - 
2. vytížení simulováno přes `wrk` verze `4.2.0` (https://github.com/wg/wrk):
      - příklad: `./wrk -t20 -c4000 -d2m http://localhost:8082/calculate`
        - 20 threads, 4000 connections, 2 minutes duration
      - lze také použít Docker Image `ghcr.io/william-yeh/wrk` (https://github.com/William-Yeh/docker-wrk):
        - příklad: `docker run --rm -it ghcr.io/william-yeh/wrk -t20 -c4000 -d2m http://host.docker.internal:8082/calculate`
        - dát si pozor na nastavení Dockeru - pokud jsou malé zdroje pro Docker, pošle se málo requestů a vytížení bude malé
      - pozn.: `h2load` jsem nebyl schopen zprovoznit, nejspíš vyžadován SSL/TLS certifikát pro `localhost`
3. přidán `Dockerfile` a `Image` pushnutý do Docker Hub,
4. vyzkoušen běh aplikace v `Kubernetes` přes `CLI`,
5. vytvořen `config yaml` pro deploy přes `Kubernetes`:
    - `Deployment` pro aplikaci,
    - `Service` (typ `LoadBalancer`) pro aplikaci,
    - `HorizontalPodAutoscaler` pro aplikaci.

### Spuštění aplikace v k8s clusteru Docker Desktop
1. spustit metrics server: `kubectl apply -f metrics-server.yml`,
2. otestovat, zda běží metrics server: `kubectl top node` - musí vrátit nechybový výpis,
3. spustit aplikaci: `kubectl apply -f metrics-server.yml`.

### Useful commands
- `kubectl describe hpa` - popis Horizontal Pod Autoscaling
- `kubectl get pods` - list všech podů

# TODO
- fixnout to, že pody jsou pending - mají málo zdrojů asi? zkusit zvýšit počet CPU v Docker Desktop
- doplnit dashboard k8s
- doplnit prometheus
