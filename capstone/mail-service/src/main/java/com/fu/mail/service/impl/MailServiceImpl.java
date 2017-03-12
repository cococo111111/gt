package com.fu.mail.service.impl;

import com.fu.mail.config.Mailer;
import com.fu.mail.model.Mail;
import com.fu.mail.service.MailService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by manlm on 8/20/2016.
 */
@Service
public class MailServiceImpl implements MailService {

    private static final Logger LOG = Logger.getLogger(MailServiceImpl.class);

    private final Mailer mailer;

    /**
     * Constructor
     *
     * @param mailer
     */
    @Autowired
    public MailServiceImpl(Mailer mailer) {
        this.mailer = mailer;
    }

    /**
     * Send email
     *
     * @param email
     * @param template
     * @param subject
     * @param entry
     * @return
     */
    @Override
    public boolean sendMail(String email, String template, String subject, Map<String, String> entry) {
        LOG.info(new StringBuilder("[sendMail] Start: email = ").append(email).append(", template = ").append(template)
                .append(", subject = ").append(subject));

        Mail mail = new Mail();
        mail.setMailTo(email);
        mail.setTemplateName(template);
        mail.setMailSubject(subject);
        mailer.sendMail(mail, entry);

        LOG.info("[sendMail] End");
        return true;
    }
}
