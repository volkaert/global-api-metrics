package fr.volkaert.global_api_metrics.fake_collector;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel(value = "Metrics Collection", description = "Collection of metrics for the period (since the last export/collect)")
public class APIMetricsCollectionDTO {
    public List<APIMetricsDTO> metrics;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate exportDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime exportDateTime;

    public APIMetricsCollectionDTO() {
    }
}
