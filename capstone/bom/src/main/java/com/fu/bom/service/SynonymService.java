package com.fu.bom.service;

import com.fu.database.entity.Synonym;

import java.util.List;

/**
 * Created by Administrator on 08/11/2016.
 */
public interface SynonymService {

    boolean addSynonym(String name, int sysnonymId);

    boolean updateSynonym(int id, String name, int sysnonymId);

    boolean updateKeyOriginal(int id, String name);

    boolean deleteSynonym(int id);

    List<Synonym> getAll();

    List<Synonym> getKeyOriginal();

    List<Synonym> getSynonymByKeyOriginalId(int keyOriginal);

    List<Synonym> getSynonym();

    boolean deleteOriginalWord(int id);
}
