package com.fu.database.dao;

import com.fu.database.entity.Gas;

import java.util.List;

/**
 * Created by Administrator on 14/02/2017.
 */
public interface GasDao  extends GenericDao<Gas, Integer> {
    List<Gas> getByName(String name);
}
