package com.edsson.expopromoter.api.model;




import com.edsson.expopromoter.api.model.json.request.JsonLocation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
@Entity
@Table(name = "location")
public class Location extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "latitude")
    private double latitude;

    @NotNull
    @Column(name = "longitude")
    private double longitude;

    @NotNull
    @Column(name = "provider")
    private String provider;


    @NotNull
    @Column(name = "bearing")
    private double bearing;

    @NotNull
    @Column(name = "time")
    private  Long time;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="watch_id")
    private Watch watch;

    public Location() {
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Watch getWatch() {
        return watch;
    }

    public Location(JsonLocation jsonLocation) {
        this.longitude = jsonLocation.getLongitude();
        this.latitude = jsonLocation.getLatitude();
        this.provider = jsonLocation.getProvider();
        this.bearing = jsonLocation.getBearing();
        this.time = jsonLocation.getTime();

    }

    public void setWatch(Watch watch) {
        this.watch = watch;
    }

    public Long getId() {
        return id;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public double getBearing() {
        return bearing;
    }

    public void setBearing(double bearing) {
        this.bearing = bearing;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
