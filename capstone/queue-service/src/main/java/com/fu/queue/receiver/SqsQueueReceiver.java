package com.fu.queue.receiver;

import com.amazonaws.services.sqs.AmazonSQS;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by manlm on 8/21/2016.
 */
@Component
public class SqsQueueReceiver {

    private static final Logger LOG = Logger.getLogger(SqsQueueReceiver.class);

    private final QueueMessagingTemplate queueMessagingTemplate;

    /**
     * Constructor
     *
     * @param amazonSqs
     */
    @Autowired
    public SqsQueueReceiver(AmazonSQS amazonSqs) {
        this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSqs);
    }

    /**
     * Receive queue message
     *
     * @param clazz
     * @param queueName
     * @param <T>
     * @return
     */
    public <T> T receive(Class<T> clazz, String queueName) {
        LOG.info("[receive] Start");
        LOG.info("[receive] End");
        return queueMessagingTemplate.receiveAndConvert(queueName, clazz);
    }
}
