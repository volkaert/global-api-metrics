package fr.volkaert.global_api_metrics.spring_gtw;

import java.util.Objects;

public class APIMetricsKey {

    private final String referentialCode;   // mandatory
    private final String apiCode;           // mandatory
    private String apiName;                 // optional

    public APIMetricsKey(String referentialCode, String apiCode, String apiName) {
        Objects.requireNonNull(referentialCode, "referentialCode cannot be null");
        Objects.requireNonNull(apiCode, "apiCode cannot be null");
        this.referentialCode = referentialCode;
        this.apiCode = apiCode;
        this.apiName = apiName;
    }

    public APIMetricsKey(String referentialCode, String apiCode) {
        Objects.requireNonNull(referentialCode, "referentialCode cannot be null");
        Objects.requireNonNull(apiCode, "apiCode cannot be null");
        this.referentialCode = referentialCode;
        this.apiCode = apiCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        APIMetricsKey that = (APIMetricsKey) o;
        return referentialCode.equals(that.referentialCode) &&
                apiCode.equals(that.apiCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(referentialCode, apiCode);
    }

    public String getReferentialCode() {
        return referentialCode;
    }

    public String getApiCode() {
        return apiCode;
    }

    public String getApiName() {
        return apiName;
    }
}
