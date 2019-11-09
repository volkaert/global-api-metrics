# Global API Metrics Spring Gateway


## Introduction

The **Global API Metrics Spring Gateway** is a Spring Cloud Gateway that collects metrics about APIs/microservices called and sends these metrics periodically to a **Global API Metrics Collector** (using a REST API of the Collector).

The Gateway uses custom filters (pre and post filters) to intercept requests and extract metrics.

To test the Gateway, 2 others components are provided: 
- a **Fake Collector** that is a mock for the actual Global API Metrics Collector. It exposes a REST API at `http://localhost:8081/collector/metrics`. 
- a **Fake API** that is a mock for the actual API behind the Gateway. It exposes a REST API at `http://localhost:8082/hello/{name}`.

> By default, **Java 11** (LTS) is used to build and run the various components. 
>It should also work with Java 8 but it does NOT have been tested with Java 8 so be careful if you use Java 8 (but why are you still using Java 8 ?!).

 
## Build

### Build the Gateway
```
cd spring-gtw
./mvnw clean package
```

### Build the Fake Collector
```
cd fake-collector
./mvnw clean package
```

### Build the Fake API
```
cd fake-api
./mvnw clean package
```


## Run
 
### Run the Fake Collector
```
cd fake-collector
./mvnw spring-boot:run
```
By default, the Fake Collector listens on port `8081`.
The default port can be changed using the `server.port` property in the `application.yml` file.

### Run the Fake API
```
cd fake-api
./mvnw spring-boot:run
```
By default, the Fake API listens on port `8082`. 
The default port can be changed using the `server.port` property in the `application.yml` file.

### Run the Gateway
```
cd spring-gtw
./mvnw spring-boot:run
```
By default, the Gateway listens on port `8080` and forwards all requests to the Fake API (at `http://localhost:8082`).
The default port can be changed using the `server.port` property in the `application.yml` file.

## Test

- In the `application.yml` file of the Gateway, replace the `cron-expr` by `"0/5 * * * * ?"` to send the metrics to the Fake Collector every 5 seconds (instead of once per day; or be patient ;-) ).
- Run (or restart) the Gateway (if you run the target jar, don't forget to rebuild the Gateway to embed the updated `application.yml` file in your jar before to run it)
- Open a browser (or run a `curl`) at `http://localhost:8080/hello/luke`
- Look at the metrics displayed in the terminals of both the Gateway and the Fake Collector (you may have to wait 5 seconds because metrics are sent to the Fake Collector only every 5 seconds)
- To generate a 4xx client error (to see metrics about errors), open a browser (or run a `curl`) at `http://localhost:8080/hello/4xx` 
- To generate a 5xx server error (to see metrics about errors), open a browser (or run a `curl`) at `http://localhost:8080/hello/5xx` 

> **Pay attention**: the metrics are reset each time they are sent to the Collector.
> It is therefore normal to see a number of metrics 0 quite often 
> (for example the counter may be reset to 0 every 5 seconds if you modified the `cron-expr` property in DEV mode).
> Be quick to read the logs just after pressing Enter in your browser (or your `curl`) or 
>increase the period in the `cron-expr` for a slower pace.


## Customize

All customizations are made in the `application.yml` files of the various components.

## Customize the Gateway
- `server.port`: the port used by the Gateway to listen to incoming requests. Default is `8080`. May be changed for PROD (to use regular `80` or `443`)
- `global-api-metrics.export.cron-expr`: the cron expression to schedule the export of metrics to the Global Collector. Should be set to something like `"0 59 23 * * ?"` (every day at 23:59) for PROD and `"0/5 * * * * ?"` (every 5 seconds) for DEV.
- `global-api-metrics.collector.url`: the URL of the Global Collector where to export the metrics. Default is `http://localhost:8081/collector/metrics`. Must be changed for PROD to use the URL of the actual Global API Metrics Collector.
- `global-api-metrics.referential.code`: the referential code used to identify your API. Default is "GROUP". Must be changed if you don't use the GROUP referential.
- `global-api-metrics.api.code`: the code of your API in your referential. Mandatory. Must be changed for PROD.
- `global-api-metrics.api.name`: the name of your API in your referential. Optional (but recommended to ease the analysis of the metrics). Must be changed for PROD (or not defined).
- `spring.cloud.gateway.routes`: the configuration of your routes (where to forward requests, what are the predicates, what are the filters...). Must be changed for PROD.

## Customize the Fake Collector
- `server.port`: the port used by the Fake Collector to listen to metrics sent by the Gateway

## Customize the Fake API
- `server.port`: the port used by the Fake API to listen to `/hello/{name}` requests
