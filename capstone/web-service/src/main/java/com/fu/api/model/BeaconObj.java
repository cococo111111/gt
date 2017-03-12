package com.fu.api.model;

import com.fu.database.entity.*;

/**
 * Created by manlm on 11/11/2016.
 */
public class BeaconObj {

    private String username;

    private Beacon beacon;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Beacon getBeacon() {
        return beacon;
    }

    public void setBeacon(Beacon beacon) {
        this.beacon = beacon;
    }
}
