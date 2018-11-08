package com.edsson.expopromoter.api.model;


import com.edsson.expopromoter.api.model.json.request.JsonWatch;

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
    //
//    @NotNull
//    @Column(name = "group")
//    private Long group;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phone_id", nullable = true)
    private Phone phone;


    @NotNull
    @Column(name = "name")
    private String name;


    @OneToOne(mappedBy="watch", cascade = CascadeType.ALL)
    private Measures measure;

    @OneToOne(mappedBy="watch",cascade = CascadeType.ALL)
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Measures getMeasure() {
        return measure;
    }

    public void setMeasure(Measures measure) {
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Watch() {
    }

    public Watch(JsonWatch jsonWatch) {
        this.ip = jsonWatch.getIp();
        this.name = jsonWatch.getName();
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



    @Override
    public String toString() {
        return "Watch{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
//                ", group='" + group + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Watch watch = (Watch) o;

        return id == watch.getId();
    }


}
