package fr.volkaert.global_api_metrics.spring_gtw.filter;

import fr.volkaert.global_api_metrics.spring_gtw.APIMetricsCollection;
import fr.volkaert.global_api_metrics.spring_gtw.APIMetricsKey;
import fr.volkaert.global_api_metrics.spring_gtw.SpringGtwApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

// See https://cloud.spring.io/spring-cloud-gateway/reference/html/#developer-guide
// See https://medium.com/@niral22/spring-cloud-gateway-tutorial-5311ddd59816

@Component
public class APIMetricsPostFilter extends AbstractGatewayFilterFactory<APIMetricsPostFilter.Config> {

    @Autowired
    private APIMetricsCollection metricsCollection;

    public APIMetricsPostFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                ServerHttpResponse response = exchange.getResponse();

                APIMetricsKey metricsKey = config.getAPIMetricsKey();

                if (response.getStatusCode().is2xxSuccessful()) {
                    metricsCollection.incrementSuccess2xxCount(metricsKey);
                }
                else if (response.getStatusCode().is4xxClientError()) {
                    metricsCollection.incrementError4xxCount(metricsKey);
                }
                else if (response.getStatusCode().is5xxServerError()) {
                    metricsCollection.incrementError5xxCount(metricsKey);
                }
                long responseSize = response.getHeaders().getContentLength();
                metricsCollection.updateMaxResponseSizeIfGreater(metricsKey, responseSize);

                Long executionStartTime = (Long)exchange.getAttribute(SpringGtwApplication.EXCHANGE_ATTRIBUTE_FOR_EXECUTION_START_TIME);
                if (executionStartTime != null) {
                    long now = System.currentTimeMillis();
                    long executionTime = now - executionStartTime.longValue();
                    metricsCollection.incrementTotalExecutionTime(metricsKey, executionTime);
                    metricsCollection.updateMaxExecutionTimeIfGreater(metricsKey, executionTime);
                }
            }));
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
