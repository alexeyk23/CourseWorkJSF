/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.curswork.dao;

import com.curswork.model.Command;
import com.curswork.util.UtilHibernate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

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
    static private int hour =4;
    public static List<Command> getLastCommandsForApp(int idApp)
    {
        EntityManager entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        Calendar calendar = new GregorianCalendar();
        calendar.roll(Calendar.HOUR, -hour);
        Query q = entityManager.createQuery("Select c FROM Command c WHERE c.idApplication = ?1 AND c.dateMake > ?2")
                .setParameter(1, idApp)
                .setParameter(2, calendar.getTime(),TemporalType.TIMESTAMP);                
        List<Command> listRes = q.getResultList();
        entityManager.getTransaction().commit();
        return listRes;
    }
}
