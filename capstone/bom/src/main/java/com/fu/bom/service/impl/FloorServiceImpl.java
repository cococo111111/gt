package com.fu.bom.service.impl;

import com.fu.bom.service.FloorService;
import com.fu.database.dao.AreaDao;
import com.fu.database.dao.FloorDao;
import com.fu.database.dao.ProductDao;
import com.fu.database.entity.Area;
import com.fu.database.entity.Floor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 07/11/2016.
 */
@Service
public class FloorServiceImpl implements FloorService {

    private final FloorDao floorDao;

    private final ProductDao productDao;

    private final AreaDao areaDao;

    private static final Logger LOG = Logger.getLogger(AreaServiceImpl.class);

    public FloorServiceImpl(FloorDao floorDao, ProductDao productDao, AreaDao areaDao) {
        this.floorDao = floorDao;
        this.productDao = productDao;
        this.areaDao = areaDao;
    }

    @Override
    public boolean addFloor(String name) {
        LOG.info("[addFloor] Start: name = " + name);
        if (floorDao.checkDuplicateName(name).isEmpty()) {
            Floor floor = new Floor();
            floor.setName(name);
            try {
                floorDao.insert(floor);
                LOG.info("[addFloor] End");
                return true;
            } catch (RuntimeException ex) {
                LOG.error("[addFloor] RuntimeException: ", ex);
            }
        }
        LOG.info("[addFloor] End");
        return false;
    }

    @Override
    public boolean updateFloor(int id, String name) {
        LOG.info("[updateFloor] Start: name = " + name);
        Floor floor = floorDao.getById(id);
        if (floor.getName().equals(name)) {
            return true;
        } else {
            if (floorDao.checkDuplicateName(name).isEmpty()) {
                try {
                    floor.setName(name);
                    floorDao.update(floor);
                    List<Area> list = areaDao.getAreaByChangeFloor(id);
                    for(int i=0;i<list.size();i++){
                        productDao.updateFloorInProduct(list.get(i),name);
                    }
                    return true;
                } catch (RuntimeException ex) {
                    LOG.error("[RuntimeException] Error ", ex);
                }
                LOG.info("[updateFloor] End");
                return false;
            }
        }
        LOG.info("[updateFloor] End");
        return false;
    }

    @Override
    public boolean deleteFloor(int floorId) {
        LOG.info("[deleteFloor] Start: floorId = " + floorId);
        try {
            List<Area> listArea=floorDao.checkMappingFloorInArea(floorId);
            if(listArea.isEmpty()){
                floorDao.deleteFloorInBeacon(floorId);
                floorDao.deleteFloor(floorId);
                LOG.info("[deleteFloor] End");
                return true;
            }else{
                LOG.info("[deleteFloor] End");
                return false;
            }
        } catch (RuntimeException ex) {
            LOG.error("[deleteFloor] RuntimeException: ", ex);
        }
        LOG.info("[deleteFloor] End");
        return false;
    }

    @Override
    public List<Floor> getAll() {
        LOG.info("[getAll] Start:");
        LOG.info("[getAll] End");
        return floorDao.getAllFloor();
    }
}
