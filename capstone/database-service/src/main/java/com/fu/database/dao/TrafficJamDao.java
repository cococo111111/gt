package com.fu.database.dao;

import com.fu.database.entity.TrafficJam;

import java.util.List;

/**
 * Created by Administrator on 15/02/2017.
 */
public interface TrafficJamDao extends GenericDao<TrafficJam,Integer> {
    List<TrafficJam> getTrafficJamByName(String nameLocation);
}
