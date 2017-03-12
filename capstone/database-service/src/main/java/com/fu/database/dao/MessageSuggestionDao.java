package com.fu.database.dao;

import com.fu.database.entity.MessageSuggestion;

import java.util.List;

/**
 * Created by Administrator on 15/02/2017.
 */
public interface MessageSuggestionDao extends GenericDao<MessageSuggestion, Integer> {
    List<MessageSuggestion> getMessageByFieldId(int FieldId);

    List<MessageSuggestion> getMessageByVehicleId(int vehicleId);
}
