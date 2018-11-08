package com.edsson.expopromoter.api.model.json.request;

import com.edsson.expopromoter.api.model.Measures;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonMeasuredData {
    private String measuredData;

    public JsonMeasuredData(Measures measures) {
        this.measuredData = measures.getMeasures();
    }

    public static JsonMeasuredData createFromDao(Measures measures) {
        return new JsonMeasuredData(measures);
    }

}
