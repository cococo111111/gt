package com.fu.api.service.impl;

import com.fu.api.model.AreaObj;
import com.fu.api.service.AreaService;
import com.fu.database.dao.AreaDao;
import com.fu.database.dao.BeaconDao;
import com.fu.database.dao.FloorDao;
import com.fu.database.dao.ProductDao;
import com.fu.database.entity.Area;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by manlm on 11/16/2016.
 */
@Service
public class AreaServiceImpl implements AreaService {

    private static final Logger LOG = Logger.getLogger(AreaServiceImpl.class);

    private final AreaDao areaDao;

    private final ProductDao productDao;

    private final BeaconDao beaconDao;

    private final FloorDao floorDao;

    @Autowired
    public AreaServiceImpl(AreaDao areaDao, ProductDao productDao, BeaconDao beaconDao, FloorDao floorDao) {
        this.areaDao = areaDao;
        this.productDao = productDao;
        this.beaconDao = beaconDao;
        this.floorDao = floorDao;
    }

    @Override
    public List<Area> getAll() {
        LOG.info("[getAll] Start");
        LOG.info("[getAll] End");
        return areaDao.getAll();
    }

    @Override
    public com.fu.api.model.Area getInfo(String name) {
        LOG.info("[getInfo] Start: name = " + name);
        Area a = areaDao.getAreaByName(name);
        com.fu.api.model.Area area = new com.fu.api.model.Area();
        area.setId(a.getId());
        area.setName(a.getName());
        area.setWeight(a.getWeight());
        area.setBeaconMinor1(beaconDao.getById(a.getBeaconId1()).getMinor());
        area.setBeaconMinor2(beaconDao.getById(a.getBeaconId2()).getMinor());
        area.setBeaconMinor3(beaconDao.getById(a.getBeaconId3()).getMinor());
        area.setBeaconMinor4(beaconDao.getById(a.getBeaconId4()).getMinor());
        area.setFloorId(a.getFloorId());
        area.setFloorName(floorDao.getById(a.getFloorId()).getName());
        area.setLastUpdate(a.getLastUpdate());
        LOG.info("[getInfo] End");
        return area;
    }

    @Override
    public boolean insert(AreaObj areaObj) {
        LOG.info("[insert] Start");
        List<Area> areaList = areaDao.checkDuplicateName(areaObj.getName());
        if (areaList.isEmpty()) {
            Area area = new Area();
            area.setName(areaObj.getName());
            area.setWeight(areaObj.getWeight());
            area.setBeaconId1(beaconDao.getBeaconByMinor(areaObj.getBeaconMinor1()).get(0).getId());
            area.setBeaconId2(beaconDao.getBeaconByMinor(areaObj.getBeaconMinor2()).get(0).getId());
            area.setBeaconId3(beaconDao.getBeaconByMinor(areaObj.getBeaconMinor3()).get(0).getId());
            area.setBeaconId4(beaconDao.getBeaconByMinor(areaObj.getBeaconMinor4()).get(0).getId());
            area.setFloorId(areaObj.getFloorId());
            area.setLastUpdate(System.currentTimeMillis());
            areaDao.insert(area);
            LOG.info("[insert] End");
            return true;
        }
        LOG.info("[insert] End");
        return false;
    }

    @Override
    public int update(AreaObj areaObj) {
        LOG.info("[update] Start");
        List<Area> areaList = areaDao.checkDuplicateName(areaObj.getName());

        if (!areaObj.getName().equals(areaObj.getOldName())) {
            if (!areaList.isEmpty()) {
                LOG.info("[update] End: duplicate");
                return -1;
            }
        }

        areaList = areaDao.checkDuplicateName(areaObj.getOldName());
        if (areaList.isEmpty()) {
            LOG.info("[update] End: not found");
            return 0;
        }

        Area area = areaList.get(0);
        area.setName(areaObj.getName());
        area.setWeight(areaObj.getWeight());
        area.setBeaconId1(beaconDao.getBeaconByMinor(areaObj.getBeaconMinor1()).get(0).getId());
        area.setBeaconId2(beaconDao.getBeaconByMinor(areaObj.getBeaconMinor2()).get(0).getId());
        area.setBeaconId3(beaconDao.getBeaconByMinor(areaObj.getBeaconMinor3()).get(0).getId());
        area.setBeaconId4(beaconDao.getBeaconByMinor(areaObj.getBeaconMinor4()).get(0).getId());
        area.setFloorId(areaObj.getFloorId());
        area.setLastUpdate(System.currentTimeMillis());

        areaDao.update(area);
        productDao.updateAreaInProduct(area, floorDao.getById(area.getFloorId()).getName());

        LOG.info("[update] End");
        return 1;
    }
}
