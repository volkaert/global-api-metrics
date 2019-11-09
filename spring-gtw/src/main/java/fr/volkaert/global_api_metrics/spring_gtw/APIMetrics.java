package fr.volkaert.global_api_metrics.spring_gtw;

public class APIMetrics {

    private final APIMetricsKey key;
    private long hitCount = 0;
    private long success2xxCount = 0;
    private long error4xxCount = 0;
    private long error5xxCount = 0;
    private long totalExecutionTime = 0;
    private long maxExecutionTime = 0;
    private long maxRequestSize = 0;
    private long maxResponseSize = 0;

    public APIMetrics(APIMetricsKey key) {
        this.key = key;
    }

    public APIMetrics(APIMetrics metricsToClone) {
        this.key = metricsToClone.getKey();
        this.hitCount = metricsToClone.hitCount;
        this.success2xxCount = metricsToClone.success2xxCount;
        this.error4xxCount = metricsToClone.error4xxCount;
        this.error5xxCount = metricsToClone.error5xxCount;
        this.totalExecutionTime = metricsToClone.totalExecutionTime;
        this.maxExecutionTime = metricsToClone.maxExecutionTime;
        this.maxRequestSize = metricsToClone.maxRequestSize;
        this.maxResponseSize = metricsToClone.maxResponseSize;
    }

    public APIMetricsKey getKey() {
        return key;
    }

    public long incrementHitCount() {
        return ++hitCount;
    }

    public long incrementSuccess2xxCount() {
        return ++success2xxCount;
    }

    public long incrementError4xxCount() {
        return ++error4xxCount;
    }

    public long incrementError5xxCount() {
        return ++error5xxCount;
    }

    public long incrementTotalExecutionTime(long executionTime) {
        totalExecutionTime += executionTime;
        return totalExecutionTime;
    }

    public long updateMaxExecutionTimeIfGreater(long executionTime) {
        if (executionTime > maxExecutionTime) {
            maxExecutionTime = executionTime;
        }
        return maxExecutionTime;
    }

    public long updateMaxRequestSizeIfGreater(long requestSize) {
        if (requestSize > maxRequestSize) {
            maxRequestSize = requestSize;
        }
        return maxRequestSize;
    }

    public long updateMaxResponseSizeIfGreater(long responseSize) {
        if (responseSize > maxResponseSize) {
            maxResponseSize = responseSize;
        }
        return maxResponseSize;
    }

    public long getHitCount() {
        return hitCount;
    }

    public long getSuccess2xxCount() {
        return success2xxCount;
    }

    public long getError4xxCount() {
        return error4xxCount;
    }

    public long getError5xxCount() {
        return error5xxCount;
    }

    public long getTotalExecutionTime() {
        return totalExecutionTime;
    }

    public long getMaxExecutionTime() {
        return maxExecutionTime;
    }

    public long getMaxRequestSize() {
        return maxRequestSize;
    }

    public long getMaxResponseSize() {
        return maxResponseSize;
    }
}
