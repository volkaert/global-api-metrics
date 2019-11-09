package fr.volkaert.global_api_metrics.spring_gtw.exporter;

public class APIMetricsDTO {

    public String referentialCode;
    public String apiCode;
    public String apiName;
    public long hitCount = 0;
    public long success2xxCount = 0;
    public long error4xxCount = 0;
    public long error5xxCount = 0;
    public long totalExecutionTime = 0;
    public long maxExecutionTime = 0;
    public long maxRequestSize = 0;
    public long maxResponseSize = 0;

    public APIMetricsDTO() {
    }
}
