package com.edsson.expopromoter.api.model;

import com.edsson.expopromoter.api.model.json.request.JsonData;
import com.edsson.expopromoter.api.model.json.request.JsonMeasuredData;
import com.edsson.expopromoter.api.model.json.response.JsonMeasures;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
@Entity
@Table(name = "measured_data")
public class Measures {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "data")
    private String measures;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="watch_id")
    private Watch watch;


    public Measures() {
    }

    public
    Measures(JsonData jsonData){
      this.measures = jsonData.getMeasuredData();
    }

    public String getMeasures() {
        return measures;
    }

    public void setMeasures(String measures) {
        this.measures = measures;
    }

    public Watch getWatch() {
        return watch;
    }

    public void setWatch(Watch watch) {
        this.watch = watch;
    }
}
