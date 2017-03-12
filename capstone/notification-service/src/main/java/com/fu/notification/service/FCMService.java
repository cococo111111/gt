package com.fu.notification.service;

/**
 * Created by manlm on 9/2/2016.
 */
public interface FCMService {

    void sendNotificationMessage(String title, String body, String receiver);

    void sendDataMessage(String data, String receiver);
}
