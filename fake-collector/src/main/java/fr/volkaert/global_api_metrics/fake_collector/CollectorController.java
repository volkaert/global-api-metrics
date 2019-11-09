package fr.volkaert.global_api_metrics.fake_collector;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collector")
@Api(value = "Collector", tags = { "Collector" })
public class CollectorController {

    Logger logger = LoggerFactory.getLogger(CollectorController.class);

    @PostMapping(value = "/metrics")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value="Collect metrics")
    public void collect(@RequestBody @ApiParam(name="metricsCollection", value="Metrics Collection") APIMetricsCollectionDTO allMetrics /*, @RequestHeader HttpHeaders headers */) {
        logger.info("Metrics collector endpoint called");
        log(allMetrics);
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
