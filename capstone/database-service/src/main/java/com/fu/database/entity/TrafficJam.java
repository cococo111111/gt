package com.fu.database.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 15/02/2017.
 */
@Entity
@Table(name = "trafficjam")
public class TrafficJam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "detailJam")
    @NotNull
    private String detailJam;

    @Column(name = "detailJamReal")
    private String detailJamReal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetailJam() {
        return detailJam;
    }

    public void setDetailJam(String detailJam) {
        this.detailJam = detailJam;
    }

    public String getDetailJamReal() {
        return detailJamReal;
    }

    public void setDetailJamReal(String detailJamReal) {
        this.detailJamReal = detailJamReal;
    }
}
