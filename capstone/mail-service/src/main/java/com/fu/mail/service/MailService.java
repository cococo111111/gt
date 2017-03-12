package com.fu.mail.service;

import java.util.Map;

/**
 * Created by manlm on 8/20/2016.
 */
@FunctionalInterface
public interface MailService {

    /**
     * Send email
     *
     * @param email
     * @param template
     * @param subject
     * @param entry
     * @return
     */
    boolean sendMail(String email, String template, String subject, Map<String, String> entry);
}
