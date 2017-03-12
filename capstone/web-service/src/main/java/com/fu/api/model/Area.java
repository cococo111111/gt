package com.fu.api.model;

/**
 * Created by manlm on 9/17/2016.
 */
public class Area {

    private int id;

    private String name;

    private int weight;

    private int beaconMinor1;

    private int beaconMinor2;

    private int beaconMinor3;

    private int beaconMinor4;

    private int floorId;

    private String floorName;

    private long lastUpdate;

    public Area() {
        // Default Constructor
    }

    /**
     * Constructor
     *
     * @param name
     * @param weight
     * @param beaconMinor1
     * @param beaconMinor2
     * @param beaconMinor3
     * @param beaconMinor4
     */
    public Area(String name, int weight, int beaconMinor1, int beaconMinor2, int beaconMinor3, int beaconMinor4) {
        this.name = name;
        this.weight = weight;
        this.beaconMinor1 = beaconMinor1;
        this.beaconMinor2 = beaconMinor2;
        this.beaconMinor3 = beaconMinor3;
        this.beaconMinor4 = beaconMinor4;
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

    public int getBeaconMinor1() {
        return beaconMinor1;
    }

    public void setBeaconMinor1(int beaconMinor1) {
        this.beaconMinor1 = beaconMinor1;
    }

    public int getBeaconMinor2() {
        return beaconMinor2;
    }

    public void setBeaconMinor2(int beaconMinor2) {
        this.beaconMinor2 = beaconMinor2;
    }

    public int getBeaconMinor3() {
        return beaconMinor3;
    }

    public void setBeaconMinor3(int beaconMinor3) {
        this.beaconMinor3 = beaconMinor3;
    }

    public int getBeaconMinor4() {
        return beaconMinor4;
    }

    public void setBeaconMinor4(int beaconMinor4) {
        this.beaconMinor4 = beaconMinor4;
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

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }
}
