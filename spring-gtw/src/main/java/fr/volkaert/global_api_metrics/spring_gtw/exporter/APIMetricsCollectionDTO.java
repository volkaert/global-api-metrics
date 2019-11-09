package fr.volkaert.global_api_metrics.spring_gtw.exporter;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class APIMetricsCollectionDTO {
    public List<APIMetricsDTO> metrics;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate exportDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime exportDateTime;

    public APIMetricsCollectionDTO() {
    }
}
