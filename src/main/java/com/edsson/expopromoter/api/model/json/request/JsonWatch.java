package com.edsson.expopromoter.api.model.json.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonWatch {
    String ip;
//    Long group;
String name;
    public String getIp() {
        return ip;
    }

    public String getName() {
        return name;
    }

//    public Long getGroup() {
//        return group;
//    }

//    public void setGroup(Long group) {
//        this.group = group;
//    }
}
