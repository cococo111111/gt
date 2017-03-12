package com.fu.back.service;

/**
 * Created by manlm on 8/18/2016.
 */
@FunctionalInterface
public interface SchedulerService {

    /**
     * Change execute time of cron job
     *
     * @param key
     * @param cron
     */
    void rescheduleCronJob(String key, String cron);
}
