/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.curswork.dao;

import com.curswork.model.UserRole;
import com.curswork.util.UtilHibernate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Kunakovsky A.
 */
public class UserRoleDAO {
  public static void  addUserRole(UserRole u)
    {
        
      EntityManager entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
      entityManager.getTransaction().begin();
      entityManager.merge(u);  //!!!!!!!! !!!!!!!!!!! M E R G E          
      entityManager.getTransaction().commit();
      entityManager.close();
    }
    public static List<UserRole> getAllUserRoles()
    {
       EntityManager  entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
       entityManager.getTransaction().begin();
       Query q = entityManager.createQuery("SELECT u FROM UserRole u");
       List<UserRole> userList = (List<UserRole>)q.getResultList();       
       entityManager.getTransaction().commit();
       entityManager.close();
       return userList;
    }
    public static UserRole getByRoleId(int id_role)
    {
       EntityManager  entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
       entityManager.getTransaction().begin();
       UserRole res = entityManager.find(UserRole.class, id_role);
       entityManager.getTransaction().commit();
       entityManager.close();
       return res;
    }
}
