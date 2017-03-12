package com.fu.database.dao.impl;

import com.fu.database.dao.GasDao;
import com.fu.database.entity.Gas;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 14/02/2017.
 */
@Repository
public class GasDaoImpl extends GenericDaoImpl<Gas, Integer> implements GasDao {
    @Override
    public List<Gas> getByName(String name) {
        List<Gas> gas = getEntityManager().createQuery("SELECT g FROM Gas g WHERE g.name LIKE :name ORDER BY g.name desc", Gas.class)
                .setParameter("name", "%" + name.toLowerCase() + "%")
                .getResultList();
        return gas;
    }


}
