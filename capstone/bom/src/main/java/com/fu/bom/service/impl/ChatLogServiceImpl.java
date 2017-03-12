package com.fu.bom.service.impl;

import com.fu.bom.service.ChatLogService;
import com.fu.database.dao.ChatLogDao;
import com.fu.database.entity.ChatLog;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 09/11/2016.
 */
@Service
public class ChatLogServiceImpl implements ChatLogService {

    private final ChatLogDao chatLogDao;

    private static final Logger LOG = Logger.getLogger(ChatLogServiceImpl.class);

    public ChatLogServiceImpl(ChatLogDao chatLogDao) {
        this.chatLogDao = chatLogDao;
    }

    @Override
    public List<ChatLog> getAll() {
        LOG.info("[getAll] Start:");
        LOG.info("[getAll] End");
        return chatLogDao.getAll();
    }

    @Override
    public boolean deleteChatLog(long[] listId) {
        LOG.info("[deleteChatLog] Start:");
        try {
            for (long aListId : listId) {
                ChatLog chatLog = chatLogDao.getById(aListId);
                chatLog.setStatus("DEACTIVE");
                chatLogDao.update(chatLog);
            }
            return true;
        } catch (RuntimeException ex) {
            LOG.error("[deleteProduct] RuntimeException: ", ex);
        }
        LOG.info("[deleteProduct] End");
        return false;
    }
}
