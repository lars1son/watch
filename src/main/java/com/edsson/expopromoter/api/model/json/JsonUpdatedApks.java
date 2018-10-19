package com.edsson.expopromoter.api.model.json;


import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class JsonUpdatedApks {
    List<String> names;


    public JsonUpdatedApks(List<String> names) {
        this.names = names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

}
