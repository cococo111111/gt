package com.fu.bom.controller;

import com.fu.bom.service.ChatLogService;
import com.fu.database.entity.ChatLog;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Administrator on 09/11/2016.
 */
@Controller
public class ChatLogController {

    private final ChatLogService chatLogService;

    private static final Logger LOG = Logger.getLogger(ChatLogController.class);
    @Autowired
    public ChatLogController(ChatLogService chatLogService) {
        this.chatLogService = chatLogService;
    }

    @RequestMapping(value = "/viewChatLog", method = RequestMethod.GET)
    public ModelAndView viewChatlog(@ModelAttribute(name = "resultAction") String resultAction) {
        LOG.info("[viewChatlog] Start");
        ModelAndView model = new ModelAndView("viewChatLog");
        List<ChatLog> listChatLog = chatLogService.getAll();
        model.addObject("resultAction", resultAction);
        model.addObject("listChatLog", listChatLog);
        LOG.info("[viewChatlog] End");
        return model;
    }

    @RequestMapping(value = "/deleteChatLog", method = RequestMethod.POST)
    public ModelAndView deleteChatLog(@RequestParam(value = "chatLogId") long[] chatLogId) {
        LOG.info("[viewChatlog] Start:");
        ModelAndView model = new ModelAndView("redirect:viewChatLog");
        chatLogService.deleteChatLog(chatLogId);
        LOG.info("[viewChatlog] End");
        return model;
    }

}
