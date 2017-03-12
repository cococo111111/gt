package com.fu.queue.sender;

import com.amazonaws.services.sqs.AmazonSQS;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by manlm on 8/21/2016.
 */
@Component
public class SqsQueueSender {

    private static final Logger LOG = Logger.getLogger(SqsQueueSender.class);

    private final QueueMessagingTemplate queueMessagingTemplate;

    /**
     * Constructor
     *
     * @param amazonSqs
     */
    @Autowired
    public SqsQueueSender(AmazonSQS amazonSqs) {
        this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSqs);
    }

    /**
     * Send queue message
     *
     * @param message
     * @param queueName
     * @param <E>
     */
    public <E> void send(E message, String queueName) {
        LOG.info("[send] Start");
        queueMessagingTemplate.convertAndSend(queueName, message);
        LOG.info("[send] End");
    }
}
