package com.fu.bom.service.impl;

import com.fu.bom.service.AreaService;
import com.fu.database.dao.AreaDao;
import com.fu.database.dao.ProductDao;
import com.fu.database.entity.Area;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 25/10/2016.
 */
@Service
public class AreaServiceImpl implements AreaService {

    private final AreaDao areaDao;

    private final ProductDao productDao;

    private static final Logger LOG = Logger.getLogger(AreaServiceImpl.class);

    @Autowired
    public AreaServiceImpl(AreaDao areaDao, ProductDao productDao) {
        this.areaDao = areaDao;

        this.productDao = productDao;
    }

    @Override
    public List<Area> getAllArea() {
        LOG.info("[getAllArea] Start:");
        LOG.info("[getAllArea] End");
        return areaDao.getAll();
    }

    @Override
    public Area getArea(String name) {
        LOG.info("[getArea] Start:");
        LOG.info("[getArea] End");
        return areaDao.getAreaByName(name);
    }

    @Override
    public boolean deleteArea(int id) {
        LOG.info("[deleteArea] Start:");
        Area area = areaDao.getById(id);

        try {
            productDao.deleteAreaInProduct(area);
            areaDao.deleteArea(id);
            LOG.info("[deleteArea] End");
            return true;
        } catch (RuntimeException ex) {
            LOG.error("[deleteArea] RuntimeException: ", ex);
        }
        LOG.info("[deleteArea] End");
        return false;
    }
}
