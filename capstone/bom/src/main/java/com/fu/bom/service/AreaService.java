package com.fu.bom.service;

import com.fu.database.entity.Area;

import java.util.List;

/**
 * Created by Administrator on 25/10/2016.
 */
public interface AreaService {

    List<Area> getAllArea();

    Area getArea(String name);

    boolean deleteArea(int id);

}
