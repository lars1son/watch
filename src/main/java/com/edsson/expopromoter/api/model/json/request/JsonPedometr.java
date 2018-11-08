package com.edsson.expopromoter.api.model.json.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonPedometr {

    long allStepCount;
    long todayStepCount;
    long averageStepCount;


    public JsonPedometr(long allStepCount, long todayStepCount, long averageStepCount) {
        this.allStepCount = allStepCount;
        this.todayStepCount = todayStepCount;
        this.averageStepCount = averageStepCount;
    }

    public long getAllStepCount() {
        return allStepCount;
    }

    public long getTodayStepCount() {
        return todayStepCount;
    }

    public long getAvarageStepCount() {
        return averageStepCount;
    }
}
