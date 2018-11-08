package com.edsson.expopromoter.api.model;

import com.edsson.expopromoter.api.controller.AdministratorController;
import com.edsson.expopromoter.api.model.json.request.JsonPhone;
import org.apache.log4j.Logger;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

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


    @NotNull
    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Watch> getWatchList() {
        return watchList;
    }

    public void setWatchList(List<Watch> watchList) {
        this.watchList = watchList;
    }

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
        this.name = jsonPhone.getName();
    }

    public Phone() {
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "phone")
    private List<Watch> watchList;

    public boolean addWatch(Watch watch) {
        if (!watchList.contains(watch)) {
            watchList.add(watch);
            return true;
        }
        return false;
    }
}
