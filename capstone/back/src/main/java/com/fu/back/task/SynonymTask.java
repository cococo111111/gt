package com.fu.back.task;

import com.fu.back.service.SynonymService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by manlm on 8/18/2016.
 */
@Component
public class SynonymTask {

    private static final Logger LOG = Logger.getLogger(SynonymTask.class);

    private final SynonymService synonymService;

    @Autowired
    public SynonymTask(SynonymService synonymService) {
        this.synonymService = synonymService;
    }

    /**
     * Execute task
     */
    public void doTask() {
        LOG.info("[doTask] Start");
        synonymService.generateSynonym();
        LOG.info("[doTask] End");
    }
}
