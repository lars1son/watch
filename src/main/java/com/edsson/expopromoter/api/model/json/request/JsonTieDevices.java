package com.edsson.expopromoter.api.model.json.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonTieDevices {
    String watchIp;
    String phoneIp;

    public String getWatchIp() {
        return watchIp;
    }

    public String getPhoneIp() {
        return phoneIp;
    }
}
