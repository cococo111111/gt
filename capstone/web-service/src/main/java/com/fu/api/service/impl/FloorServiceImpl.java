package com.fu.api.service.impl;

import com.fu.api.service.FloorService;
import com.fu.database.dao.FloorDao;
import com.fu.database.entity.Floor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by manlm on 11/14/2016.
 */
@Service
public class FloorServiceImpl implements FloorService {

    private static final Logger LOG = Logger.getLogger(FloorServiceImpl.class);

    private final FloorDao floorDao;

    @Autowired
    public FloorServiceImpl(FloorDao floorDao) {
        this.floorDao = floorDao;
    }

    @Override
    public List<Floor> getAll() {
        LOG.info("[getAll] Start");
        LOG.info("[getAll] End");
        return floorDao.getAllFloor();
    }
}
