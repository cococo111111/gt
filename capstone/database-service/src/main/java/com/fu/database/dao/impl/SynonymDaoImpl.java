package com.fu.database.dao.impl;

import com.fu.database.dao.SynonymDao;
import com.fu.database.entity.Synonym;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by manlm on 9/21/2016.
 */
@Repository
public class SynonymDaoImpl extends GenericDaoImpl<Synonym, Integer> implements SynonymDao {

    private static final Logger LOG = Logger.getLogger(SynonymDaoImpl.class);

    @Override
    public List<Synonym> getAll(int firstResult, int maxResult) {
        LOG.info(new StringBuilder("[getAll] Start: firstResult = ").append(firstResult)
                .append(", maxResult = ").append(maxResult));
        LOG.info("[getAll] End");
        return getEntityManager()
                .createQuery("SELECT c FROM Synonym c ", Synonym.class)
                .setFirstResult(firstResult)
                .setMaxResults(maxResult)
                .getResultList();
    }

    @Override
    public List<Synonym> getByIdAndSynonymId(int id, int synonymId) {
        LOG.info(new StringBuilder("[getByIdAndSynonymId] Start: id = ").append(id)
                .append(", synonymId = ").append(synonymId));
        LOG.info("[getByIdAndSynonymId] End");
        return getEntityManager()
                .createNativeQuery("SELECT * FROM synonym WHERE (id = :synonymId OR synonymId = :id OR (synonymId = :synonymId AND synonymId != 0) OR id = :synonymId) AND id != :id", Synonym.class)
                .setParameter("id", id)
                .setParameter("synonymId", synonymId)
                .getResultList();
    }

    @Override
    public List<Synonym> checkDuplicateName(String name) {
        LOG.info("[checkDuplicateName] Start:");
        LOG.info("[checkDuplicateName] End");
        return getEntityManager()
                .createQuery("SELECT s FROM Synonym s WHERE s.name=:name", Synonym.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public void deleteSynonym(int id) {
        LOG.info("[deleteSynonym] Start:");
        LOG.info("[deleteSynonym] End");
        getEntityManager().createQuery("DELETE FROM Synonym s " +
                "WHERE s.id=:id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public List<Synonym> getAll() {
        LOG.info("[getAll] Start");
        LOG.info("[getAll] End");
        return getEntityManager().createQuery("FROM " + Synonym.class.getSimpleName()).getResultList();
    }

    @Override
    public List<Synonym> getAllKeyOriginal() {
        LOG.info("[getAllKeyOriginal] Start");
        LOG.info("[getAllKeyOriginal] End");
        return getEntityManager().createQuery("SELECT s FROM Synonym s WHERE s.synonymId=0").getResultList();
    }

    @Override
    public List<Synonym> getSynonymByKeyOriginalId(int keyOriginalId) {
        LOG.info("[getSynonymByKeyOriginalId] Start");
        LOG.info("[getSynonymByKeyOriginalId] End");
        return getEntityManager().createQuery("SELECT s FROM Synonym s WHERE s.synonymId=:keyOriginalId")
                .setParameter("keyOriginalId", keyOriginalId)
                .getResultList();
    }

    @Override
    public List<Synonym> getSynonym() {
        LOG.info("[getAllKeyOriginal] Start");
        LOG.info("[getAllKeyOriginal] End");
        return getEntityManager().createQuery("SELECT s FROM Synonym s WHERE s.synonymId<>0").getResultList();
    }

    @Override
    public void deleteOriginalWord(int id) {
        LOG.info("[deleteOriginalWord] Start:");
        LOG.info("[deleteOriginalWord] End");
        getEntityManager().createNativeQuery("DELETE FROM synonym " +
                "WHERE id=:id OR synonymId=:id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
