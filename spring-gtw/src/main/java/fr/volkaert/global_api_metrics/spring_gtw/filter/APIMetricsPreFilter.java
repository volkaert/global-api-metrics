package fr.volkaert.global_api_metrics.spring_gtw.filter;

import fr.volkaert.global_api_metrics.spring_gtw.APIMetricsCollection;
import fr.volkaert.global_api_metrics.spring_gtw.APIMetricsKey;
import fr.volkaert.global_api_metrics.spring_gtw.SpringGtwApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

// See https://cloud.spring.io/spring-cloud-gateway/reference/html/#developer-guide
// See https://medium.com/@niral22/spring-cloud-gateway-tutorial-5311ddd59816

@Component
public class APIMetricsPreFilter extends AbstractGatewayFilterFactory<APIMetricsPreFilter.Config> {

    @Autowired
    private APIMetricsCollection metricsCollection;

    public APIMetricsPreFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            APIMetricsKey metricsKey = config.getAPIMetricsKey();

            metricsCollection.incrementHitCount(metricsKey);

            long requestSize = exchange.getRequest().getHeaders().getContentLength();
            metricsCollection.updateMaxRequestSizeIfGreater(metricsKey, requestSize);

            long now = System.currentTimeMillis();
            exchange.getAttributes().put(SpringGtwApplication.EXCHANGE_ATTRIBUTE_FOR_EXECUTION_START_TIME, now);

            //If you want to build a "pre" filter you need to manipulate the
            //request before calling chain.filter
            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
            //use builder to manipulate the request
            ServerHttpRequest request = builder
                    //.header("API-CODE", config.getApiCode())
                    .build();
            return chain.filter(exchange.mutate().request(request).build());
        };
    }


    public static class Config {

        private String referentialCode = SpringGtwApplication.DEFAULT_REFERENTIAL_CODE;
        private String apiCode; // mandatory
        private String apiName; // optional

        public APIMetricsKey getAPIMetricsKey() {
            return new APIMetricsKey(referentialCode, apiCode, apiName);
        }

        public String getReferentialCode() {
            return referentialCode;
        }

        public void setReferentialCode(String referentialCode) {
            this.referentialCode = referentialCode;
        }

        public String getApiCode() {
            return apiCode;
        }

        public void setApiCode(String apiCode) {
            this.apiCode = apiCode;
        }

        public String getApiName() {
            return apiName;
        }

        public void setApiName(String apiName) {
            this.apiName = apiName;
        }
    }
}