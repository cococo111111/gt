package com.fu.mail.config;

import com.fu.mail.model.Mail;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Created by manlm on 8/20/2016.
 */
public class Mailer {

    private static final Logger LOG = Logger.getLogger(Mailer.class);

    private final Properties properties;

    private MailSender mailSender;

    /**
     * Constructor
     *
     * @param properties
     */
    @Autowired
    public Mailer(Properties properties) {
        this.properties = properties;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Send email
     *
     * @param mail
     * @param mailAttribute
     */
    public void sendMail(Mail mail, Map<String, String> mailAttribute) {
        LOG.info("[sendMail] Start");
        SimpleMailMessage message = new SimpleMailMessage();
        Configuration configuration = new Configuration();
        StringBuilder content = new StringBuilder();

        try {
            FileTemplateLoader ftl = new FileTemplateLoader(
                    new File(properties.getProperty("mail.template.path")));
            configuration.setTemplateLoader(ftl);
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
                    configuration.getTemplate(mail.getTemplateName()), mailAttribute));
        } catch (IOException e) {
            LOG.error("[sendMail] IOException: " + e);
        } catch (TemplateException e) {
            LOG.error("[sendMail] TemplateException: " + e);
        }

        message.setFrom(mail.getMailFrom());
        message.setTo(mail.getMailTo());
        message.setSubject(mail.getMailSubject());
        message.setText(content.toString());

        mailSender.send(message);
        LOG.info("[sendMail] End");
    }
}
