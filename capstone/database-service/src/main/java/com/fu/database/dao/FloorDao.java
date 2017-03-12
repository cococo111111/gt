package com.fu.database.dao;

import com.fu.database.entity.Area;
import com.fu.database.entity.Floor;

import java.util.List;

/**
 * Created by Administrator on 06/11/2016.
 */
public interface FloorDao extends GenericDao<Floor, Integer>{

    List<Floor> getAllFloor();

    List<Floor> checkDuplicateName(String name);

    void deleteFloor(int id);

    List<Area> checkMappingFloorInArea(int floorId);

    void deleteFloorInBeacon(int floorId);

}
