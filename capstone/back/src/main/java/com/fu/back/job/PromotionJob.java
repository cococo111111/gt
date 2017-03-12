package com.fu.back.job;

import com.fu.back.task.PromotionTask;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by manlm on 12/2/2016.
 */
public class PromotionJob extends QuartzJobBean {

    private static final Logger LOG = Logger.getLogger(SynonymJob.class);

    private PromotionTask promotionTask;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOG.info("[executeInternal] Start");
        promotionTask.doTask();
        LOG.info("[executeInternal] End");
    }

    public void setPromotionTask(PromotionTask promotionTask) {
        this.promotionTask = promotionTask;
    }
}
