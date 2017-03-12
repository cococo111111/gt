package com.fu.database.dao.impl;

import com.fu.database.dao.ChatLogDao;
import com.fu.database.entity.ChatLog;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by manlm on 9/21/2016.
 */
@Repository
public class ChatLogDaoImpl extends GenericDaoImpl<ChatLog, Long> implements ChatLogDao {

    private static final Logger LOG = Logger.getLogger(ChatLogDaoImpl.class);

    @Override
    public List<ChatLog> getAll() {
        LOG.info("[getAll] Start:");
        LOG.info("[getAll] End");
        return getEntityManager()
                .createQuery("SELECT c FROM ChatLog c WHERE c.status=:status", ChatLog.class)
                .setParameter("status", "New")
                .getResultList();
    }
}
