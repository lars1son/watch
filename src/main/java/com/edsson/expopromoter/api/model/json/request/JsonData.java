package com.edsson.expopromoter.api.model.json.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonData {
    String watchId;
    String measuredData;

    public String getWatchId() {
        return watchId;
    }

    public String getMeasuredData() {
        return measuredData;
    }
}
