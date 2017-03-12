package com.fu.api.service.impl;

import com.fu.api.model.BeaconObj;
import com.fu.api.service.BeaconService;
import com.fu.database.dao.BeaconDao;
import com.fu.database.dao.FloorDao;
import com.fu.database.entity.Beacon;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by manlm on 11/11/2016.
 */
@Service
public class BeaconServiceImpl implements BeaconService {

    private static final Logger LOG = Logger.getLogger(BeaconServiceImpl.class);

    private final BeaconDao beaconDao;

    private final FloorDao floorDao;

    @Autowired
    public BeaconServiceImpl(BeaconDao beaconDao, FloorDao floorDao) {
        this.beaconDao = beaconDao;
        this.floorDao = floorDao;
    }

    @Override
    public String generateInfo() {
        LOG.info("[generateInfo] Start");
        List<Beacon> beacons = beaconDao.getAllBeaconSortByMinor();
        boolean flag = false;
        int i = 0;
        int size = beacons.size() - 1;
        int current;
        int after;
        String result = String.valueOf(size + 2);
        while (!flag && i < size) {
            current = beacons.get(i).getMinor();
            after = beacons.get(i + 1).getMinor();
            if (after - current > 1) {
                flag = true;
                result = String.valueOf(current + 1);
            } else {
                i++;
            }
        }
        LOG.info("[generateInfo] End");
        return result;
    }

    @Override
    public com.fu.api.model.Beacon getInfo(String macAddress) {
        LOG.info("[getInfo] Start: macAddress = " + macAddress);
        List<Beacon> beaconList = beaconDao.getBeaconByMac(macAddress);
        if (!beaconList.isEmpty()) {
            Beacon b = beaconList.get(0);
            com.fu.api.model.Beacon beacon = new com.fu.api.model.Beacon();
            beacon.setMacAddress(b.getMacAddress());
            beacon.setUuid(b.getUuid());
            beacon.setMajor(b.getMajor());
            beacon.setMinor(b.getMinor());
            beacon.setX(b.getX());
            beacon.setY(b.getY());
            beacon.setZ(b.getZ());
            beacon.setFloorId(b.getFloorId());
            if (b.getFloorId() == 0) {
                beacon.setFloorName("");
            } else {
                beacon.setFloorName(floorDao.getById(b.getFloorId()).getName());
            }

            LOG.info("[getInfo] End");
            return beacon;
        }
        LOG.info("[getInfo] End");
        return new com.fu.api.model.Beacon();
    }

    @Override
    public boolean insert(BeaconObj beaconObj) {
        LOG.info("[insert] Start");

        com.fu.api.model.Beacon b = beaconObj.getBeacon();
        if (beaconDao.getBeaconByMac(b.getMacAddress()).isEmpty()) {
            Beacon beacon = new Beacon();
            beacon.setMacAddress(b.getMacAddress());
            beacon.setUuid(b.getUuid());
            beacon.setMajor(b.getMajor());
            beacon.setMinor(b.getMinor());
            beacon.setX(b.getX());
            beacon.setY(b.getY());
            beacon.setZ(b.getZ());
            beacon.setFloorId(b.getFloorId());
            beacon.setLastUpdate(System.currentTimeMillis());
            beaconDao.insert(beacon);

            LOG.info("[insert] End");
            return true;
        }

        LOG.info("[insert] End");
        return false;
    }

    @Override
    public int update(BeaconObj beaconObj) {
        LOG.info("[update] Start");

        com.fu.api.model.Beacon b = beaconObj.getBeacon();

        List<Beacon> beaconList = beaconDao.getBeaconByMac(beaconObj.getBeacon().getMacAddress());
        if (beaconList.isEmpty()) {
            LOG.info("[update] End: not found");
            return 0;
        }

        Beacon beacon = beaconList.get(0);
        beacon.setUuid(b.getUuid());
        beacon.setMajor(b.getMajor());
        beacon.setMinor(b.getMinor());
        beacon.setX(b.getX());
        beacon.setY(b.getY());
        beacon.setZ(b.getZ());
        beacon.setFloorId(b.getFloorId());
        beacon.setLastUpdate(System.currentTimeMillis());
        beaconDao.update(beacon);

        LOG.info("[update] End");
        return 1;
    }
}
