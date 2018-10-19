package com.edsson.expopromoter.api.model.json;

import com.edsson.expopromoter.api.model.Apk;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class JsonApk {
    long version;
    String path;

    public JsonApk() {
    }

    public JsonApk(Apk apk) {
        this.version = apk.getVersion();
        this.path = apk.getPath().replace(".","-");
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void setPath(String path) {
        this.path = path;
    }
}