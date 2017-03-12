package com.fu.database.dao.impl;

import com.fu.database.dao.GenericDao;
import com.fu.database.dao.MessageSuggestionDao;
import com.fu.database.entity.MessageSuggestion;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 15/02/2017.
 */
@Repository
public class MessageSuggestionDaoImpl extends GenericDaoImpl<MessageSuggestion, Integer> implements MessageSuggestionDao {

    @Override
    public List<MessageSuggestion> getMessageByFieldId(int FieldId) {
        return null;
    }

    public List<MessageSuggestion> getMessageByVehicleId(int vehicleId){

        return getEntityManager().createQuery("SELECT m FROM MessageSuggestion m WHERE m.vehicleId=:id ", MessageSuggestion.class)
                .setParameter("id", vehicleId)
                .getResultList();
    }
}
