package com.fu.nlp.service;

import com.fu.nlp.model.Entry;

import java.util.List;
import java.util.Map;

/**
 * Created by manlm on 9/5/2016.
 */
public interface NaturalLanguageProcessingService {

    String processSpeech(String speech);

    Map<String, Object> processSpeech2(String speech);

    boolean addEntriesToEntity(List<Entry> entries, String entity);

    void updateEntries(List<Entry> entries, String entity);

    boolean deleteEntries(List<String> listName, String entity);
}
