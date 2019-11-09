package fr.volkaert.global_api_metrics.spring_gtw;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class APIMetricsCollection {

    private final Map<APIMetricsKey, APIMetrics> allMetrics = new HashMap();

    public APIMetrics incrementHitCount(APIMetricsKey key) {
        synchronized (allMetrics) {
            APIMetrics metrics = getOrCreate(key);
            metrics.incrementHitCount();
            return metrics;
        }
    }

    public APIMetrics incrementSuccess2xxCount(APIMetricsKey key) {
        synchronized (allMetrics) {
            APIMetrics metrics = getOrCreate(key);
            metrics.incrementSuccess2xxCount();
            return metrics;
        }
    }

    public APIMetrics incrementError4xxCount(APIMetricsKey key) {
        synchronized (allMetrics) {
            APIMetrics metrics = getOrCreate(key);
            metrics.incrementError4xxCount();
            return metrics;
        }
    }

    public APIMetrics incrementError5xxCount(APIMetricsKey key) {
        synchronized (allMetrics) {
            APIMetrics metrics = getOrCreate(key);
            metrics.incrementError5xxCount();
            return metrics;
        }
    }

    public APIMetrics incrementTotalExecutionTime(APIMetricsKey key, long executionTime) {
        synchronized (allMetrics) {
            APIMetrics metrics = getOrCreate(key);
            metrics.incrementTotalExecutionTime(executionTime);
            return metrics;
        }
    }

    public APIMetrics updateMaxExecutionTimeIfGreater(APIMetricsKey key, long executionTime) {
        synchronized (allMetrics) {
            APIMetrics metrics = getOrCreate(key);
            metrics.updateMaxExecutionTimeIfGreater(executionTime);
            return metrics;
        }
    }

    public APIMetrics updateMaxRequestSizeIfGreater(APIMetricsKey key, long requestSize) {
        synchronized (allMetrics) {
            APIMetrics metrics = getOrCreate(key);
            metrics.updateMaxRequestSizeIfGreater(requestSize);
            return metrics;
        }
    }

    public APIMetrics updateMaxResponseSizeIfGreater(APIMetricsKey key, long responseSize) {
        synchronized (allMetrics) {
            APIMetrics metrics = getOrCreate(key);
            metrics.updateMaxResponseSizeIfGreater(responseSize);
            return metrics;
        }
    }

    public List<APIMetrics> getAllMetricsThenReset() {
        synchronized (allMetrics) {
            // clone metrics (to make a snapshot and because the current metrics will be reset)
            List<APIMetrics> allMetricsSnapshot = new ArrayList<>(allMetrics.size());
            for (APIMetrics metrics : allMetrics.values()) {
                allMetricsSnapshot.add(new APIMetrics(metrics));
            }

            // reset metrics
            allMetrics.clear();

            return allMetricsSnapshot;
        }
    }

    private APIMetrics getOrCreate(APIMetricsKey key) {
        synchronized (allMetrics) {
            APIMetrics metrics = allMetrics.get(key);
            if (metrics == null) {
                metrics = new APIMetrics(key);
                allMetrics.put(key, metrics);
            }
            return metrics;
        }
    }
}
