package com.fu.api.service;

import com.fu.api.model.AreaObj;
import com.fu.database.entity.Area;

import java.util.List;

/**
 * Created by manlm on 11/16/2016.
 */
public interface AreaService {

    List<Area> getAll();

    com.fu.api.model.Area getInfo(String name);

    boolean insert(AreaObj areaObj);

    int update(AreaObj areaObj);
}
