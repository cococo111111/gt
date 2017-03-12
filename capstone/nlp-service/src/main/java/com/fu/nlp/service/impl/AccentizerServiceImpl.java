package com.fu.nlp.service.impl;

import com.fu.nlp.service.AccentizerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Created by Administrator on 23/10/2016.
 */
@Service
public class AccentizerServiceImpl implements AccentizerService {

    private static final Logger LOG = Logger.getLogger(AccentizerServiceImpl.class);

    private final Properties properties;

    @Autowired
    public AccentizerServiceImpl(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String add(String input) {
        LOG.info("[add] Start: input = " + input);
        RestTemplate restTemplate = new RestTemplate();

//        ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory())
//                .setConnectTimeout(Integer.parseInt(properties.getProperty(Constant.CONNECTION_TIMEOUT)));

        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        LOG.info("[add] End");
        return restTemplate.getForObject(properties.get("vietnamese_accentizer_add") + "/?text=" + input, String.class);
    }

    @Override
    public String remove(String input) {
        LOG.info("[remove] Start: input = " + input);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        LOG.info("[remove] End");
        return restTemplate.getForObject(properties.get("vietnamese_accentizer_remove") + "/?text=" + input, String.class);
    }
}
