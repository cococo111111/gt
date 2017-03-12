package com.fu.bot.service;

/**
 * Created by manlm on 9/2/2016.
 */
public interface FacebookAPIService {

    void doSubscribeRequest();

    void createGetStartButton();

    void createGreetingText();

    void createPersistentMenu();

    void sendTextMessage(String userId, String text);

    void sendTextMessage(String jsonString);
}
