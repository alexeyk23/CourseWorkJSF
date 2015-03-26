/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.curswork.dao;

import com.curswork.model.Role;
import com.curswork.util.UtilHibernate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Kunakovsky A.
 */
public class RoleDAO 
{
public static void  addRole(Role r)
    {
        
      EntityManager entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
      entityManager.getTransaction().begin();
      entityManager.persist(r);            
      entityManager.getTransaction().commit();
      entityManager.close();
    }
    public static List<Role> getAllRole()
    {
       EntityManager  entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
       entityManager.getTransaction().begin();
       Query q = entityManager.createQuery("SELECT u FROM Role u");
       List<Role> userList = (List<Role>)q.getResultList();       
       entityManager.getTransaction().commit();
       entityManager.close();
       return userList;
    }
    public static Role getRoleById(int id_role)
    {
       EntityManager  entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
       entityManager.getTransaction().begin();
       Role res = entityManager.find(Role.class, id_role);
       entityManager.getTransaction().commit();
       entityManager.close();
       return res;
    }
}
