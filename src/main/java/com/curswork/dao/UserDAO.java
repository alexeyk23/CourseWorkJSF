/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.curswork.dao;

import com.curswork.model.Role;
import com.curswork.model.User;
import com.curswork.util.UtilHibernate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Kunakovsky A.
 */
public class UserDAO 
{
  

    public static void  addUser(User u)
    {
        
      EntityManager entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
      entityManager.getTransaction().begin();
      entityManager.merge(u);  //!!!!!!!! !!!!!!!!!!! M E R G E          
      entityManager.getTransaction().commit();
      entityManager.close();
    }
    public static List<User> getAllUsers()
    {
       EntityManager  entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
       entityManager.getTransaction().begin();
       Query q = entityManager.createQuery("SELECT u FROM User u");
       List<User> userList = (List<User>)q.getResultList();       
       entityManager.getTransaction().commit();
       entityManager.close();
       return userList;
    }
    public static User getUserById(int id_user)
    {
       EntityManager  entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
       entityManager.getTransaction().begin();
       User res = entityManager.find(User.class, id_user);
       entityManager.getTransaction().commit();
       entityManager.close();
       return res;
    }
}
