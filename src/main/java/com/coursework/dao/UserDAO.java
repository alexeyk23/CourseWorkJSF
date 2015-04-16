/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursework.dao;

import com.coursework.model.Command;
import com.coursework.model.Permission;
import com.coursework.model.Role;
import com.coursework.model.User;
import com.coursework.util.UtilHibernate;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
            int idUser = entityManager.merge(u).getIdUser();  //!!!!!!!! !!!!!!!!!!! M E R G E            
            for (Role role : u.getRoles()) {
                for (Permission perm : role.getPermissions()) {
                     Command c = new Command(idUser, perm.getApplication().getIdApp(),
                        perm.getPrivelege().getIdPriv(), "add", new Date());
                    entityManager.persist(c);
                }
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
    public static Map<Integer,Integer> createPermMap(User user) {
        Map<Integer, Integer> dictPerm = new HashMap<Integer, Integer>();
        for (Role role : user.getRoles()) {
            for (Permission p : role.getPermissions()) {
                Integer foundPerm = dictPerm.put(p.getIdPerm(), 0);
                if (foundPerm != null) {
                    dictPerm.put(p.getIdPerm(), foundPerm + 1);
                }
            }
        }
        return dictPerm;
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
            //частотный словарь разрешений
            //после всех операций ключи с отрицательным значением удалим,
            //а с = 0 запишем как новые разрешения.
            Map<Integer, Integer> dictPerm = new HashMap<Integer, Integer>();
            for (Role role : currRoles) {
                for (Permission perm : role.getPermissions()) {
                    Integer foundPerm = dictPerm.put(perm.getIdPerm(), 0);
                    if (foundPerm != null)
                        dictPerm.put(perm.getIdPerm(), foundPerm+1);
                }
            }
            //delete role
            for (Role role : currRoles) {
                if (!roleIds.contains(String.valueOf(role.getIdRole()))) {
                    {
                        for (Permission perm : role.getPermissions())
                            dictPerm.put(perm.getIdPerm(), dictPerm.get(perm.getIdPerm()) - 1);
                        upUser.getRoles().remove(role);
                    }
                }
            }
            //set new roles
            for (String id : roleIds) {
                Role r = entityManager.find(Role.class, Integer.valueOf(id));
                for (Permission perm : r.getPermissions()) {
                    Integer foundPerm = dictPerm.put(perm.getIdPerm(), 0);
                    if (foundPerm != null) {
                        //если попытаемся вернуть удаленную роль то foundPerm<0
                        //роль повторно не назначаем, поэтому foundPerm+2.
                        dictPerm.put(perm.getIdPerm(), foundPerm<0?foundPerm+2:foundPerm+1);
                    }
                }
                upUser.getRoles().add(r);
            }
            for (Map.Entry<Integer, Integer> entrySet : dictPerm.entrySet()) {
                Integer key = entrySet.getKey();
                Integer value = entrySet.getValue();
                //if value<=0 then permission will be deleted or persist
                if (value <= 0) {
                    Permission newPermission = entityManager.find(Permission.class, key);
                    Command command = new Command(id_user, newPermission.getApplication().getIdApp(),
                            newPermission.getPrivelege().getIdPriv(), (value == 0) ? "add" : "del", new Date());
                    entityManager.persist(command);
                }
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
            for (Role role : u.getRoles()) {
                for (Permission perm : role.getPermissions()) {
                    Command c = new Command(u.getIdUser(), perm.getApplication().getIdApp(),
                        perm.getPrivelege().getIdPriv(), "del", new Date());
                    entityManager.persist(c);
                }
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
