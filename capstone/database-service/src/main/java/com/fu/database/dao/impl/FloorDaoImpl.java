package com.fu.database.dao.impl;

import com.fu.database.dao.FloorDao;
import com.fu.database.entity.Area;
import com.fu.database.entity.Floor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 06/11/2016.
 */
@Repository
public class FloorDaoImpl extends GenericDaoImpl<Floor, Integer> implements FloorDao {

    private static final Logger LOG = Logger.getLogger(FloorDaoImpl.class);

    @Override
    public List<Floor> getAllFloor() {
        LOG.info("[getAllFloor] Start");
        LOG.info("[getAllFloor] End");
        return getEntityManager().createQuery("FROM " + Floor.class.getSimpleName()).getResultList();
    }

    @Override
    public List<Floor> checkDuplicateName(String name) {
        LOG.info("[checkDuplicateName] Start:");
        LOG.info("[checkDuplicateName] End");
        return getEntityManager()
                .createQuery("SELECT f FROM Floor f WHERE f.name=:name", Floor.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public void deleteFloor(int id) {
        LOG.info("[deleteFloor] Start:");
        LOG.info("[deleteFloor] End");
        getEntityManager().createQuery("DELETE FROM Floor f " +
                "WHERE f.id=:id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public List<Area> checkMappingFloorInArea(int floorId) {
        LOG.info("[checkMappingFloorInArea] Start:");
        LOG.info("[checkMappingFloorInArea] End");
        return getEntityManager()
                .createQuery("SELECT a FROM Area a WHERE a.floorId=:floorId", Area.class)
                .setParameter("floorId", floorId)
                .getResultList();
    }

    @Override
    public void deleteFloorInBeacon(int floorId) {
        LOG.info("[deleteFloorInBeacon] Start:");
        getEntityManager().createQuery("UPDATE Beacon b SET b.floorId=0 WHERE b.floorId=:floorId")
                .setParameter("floorId", floorId)
                .executeUpdate();
        LOG.info("[deleteFloorInBeacon] End");
    }
}
