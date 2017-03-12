package com.fu.back.controller;

import com.fu.back.service.SchedulerService;
import com.fu.back.service.SynonymService;
import com.fu.cache.client.JedisClient;
import com.fu.queue.receiver.SqsQueueReceiver;
import com.fu.queue.sender.SqsQueueSender;
import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * Created by manlm on 8/18/2016.
 */
@RestController
public class IndexController {

    private static final Logger LOG = Logger.getLogger(IndexController.class);

    private final SchedulerService schedulerService;

    private final JedisClient jedisClient;

    private final SqsQueueSender sender;

    private final SqsQueueReceiver receiver;

    private final SynonymService synonymService;

    @Autowired
    public IndexController(SchedulerService schedulerService, JedisClient jedisClient
            , SqsQueueSender sender, SqsQueueReceiver receiver, SynonymService synonymService) {
        this.schedulerService = schedulerService;
        this.jedisClient = jedisClient;
        this.sender = sender;
        this.receiver = receiver;
        this.synonymService = synonymService;
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String main() throws ParseException, SchedulerException {
        sender.send("my queue message", "manlmqueue");
        LOG.info(receiver.receive(String.class, "manlmqueue"));
        jedisClient.set("mykey", "abc", 120);
        LOG.info(String.valueOf(jedisClient.get("mykey")));
        schedulerService.rescheduleCronJob("cronSchedulerJobTrigger", "0 0/1 * 1/1 * ? *");
        return "update";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() throws ParseException, SchedulerException {
        synonymService.generateSynonym();
        return "update";
    }
}
