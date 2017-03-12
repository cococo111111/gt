package com.fu.database.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by manlm on 9/17/2016.
 */
@Entity
@Table(name = "area")
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "weight")
    @NotNull
    private int weight;

    @Column(name = "beaconId1")
    private int beaconId1;

    @Column(name = "beaconId2")
    private int beaconId2;

    @Column(name = "beaconId3")
    private int beaconId3;

    @Column(name = "beaconId4")
    private int beaconId4;

    @Column(name = "floorId")
    private int floorId;

    @Column(name = "lastUpdate")
    private long lastUpdate;

    public Area() {
        // Default Constructor
    }


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

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getBeaconId1() {
        return beaconId1;
    }

    public void setBeaconId1(int beaconId1) {
        this.beaconId1 = beaconId1;
    }

    public int getBeaconId2() {
        return beaconId2;
    }

    public void setBeaconId2(int beaconId2) {
        this.beaconId2 = beaconId2;
    }

    public int getBeaconId3() {
        return beaconId3;
    }

    public void setBeaconId3(int beaconId3) {
        this.beaconId3 = beaconId3;
    }

    public int getBeaconId4() {
        return beaconId4;
    }

    public void setBeaconId4(int beaconId4) {
        this.beaconId4 = beaconId4;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }
}
