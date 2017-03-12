package com.fu.bom.service.impl;

import com.fu.bom.service.BeaconService;
import com.fu.database.dao.BeaconDao;
import com.fu.database.entity.Beacon;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by PhucNT on 10/27/2016.
 */
@Service
public class BeaconServiceImpl implements BeaconService {

    private final BeaconDao beaconDao;

    private static final Logger LOG = Logger.getLogger(AreaServiceImpl.class);

    @Autowired
    public BeaconServiceImpl(BeaconDao beaconDao) {
        this.beaconDao = beaconDao;
    }

    @Override
    public List<Beacon> getAllBeacon() {
        LOG.info("[getAllBeacon] Start:");
        LOG.info("[getAllBeacon] End");
        return beaconDao.getAllBeacon();
    }

    @Override
    public boolean deleteBeacon(int beaconId) {
        LOG.info("[deleteBeacon] Start:");
        if (beaconDao.checkMappingBeaconInArea(beaconId).isEmpty()) {
            beaconDao.deleteBeacon(beaconId);
        } else {
            LOG.info("[deleteBeacon] End");
            return false;
        }
        LOG.info("[deleteBeacon] End");
        return true;
    }
}
