package com.fu.database.dao.impl;

import com.fu.database.dao.AreaDao;
import com.fu.database.entity.Area;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by manlm on 9/21/2016.
 */
@Repository
public class AreaDaoImpl extends GenericDaoImpl<Area, Integer> implements AreaDao {

    private static final Logger LOG = Logger.getLogger(AreaDaoImpl.class);

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Area> getAll() {
        LOG.info("[getAll] Start");
        Query query = getEntityManager().createQuery("FROM " + Area.class.getSimpleName());
        LOG.info("[getAll] End");
        return query.getResultList();
    }

    @Override
    public Area getAreaByName(String name) {
        LOG.info("[getAreaByName] Start:");
        LOG.info("[getAreaByName] End");
        return getEntityManager()
                .createQuery("SELECT a FROM Area a WHERE a.name=:name", Area.class)
                .setParameter("name", name)
                .getSingleResult();

    }

    @Override
    public List<Area> checkDuplicateName(String name) {
        LOG.info("[checkDuplicateName] Start:");
        LOG.info("[checkDuplicateName] End");
        return getEntityManager()
                .createQuery("SELECT a FROM Area a WHERE a.name=:name", Area.class)
                .setParameter("name", name)
                .getResultList();

    }

    @Override
    public void deleteArea(int id) {
        LOG.info("[deleteArea] Start:");
        LOG.info("[deleteArea] End");
        getEntityManager().createQuery("DELETE FROM Area a " +
                "WHERE a.id=:id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void updateFloorInArea(int floorId, String floorName) {
        LOG.info("[updateFloorInArea] Start:");
        getEntityManager().createQuery("UPDATE Area a SET  a.floorName=:floorName WHERE a.floorId=:floorId")
                .setParameter("floorId", floorId)
                .setParameter("floorName", floorName).executeUpdate();
        LOG.info("[updateFloorInArea] End");

    }

    @Override
    public List<Area> getAreaByChangeFloor(int floorId) {
        LOG.info("[getAreaByChangeFloor] Start:");
        LOG.info("[getAreaByChangeFloor] End");
        return getEntityManager().createQuery("SELECT a FROM Area a WHERE a.floorId=:floorId")
                .setParameter("floorId", floorId)
                .getResultList();
    }

    @Override
    public void deleteFloorInArea(int floorId) {
        LOG.info("[deleteFloorInArea] Start:");
        getEntityManager().createQuery("UPDATE Area a SET a.floorId=:deleteFloor WHERE a.floorId=:floorId")
                .setParameter("floorId", floorId)
                .setParameter("deleteFloor", 0)
                .executeUpdate();
        LOG.info("[deleteFloorInArea] End");
    }
}
