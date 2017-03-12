package com.fu.api.service;

import com.fu.api.model.Beacon;
import com.fu.api.model.BeaconObj;

/**
 * Created by manlm on 11/11/2016.
 */
public interface BeaconService {

    String generateInfo();

    Beacon getInfo(String macAddress);

    boolean insert(BeaconObj beaconObj);

    int update(BeaconObj beaconObj);
}
