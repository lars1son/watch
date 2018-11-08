package com.edsson.expopromoter.api.model.json.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public class JsonUpdateApkList {
   private List<JsonUpdateApk> modelList;

    public List<JsonUpdateApk> getModelList() {
        return modelList;
    }
}
