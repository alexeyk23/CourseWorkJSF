/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.curswork.dao;

import com.curswork.model.Command;
import com.curswork.util.UtilHibernate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author admin
 */
public class CommandDAO {

    public static List<Command> getAllCommand() {
        EntityManager entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        Query q = entityManager.createQuery("Select c FROM Command c");
        List<Command> listRes = q.getResultList();
        entityManager.getTransaction().commit();
        return listRes;
    }
}
