package com.fu.database.dao.impl;

import com.fu.database.dao.LawDao;
import com.fu.database.entity.Law;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 11/02/2017.
 */
@Repository
public class LawDaoImpl extends GenericDaoImpl<Law, Long> implements LawDao {

    private static final Logger LOG = Logger.getLogger(LawDaoImpl.class);

    public LawDaoImpl() {
    }

    @Override
    public Law getLawById(long lawId) {
        LOG.info("[getLawById] Start: productId = " + lawId);
        LOG.info("[getLawById] End");
        return getEntityManager().createQuery("SELECT c FROM Law c WHERE c.id=:id ", Law.class)
                .setParameter("id", lawId)
                .getSingleResult();
    }

    @Override
    public List<Law> getLawBySearchName(String name, int positionInResult, int maxShowResult) {

        LOG.info("[getProductBySearchName] Start: name = " + name);
        List<Law> laws = getEntityManager().createQuery("SELECT l FROM Law l WHERE l.name LIKE :name  ORDER BY l.vehicleId asc ", Law.class)
                .setParameter("name", "%"+name.toLowerCase()+"%")
                .setFirstResult(positionInResult)
                .setMaxResults(maxShowResult)
                .getResultList();
        return laws;
    }
}
