package com.fu.database.dao;

import com.fu.database.entity.Law;

import java.util.List;

/**
 * Created by Administrator on 11/02/2017.
 */
public interface LawDao extends GenericDao<Law, Long>  {

    Law getLawById(long lawId);
    List<Law> getLawBySearchName(String name, int positionInResult, int maxShowResult);

}
