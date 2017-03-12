package com.fu.database.dao;

import com.fu.database.entity.ChatLog;

import java.util.List;

/**
 * Created by manlm on 9/21/2016.
 */
public interface ChatLogDao extends GenericDao<ChatLog, Long> {

    List<ChatLog> getAll();
}
