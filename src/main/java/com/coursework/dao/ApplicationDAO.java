/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.coursework.dao;

import com.coursework.model.Application;
import com.coursework.model.Permission;
import com.coursework.model.Privilege;
import com.coursework.model.Role;
import com.coursework.util.UtilHibernate;
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
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(r);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
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
    public static void deleteApp(int id_app) throws Exception
    {
        EntityManager entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Application app = entityManager.find(Application.class, id_app);
            for (Permission p : app.getPermissions()) {
                for (Role role : p.getRoles()) {
                    role.getPermissions().remove(p);
                }                
                entityManager.remove(p);
            }
            for(Privilege p : app.getPrivs())
            {
                p.getApps().remove(app);
            }
            entityManager.remove(app);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction() != null) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }
}
