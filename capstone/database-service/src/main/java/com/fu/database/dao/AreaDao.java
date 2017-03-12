package com.fu.database.dao;

import com.fu.database.entity.Area;

import java.util.List;

/**
 * Created by manlm on 9/21/2016.
 */
public interface AreaDao extends GenericDao<Area, Integer> {

    List<Area> getAll();

    Area getAreaByName(String name);

    List<Area> checkDuplicateName(String name);

    void deleteArea(int id);

    void updateFloorInArea(int floorId, String floorName);

    List<Area> getAreaByChangeFloor(int floorId);

    void deleteFloorInArea(int floorId);
}
