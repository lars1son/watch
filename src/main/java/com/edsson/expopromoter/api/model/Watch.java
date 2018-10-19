package com.edsson.expopromoter.api.model;


import com.edsson.expopromoter.api.model.json.JsonWatch;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
@Entity
@Table(name = "watch")
public class Watch extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Column(name = "ip")
    private String ip;

    @NotNull
    @Column(name = "group")
    private int group;


    public Watch() {
    }

    public Watch(JsonWatch jsonWatch) {
        this.ip = jsonWatch.getIp();
        this.group = jsonWatch.getGroup();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Watch{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
