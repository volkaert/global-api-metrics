package fr.volkaert.global_api_metrics.fake_collector;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Metrics", description = "Metrics for the period (since the last export/collect)")
public class APIMetricsDTO {

    @ApiModelProperty(notes = "Code of the API referential used to scope the API code (GROUP if the Group Referential is used)", example = "GROUP")
    public String referentialCode;

    @ApiModelProperty(notes = "Code of the API (in the API referential identified by the referentialCode attribute) for which metrics are collected", example = "12345")
    public String apiCode;

    @ApiModelProperty(notes = "Name of the API (in the API referential identified by the referentialCode attribute) for which metrics are collected (optional but still useful to ease the analysis)", example = "My Awesome API")
    public String apiName;

    @ApiModelProperty(notes = "Hit count (= requests count) for this API", example = "246802")
    public long hitCount = 0;

    @ApiModelProperty(notes = "Number of times the API returned a success (2xx HTTP Code)", example = "244623")
    public long success2xxCount = 0;

    @ApiModelProperty(notes = "Number of times the API returned a client error (4xx HTTP Code)", example = "146")
    public long error4xxCount = 0;

    @ApiModelProperty(notes = "Number of times the API returned a server error (5xx HTTP Code)", example = "58")
    public long error5xxCount = 0;


    @ApiModelProperty(notes = "Total execution time (in ms) for the requests sent to the API", example = "468013579")
    public long totalExecutionTime = 0;

    @ApiModelProperty(notes = "Max execution time (in ms) for the requests sent to the API", example = "468013579")
    public long maxExecutionTime = 0;

    @ApiModelProperty(notes = "Max request size (in bytes) for the requests sent to the API", example = "728")
    public long maxRequestSize = 0;

    @ApiModelProperty(notes = "Max response size (in bytes) for the requests sent to the API", example = "12635")
    public long maxResponseSize = 0;

    public APIMetricsDTO() {
    }
}
