package com.fu.nlp.service.impl;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import com.fu.common.constant.KeyConstant;
import com.fu.common.util.AESUtil;
import com.fu.common.util.JSONUtil;
import com.fu.nlp.model.Entry;
import com.fu.nlp.service.NaturalLanguageProcessingService;
import com.fu.nlp.utils.Constant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by manlm on 9/5/2016.
 */
@Service
public class NaturalLanguageProcessingServiceImpl implements NaturalLanguageProcessingService {

    private static final Logger LOG = Logger.getLogger(NaturalLanguageProcessingServiceImpl.class);

    private String aesKey;

    private AIDataService dataService;

    private Properties properties;

    @Autowired
    private NaturalLanguageProcessingServiceImpl(Properties properties) {
        aesKey = properties.getProperty("aes.key") + KeyConstant.AES_KEY;
        AIConfiguration configuration
                = new AIConfiguration(AESUtil.decryptByAES(properties.getProperty("api_ai_access_token"), aesKey));
        dataService = new AIDataService(configuration);
        this.properties = properties;
    }

    @Override
    public String processSpeech(String speech) {
        LOG.info("[processSpeech] Start: speech = " + speech);

        try {
            AIRequest request = new AIRequest(speech);
            AIResponse response = dataService.request(request);

            if (response.getStatus().getCode() == 200) {
                LOG.info("[processSpeech] End");
                if(!response.getResult().getParameters().isEmpty()){
                    return response.getResult().getFulfillment().getSpeech();
                }
                return Constant.DO_NOT_UNDERSTAND_MESSAGE;
            } else {
                LOG.info("[processSpeech] End: " + response.getStatus().getErrorDetails());
                return Constant.DO_NOT_UNDERSTAND_MESSAGE;
            }

        } catch (AIServiceException e) {
            LOG.error("[processSpeech] AIServiceException: " + e);
            return Constant.DO_NOT_UNDERSTAND_MESSAGE;
        }
    }
    @Override
    public Map<String, Object> processSpeech2(String speech) {
        LOG.info("[processSpeech] Start: speech = " + speech);
        Map data = new HashMap();
        try {
            AIRequest request = new AIRequest(speech);
            AIResponse response = dataService.request(request);

            data.put("status",response.getStatus().getCode());
            data.put("intent",response.getResult().getMetadata().getIntentName());
            data.put("speech",response.getResult().getFulfillment().getSpeech());
        } catch (AIServiceException e) {
            LOG.error("[processSpeech] AIServiceException: " + e);
        }
        return data;
    }

    @Override
    public boolean addEntriesToEntity(List<Entry> entries, String entity) {
        LOG.info("[addEntriesToEntity] Start: entries = " + entries.get(0).getValue());
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Content-Type", "application/json; charset=utf-8");
            headers.add("Authorization", "Bearer " + AESUtil.decryptByAES(properties.getProperty("api_ai_access_token"), aesKey));

            String bodyString = JSONUtil.ojbToJson(entries);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter(StandardCharsets.UTF_8));

            HttpEntity<String> request = new HttpEntity<>(bodyString, headers);
            restTemplate.postForObject("https://api.api.ai/v1/entities/" + entity + "/entries", request, String.class);
            LOG.info("[addEntriesToEntity] End");
            return true;
        } catch (HttpClientErrorException e) {
            LOG.error("[addEntriesToEntity] HttpClientErrorException: " + e);
            return false;
        }
    }

    @Override
    public void updateEntries(List<Entry> entries, String entity) {
        LOG.info("[updateEntries] Start: entries = " + entries.get(0));

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json; charset=utf-8");
        headers.add("Authorization", "Bearer " + AESUtil.decryptByAES(properties.getProperty("api_ai_access_token"), aesKey));

        String bodyString = JSONUtil.ojbToJson(entries);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpEntity<String> request = new HttpEntity<>(bodyString, headers);
        restTemplate.put("https://api.api.ai/v1/entities/" + entity + "/entries", request, String.class);

        LOG.info("[updateEntries] End");
    }

    @Override
    public boolean deleteEntries(List<String> listName, String entity) {
        LOG.info("[deleteEntries] Start: listName = " + listName.get(0));
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Content-Type", "application/json; charset=utf-8");
            headers.add("Authorization", "Bearer " + AESUtil.decryptByAES(properties.getProperty("api_ai_access_token"), aesKey));

            String bodyString = JSONUtil.ojbToJson(listName);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            HttpEntity<String> request = new HttpEntity<>(bodyString, headers);
            restTemplate.exchange("https://api.api.ai/v1/entities/" + entity + "/entries", HttpMethod.DELETE, request, String.class);
            //restTemplate.delete("https://api.api.ai/v1/entities/" + entity + "/entries", request, String.class);

            LOG.info("[deleteEntries] End");
            return true;
        } catch (HttpClientErrorException e) {
            LOG.info("[deleteEntries] HttpClientErrorException: " + e);
            return false;
        }
    }
}
