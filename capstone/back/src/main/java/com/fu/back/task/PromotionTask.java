package com.fu.back.task;

import com.fu.back.service.PromotionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by manlm on 12/2/2016.
 */
@Component
public class PromotionTask {

    private static final Logger LOG = Logger.getLogger(SynonymTask.class);

    private final PromotionService promotionService;

    @Autowired
    public PromotionTask(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    /**
     * Execute task
     */
    public void doTask() {
        LOG.info("[doTask] Start");

        LOG.info("[doTask] End");
    }
}
