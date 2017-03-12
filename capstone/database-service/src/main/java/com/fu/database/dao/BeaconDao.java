package com.fu.database.dao;

import com.fu.database.entity.Area;
import com.fu.database.entity.Beacon;
import com.fu.database.model.BeaconApi;

import java.util.List;

/**
 * Created by manlm on 9/21/2016.
 */
public interface BeaconDao extends GenericDao<Beacon, Integer> {

    List<BeaconApi> getBeaconForApi();

    List<Beacon> getAllBeacon();

    List<Beacon> getAllBeaconSortByMinor();

    boolean deleteBeacon(int beaconId);

    boolean findAreaMinor(int minor);

    List<Beacon> getBeaconByMinor(int minor);

    List<Beacon> getBeaconByMac(String macAddress);

    List<Area> checkMappingBeaconInArea(int beaconId);


}
