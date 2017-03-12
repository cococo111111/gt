package com.fu.bom.service;

import com.fu.database.entity.ChatLog;

import java.util.List;

/**
 * Created by Administrator on 09/11/2016.
 */
public interface ChatLogService {

    List<ChatLog> getAll();

    boolean deleteChatLog(long[] listId);
}
