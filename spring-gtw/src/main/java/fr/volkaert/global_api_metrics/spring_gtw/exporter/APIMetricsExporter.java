package fr.volkaert.global_api_metrics.spring_gtw.exporter;

import fr.volkaert.global_api_metrics.spring_gtw.APIMetrics;
import fr.volkaert.global_api_metrics.spring_gtw.APIMetricsCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class APIMetricsExporter {

    Logger logger = LoggerFactory.getLogger(APIMetricsExporter.class);

    @Autowired
    private APIMetricsCollection metricsCollection;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${global-api-metrics.collector.url}")
    private String collectorUrl;

    public void export() {
        logger.info("API Metrics Export triggered at {}", LocalDateTime.now());

        List<APIMetrics> allMetrics = metricsCollection.getAllMetricsThenReset();

        APIMetricsCollectionDTO allMetricsDTO = toDTO(allMetrics);
        allMetricsDTO.exportDate = LocalDate.now();
        allMetricsDTO.exportDateTime = LocalDateTime.now();

        log(allMetricsDTO);

        sendMetricsToTheCollectorAPI(allMetricsDTO);
    }

    private void sendMetricsToTheCollectorAPI(APIMetricsCollectionDTO allMetrics) {
        try {
            logger.info("Sending metrics to the API Metrics Collector using the URL {}", collectorUrl);
            restTemplate.postForObject(collectorUrl, allMetrics, Void.class);
            logger.info("Metrics successfully sent to the API Metrics Collector");
        } catch (Exception ex) {
            logger.error("ERROR " + ex.getMessage(), ex);
        }
    }

    private APIMetricsDTO toDTO(APIMetrics metrics) {
        APIMetricsDTO dto = new APIMetricsDTO();
        dto.referentialCode = metrics.getKey().getReferentialCode();
        dto.apiCode = metrics.getKey().getApiCode();
        dto.apiName = metrics.getKey().getApiName();
        dto.hitCount = metrics.getHitCount();
        dto.success2xxCount = metrics.getSuccess2xxCount();
        dto.error4xxCount = metrics.getError4xxCount();
        dto.error5xxCount = metrics.getError5xxCount();
        dto.totalExecutionTime = metrics.getTotalExecutionTime();
        dto.maxExecutionTime = metrics.getMaxExecutionTime();
        dto.maxRequestSize = metrics.getMaxRequestSize();
        dto.maxResponseSize = metrics.getMaxResponseSize();
        return dto;
    }

    private APIMetricsCollectionDTO toDTO(List<APIMetrics> allMetrics) {
        APIMetricsCollectionDTO dto = new APIMetricsCollectionDTO();
        dto.metrics = new ArrayList<>(allMetrics.size());
        for (APIMetrics metrics : allMetrics) {
            dto.metrics.add(toDTO(metrics));
        }
        return dto;
    }

    private void log(APIMetricsCollectionDTO allMetrics) {
        logger.info("Export date: {}", allMetrics.exportDate);
        logger.info("Export datetime: {}", allMetrics.exportDateTime);
        logger.info("Metrics count (= API count): {}", allMetrics.metrics.size());
        if (logger.isDebugEnabled()) {
            for (APIMetricsDTO metrics : allMetrics.metrics) {
                String apiCode = metrics.apiCode;
                logger.debug("API {} was called {} times", apiCode, metrics.hitCount);
                logger.debug("API {} returned a success (2xx) {} times", apiCode, metrics.success2xxCount);
                logger.debug("API {} returned a 4xx client error {} times", apiCode, metrics.error4xxCount);
                logger.debug("API {} returned a 5xx server error {} times", apiCode, metrics.error5xxCount);
                logger.debug("API {} had a total execution time of {} ms", apiCode, metrics.totalExecutionTime);
                logger.debug("API {} had a max execution time of {} ms", apiCode, metrics.maxExecutionTime);
                logger.debug("API {} had an average execution time of {} ms", apiCode, metrics.totalExecutionTime / metrics.hitCount);
                logger.debug("API {} had a max request size of {} bytes", apiCode, metrics.maxRequestSize);
                logger.debug("API {} had a max response size of {} bytes", apiCode, metrics.maxResponseSize);
            }
        }
    }
}
