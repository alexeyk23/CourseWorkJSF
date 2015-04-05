/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.curswork.dao;

import com.curswork.model.Command;
import com.curswork.model.Permission;
import com.curswork.model.Role;
import com.curswork.model.User;
import com.curswork.util.UtilHibernate;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Kunakovsky A.
 */
public class UserDAO {

    public static void addUser(User u) throws Exception {
        EntityManager entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            int idUser= entityManager.merge(u).getIdUser();  //!!!!!!!! !!!!!!!!!!! M E R G E 
            //command for queue
            Set<Permission> currPermissions = new HashSet<Permission>();
            for (Role role : u.getRoles()) {
                for (Permission perm : role.getPermissions()) {                    
                    currPermissions.add(perm);
                }
            }
            //insert command
            for (Permission p : currPermissions) {
                Command c = new Command(idUser, p.getApplication().getIdApp(), 
                        p.getPrivelege().getIdPriv(),"add",new Date());
                entityManager.persist(c);
            }
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

    public static List<User> getAllUsers() {
        EntityManager entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        Query q = entityManager.createQuery("SELECT u FROM User u");
        List<User> userList = (List<User>) q.getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return userList;
    }

    public static User getUserById(int id_user) {
        EntityManager entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        User res = entityManager.find(User.class, id_user);
        entityManager.getTransaction().commit();
        entityManager.close();
        return res;
    }

    public static void updateUser(int id_user, String userName, Date dateofb, List<String> roleIds) {
        EntityManager entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            User upUser = entityManager.find(User.class, id_user);
            upUser.setNameUser(userName);
            upUser.setDateOfBirthday(dateofb);
            Set<Role> currRoles = new HashSet<Role>();
            currRoles.addAll(upUser.getRoles());
            //delete role
            for (Role role : currRoles) {
                if (!roleIds.contains(String.valueOf(role.getIdRole()))) {
                    upUser.getRoles().remove(role);
                }
            }
            //set new roles
            for (String id : roleIds) {
                upUser.getRoles().add(entityManager.find(Role.class, Integer.valueOf(id)));
            }
            entityManager.merge(upUser);
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

    public static void deleteUser(int id_user) throws Exception {
        EntityManager entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            User u = entityManager.find(User.class, id_user);
            //command for queue
            Set<Permission> currPermissions = new HashSet<Permission>();
            for (Role role : u.getRoles()) {
                for (Permission perm : role.getPermissions()) {                    
                    currPermissions.add(perm);
                }
            }
            //insert command
            for (Permission p : currPermissions) {
                Command c = new Command(u.getIdUser(), p.getApplication().getIdApp(), 
                        p.getPrivelege().getIdPriv(),"del",new Date());
                entityManager.persist(c);
            }
            u.getRoles().clear();
            entityManager.remove(u);
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
