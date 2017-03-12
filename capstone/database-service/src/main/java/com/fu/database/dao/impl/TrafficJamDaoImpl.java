package com.fu.database.dao.impl;

import com.fu.database.dao.TrafficJamDao;
import com.fu.database.entity.TrafficJam;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 15/02/2017.
 */
@Repository
public class TrafficJamDaoImpl extends GenericDaoImpl<TrafficJam,Integer> implements TrafficJamDao {

    private static final Logger LOG = Logger.getLogger(LawDaoImpl.class);

    @Override
    public List<TrafficJam> getTrafficJamByName(String nameLocation) {
        LOG.info("[getProductBySearchName] Start: name = " + nameLocation);
        List<TrafficJam> trafficJam = getEntityManager().createQuery("SELECT t FROM TrafficJam t WHERE t.name LIKE :name  ORDER BY t.name desc", TrafficJam.class)
                .setParameter("name", "%"+nameLocation.toLowerCase()+"%")
                .getResultList();
        return trafficJam;
    }
}
