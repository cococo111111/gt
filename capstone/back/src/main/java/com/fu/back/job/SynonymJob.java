package com.fu.back.job;

import com.fu.back.task.SynonymTask;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by manlm on 8/18/2016.
 */
public class SynonymJob extends QuartzJobBean {

    private static final Logger LOG = Logger.getLogger(SynonymJob.class);

    private SynonymTask synonymTask;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOG.info("[executeInternal] Start");
        synonymTask.doTask();
        LOG.info("[executeInternal] End");
    }

    public void setSynonymTask(SynonymTask synonymTask) {
        this.synonymTask = synonymTask;
    }
}
