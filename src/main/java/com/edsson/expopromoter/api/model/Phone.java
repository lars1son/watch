package com.edsson.expopromoter.api.model;

import com.edsson.expopromoter.api.model.json.JsonPhone;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
@Entity
@Table(name = "phone")
public class Phone extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @NotNull
    @Column(name = "ip")
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Phone(JsonPhone jsonPhone) {
        this.ip = jsonPhone.getIp();
    }
}
