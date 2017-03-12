package com.fu.database.dao;

import com.fu.database.entity.Synonym;

import java.util.List;

/**
 * Created by manlm on 9/21/2016.
 */
public interface SynonymDao extends GenericDao<Synonym, Integer> {

    List<Synonym> getAll(int firstResult, int maxResult);

    List<Synonym> getByIdAndSynonymId(int id, int synonymId);

    List<Synonym> checkDuplicateName(String name);

    void deleteSynonym(int id);

    List<Synonym> getAll();

    List<Synonym> getAllKeyOriginal();

    List<Synonym> getSynonymByKeyOriginalId(int keyOriginalId);

    List<Synonym> getSynonym();

    void deleteOriginalWord(int id);
}
