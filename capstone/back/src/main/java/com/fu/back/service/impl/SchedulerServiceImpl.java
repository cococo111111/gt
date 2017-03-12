package com.fu.back.service.impl;


import com.fu.back.service.SchedulerService;
import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.text.ParseException;

/**
 * Created by manlm on 8/18/2016.
 */
@Service
public class SchedulerServiceImpl implements SchedulerService {

    private static final Logger LOG = Logger.getLogger(SchedulerServiceImpl.class);

    private final SchedulerFactoryBean schedulerFactoryBean;

    /**
     * Constructor
     *
     * @param schedulerFactoryBean
     */
    @Autowired
    public SchedulerServiceImpl(SchedulerFactoryBean schedulerFactoryBean) {
        this.schedulerFactoryBean = schedulerFactoryBean;
    }

    /**
     * Change execute time of cron job
     *
     * @param key
     * @param cron
     */
    @Override
    public void rescheduleCronJob(String key, String cron) {
        LOG.info(new StringBuilder("[rescheduleCronJob] Start: key = ").append(key)
                .append(", cron = ").append(cron));
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            TriggerKey triggerKey = new TriggerKey(key);
            CronTriggerImpl trigger = (CronTriggerImpl) scheduler.getTrigger(triggerKey);
            trigger.setCronExpression(cron);
            scheduler.rescheduleJob(triggerKey, trigger);
            LOG.info("[rescheduleCronJob] End");
        } catch (SchedulerException e) {
            LOG.error("[rescheduleCronJob] SchedulerException = " + e);
        } catch (ParseException e) {
            LOG.error("[rescheduleCronJob] ParseException = " + e);
        }
    }
}
