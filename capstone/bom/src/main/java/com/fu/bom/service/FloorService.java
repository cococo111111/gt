package com.fu.bom.service;

import com.fu.database.entity.Floor;

import java.util.List;

/**
 * Created by Administrator on 07/11/2016.
 */
public interface FloorService {
    boolean addFloor(String name);

    boolean updateFloor(int id, String name);

    boolean deleteFloor(int id);

    List<Floor> getAll();

}
