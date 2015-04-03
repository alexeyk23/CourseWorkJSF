/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.curswork.dao;

import com.curswork.model.Application;
import com.curswork.model.Privilege;
import com.curswork.util.UtilHibernate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Kunakovsky A.
 */
public class PrivilegeDAO {
    public static void  addPrivilege(Privilege r)
    {
        
      EntityManager entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
      entityManager.getTransaction().begin();
      entityManager.persist(r);            
      entityManager.getTransaction().commit();
      entityManager.close();
    }
    public static List<Privilege> getAllPrivilege()
    {
       EntityManager  entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
       entityManager.getTransaction().begin();
       Query q = entityManager.createQuery("SELECT u FROM Privilege u");
       List<Privilege> userList = (List<Privilege>)q.getResultList();       
       entityManager.getTransaction().commit();
       entityManager.close();
       return userList;
    }
    public static Privilege getPrivilegeById(int id_priv)
    {
       EntityManager  entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
       entityManager.getTransaction().begin();
       Privilege res = entityManager.find(Privilege.class, id_priv);
       entityManager.getTransaction().commit();
       entityManager.close();
       return res;
    }
    public static void deletePrivilege(int id_priv) throws Exception
    {
       EntityManager entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
        try {        
            entityManager.getTransaction().begin();      
            Privilege privilege = entityManager.find(Privilege.class, id_priv);
            for (Application app : privilege.getApps()) {
                app.getPrivs().remove(privilege);
            }
            entityManager.remove(privilege);
            entityManager.getTransaction().commit();      
        } catch (Exception e) {
            if(entityManager.getTransaction()!=null)
                entityManager.getTransaction().rollback();
            throw e;
        }
        finally
        {
              entityManager.close();
        }
    }
}
