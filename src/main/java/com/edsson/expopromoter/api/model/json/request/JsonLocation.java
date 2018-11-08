package com.edsson.expopromoter.api.model.json.request;

import com.edsson.expopromoter.api.model.Location;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonLocation {

    double longitude;
    double latitude;
    String provider;
    double bearing;
    Long time;

    public JsonLocation() {
    }

    public JsonLocation(Location location) {
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
        this.provider = location.getProvider();
        this.bearing = location.getBearing();
        this.time = location.getTime();
    }

    public static JsonLocation createFromDao(Location location){
        return new JsonLocation(location);
    }



    public double getLatitude() {
        return latitude;
    }

    public String getProvider() {
        return provider;
    }

    public double getBearing() {
        return bearing;
    }

    public Long getTime() {
        return time;
    }

    public double getLongitude() {
        return longitude;
    }
}
