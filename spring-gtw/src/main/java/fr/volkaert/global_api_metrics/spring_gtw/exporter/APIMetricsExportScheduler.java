package fr.volkaert.global_api_metrics.spring_gtw.exporter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class APIMetricsExportScheduler {

    @Autowired
    private APIMetricsExporter exporter;

    @Scheduled(cron = "${global-api-metrics.export.cron-expr}")
    public void exportMetrics() {
        // try/catch is used to prevent from exception raising
        // (to be sure the scheduler will not be deactivated)
        try {
            exporter.export();
        } catch (Exception ex) {
            System.out.println("*** ERROR: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
