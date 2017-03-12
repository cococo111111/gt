package com.fu.bom.service;

import com.fu.database.entity.Beacon;

import java.util.List;


/**
 * Created by PhucNT on 10/27/2016.
 */
public interface BeaconService {

    List<Beacon> getAllBeacon();

    boolean deleteBeacon(int beaconId);
}
