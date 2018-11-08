package com.edsson.expopromoter.api.model.json.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class JsonUpdateApk {
    private String packageName;
    private int version;

    public String getPackageName() {
        return packageName;
    }

    public int getVersion() {
        return version;
    }
}
