package com.edsson.expopromoter.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
@Entity
@Table(name = "apk")

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Apk extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name="path")
    private String path;


    @NotNull
    @Column(name="version")
    private long version;

    public Apk() {
    }

    public Apk(String name, String path, long version) {
        this.name = name;
        this.path = path;
        this.version = version;
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Apk{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", version=" + version +
                '}';
    }
}
