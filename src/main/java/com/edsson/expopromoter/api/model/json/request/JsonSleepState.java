package com.edsson.expopromoter.api.model.json.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonSleepState {
    Double duration;
    Double deepSleepDuration;
    Double fastSleepDuration;
    Double lightSleep;


    public JsonSleepState(Double duration, Double deepSleepDuration, Double fastSleepDuration, Double lightSleep) {
        this.duration = duration;
        this.deepSleepDuration = deepSleepDuration;
        this.fastSleepDuration = fastSleepDuration;
        this.lightSleep = lightSleep;
    }

    public Double getDuration() {
        return duration;
    }

    public Double getDeepSleepDuration() {
        return deepSleepDuration;
    }

    public Double getFastSleepDuration() {
        return fastSleepDuration;
    }

    public Double getLightSleep() {
        return lightSleep;
    }
}
