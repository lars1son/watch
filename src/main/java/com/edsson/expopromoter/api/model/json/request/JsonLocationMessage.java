package com.edsson.expopromoter.api.model.json.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonLocationMessage {
    String watchId;
    JsonLocation location;

    public JsonLocationMessage() {
    }

    public String getWatchId() {
        return watchId;
    }

    public JsonLocation getLocation() {
        return location;
    }
}
