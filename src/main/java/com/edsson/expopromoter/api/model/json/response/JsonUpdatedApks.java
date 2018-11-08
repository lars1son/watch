package com.edsson.expopromoter.api.model.json.response;


import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class JsonUpdatedApks {
    ArrayList<String> names;


    public JsonUpdatedApks(ArrayList<String> names) {

        this.names = setLauncherAtLastPos(names);
    }


    private ArrayList<String> setLauncherAtLastPos(ArrayList<String> nameList){
        Iterator iterator= nameList.iterator();
        String name=null;
        while (iterator.hasNext()){
           name = (String) iterator.next();
            if (name.trim().contains("launcher")){
//                nameList.add(name);
                iterator.remove();
                break;
            }
            name=null;
        }
        if (name!=null){
            nameList.add(name);
        }
        return nameList;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }

}
