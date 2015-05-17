/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursework.dao.old;

import com.coursework.model.Command;
import com.coursework.util.UtilHibernate;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author admin
 */
public class CommandDAO {

    public static List<Command> getAllCommand() throws Exception {
        EntityManager entityManager = null;
        List<Command> listRes = null;
        try {
            entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createQuery("Select c FROM Command c");
            listRes = q.getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction() != null) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return listRes;
    }

    public static List<Command> getLastCommandsForApp(int idApp, Date date)throws Exception  {
        EntityManager entityManager = null;
        List<Command> listRes = null;
        try {
            entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createQuery("Select c FROM Command c WHERE c.idApplication = ?1 AND c.dateMake>?2")
                    .setParameter(1, idApp)
                    .setParameter(2, date, TemporalType.TIMESTAMP);
            listRes = q.getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction() != null) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return listRes;
    }
}
