/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.curswork.dao;

import com.curswork.model.Application;
import com.curswork.util.UtilHibernate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Kunakovsky A.
 */
public class ApplicationDAO 
{
    public static void  addApp(Application r)
    {
        
      EntityManager entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
      entityManager.getTransaction().begin();
      entityManager.persist(r);            
      entityManager.getTransaction().commit();
      entityManager.close();
    }
    public static List<Application> getAllApp()
    {
       EntityManager  entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
       entityManager.getTransaction().begin();
       Query q = entityManager.createQuery("SELECT u FROM Application u");
       List<Application> userList = (List<Application>)q.getResultList();       
       entityManager.getTransaction().commit();
       entityManager.close();
       return userList;
    }
    public static Application getAppById(int id_app)
    {
       EntityManager  entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
       entityManager.getTransaction().begin();
       Application res = entityManager.find(Application.class, id_app);
       entityManager.getTransaction().commit();
       entityManager.close();
       return res;
    }

}
