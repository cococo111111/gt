package com.fu.database.dao.impl;

import com.fu.database.dao.BeaconDao;
import com.fu.database.dao.FloorDao;
import com.fu.database.entity.Area;
import com.fu.database.entity.Beacon;
import com.fu.database.entity.Floor;
import com.fu.database.model.BeaconApi;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by manlm on 9/21/2016.
 */
@Repository
public class BeaconDaoImpl extends GenericDaoImpl<Beacon, Integer> implements BeaconDao {

    private static final Logger LOG = Logger.getLogger(BeaconDaoImpl.class);

    private final FloorDao floorDao;

    @Autowired
    public BeaconDaoImpl(FloorDao floorDao) {
        this.floorDao = floorDao;
    }

    @Override
    public List<BeaconApi> getBeaconForApi() {
        LOG.info("[getBeaconForApi] Start");

        List<Beacon> beaconList = getEntityManager().createQuery("SELECT b FROM Beacon b", Beacon.class)
                .getResultList();

        List<BeaconApi> beaconApiList = new ArrayList<>();
        BeaconApi beaconApi;
        String floorName;
        Floor floor;
        for (Beacon beacon : beaconList) {
            beaconApi = new BeaconApi();
            beaconApi.setId(beacon.getId());
            beaconApi.setMacAddress(beacon.getMacAddress());
            beaconApi.setUuid(beacon.getUuid());
            beaconApi.setMajor(beacon.getMajor());
            beaconApi.setMinor(beacon.getMinor());
            beaconApi.setX(beacon.getX());
            beaconApi.setY(beacon.getY());
            beaconApi.setZ(beacon.getZ());
            beaconApi.setFloorId(beacon.getFloorId());
            floor = floorDao.getById(beacon.getFloorId());
            if (floor != null) {
                beaconApi.setFloorName(floor.getName());
            } else {
                beaconApi.setFloorName("");
            }
            beaconApiList.add(beaconApi);
        }

        LOG.info("[getBeaconForApi] End");
        return beaconApiList;
    }

    @Override
    public List<Beacon> getAllBeacon() {
        LOG.info("[getAllBeacon] Start");
        Query query = getEntityManager().createQuery("FROM " + Beacon.class.getSimpleName());
        LOG.info("[getAllBeacon] End");
        return query.getResultList();
    }

    @Override
    public List<Beacon> getAllBeaconSortByMinor() {
        LOG.info("[getAllBeaconSortByMinor] Start");
        Query query = getEntityManager().createQuery("FROM " + Beacon.class.getSimpleName() + " ORDER BY minor ASC");
        LOG.info("[getAllBeaconSortByMinor] End");
        return query.getResultList();
    }

    @Override
    public boolean deleteBeacon(int beaconId) {
        LOG.info("[delete] Start");
        Query query = getEntityManager().createQuery("DELETE FROM " + Beacon.class.getSimpleName() + " b WHERE b.id=:beaconId");
        query.setParameter("beaconId", beaconId);
        query.executeUpdate();
        LOG.info("[delete] End");
        return true;
    }

    @Override
    public boolean findAreaMinor(int minor) {
        LOG.info("[findAreaMinor] Start");
        Query query = getEntityManager().createQuery("FROM " + Area.class.getSimpleName() + " a WHERE a.beaconMinor1=:minor OR" +
                " a.beaconMinor2=:minor OR a.beaconMinor3=:minor OR a.beaconMinor4=:minor");
        query.setParameter("minor", minor);
        List<Area> areas = query.getResultList();
        if (!areas.isEmpty()) {
            LOG.info("[findAreaMinor] End");
            return true;
        }
        LOG.info("[findAreaMinor] End");
        return false;

    }

    @Override
    public List<Beacon> getBeaconByMinor(int minor) {
        LOG.info("[getBeaconByMinor] Start");
        Query query = getEntityManager().createQuery("FROM " + Beacon.class.getSimpleName() + " b WHERE b.minor=:minor");
        query.setParameter("minor", minor);
        LOG.info("[getBeaconByMinor] End");
        return query.getResultList();
    }

    @Override
    public List<Beacon> getBeaconByMac(String macAddress) {
        LOG.info("[getBeaconByMac] Start");
        Query query = getEntityManager().createQuery("FROM " + Beacon.class.getSimpleName() + " b WHERE b.macAddress=:macAddress");
        query.setParameter("macAddress", macAddress);
        LOG.info("[getBeaconByMac] End");
        return query.getResultList();
    }

    @Override
    public List<Area> checkMappingBeaconInArea(int beaconId) {
        LOG.info("[checkMappingBeaconInArea] Start:");
        LOG.info("[checkMappingBeaconInArea] End");
        return getEntityManager()
                .createQuery("SELECT a FROM Area a WHERE a.beaconId1=:beaconId OR a.beaconId2=:beaconId OR a.beaconId3=:beaconId OR a.beaconId4=:beaconId", Area.class)
                .setParameter("beaconId", beaconId)
                .getResultList();
    }
}
