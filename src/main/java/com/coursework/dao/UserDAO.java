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
        EntityManager entityManager = null;
        try {
            entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            int idUser = entityManager.merge(u).getIdUser();  //!!!!!!!! !!!!!!!!!!! M E R G E 
            //приложения, в которых пользователь уже доавблен
            Set<Integer> addAppsId = new HashSet<Integer>();
            for (Role role : u.getRoles()) {
                for (Permission perm : role.getPermissions()) {
                    int idApp = perm.getApplication().getIdApp();
                    Command c = new Command(idUser, idApp, "add user", new Date());
                    if (!addAppsId.contains(idApp)) {
                        entityManager.persist(c);
                        addAppsId.add(idApp);
                    }
                    c.setOperation("grant priv");
                    c.setIdPrivilege(perm.getPrivelege().getIdPriv());
                    c.setDateMake(new Date());
                    entityManager.persist(c);
                }
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction() != null) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public static List<User> getAllUsers() throws Exception  {
        EntityManager entityManager = null;
        List<User> userList = null;
        try {
            entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createQuery("SELECT u FROM User u");
            userList = q.getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction() != null) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return userList;
    }

    public static User getUserById(int id_user) throws Exception  {
        User res = null;
        EntityManager entityManager = null;
        try {
            entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            res = entityManager.find(User.class, id_user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction() != null) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return res;
    }

    //создание частотного словаря разрешений для юзера
    public static Map<Integer, Integer> createPermMap(User user) {
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

    public static void updateUser(int id_user, String userName, Date dateofb, List<String> roleIds)throws Exception  {
        EntityManager entityManager = null;
        try {
            entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
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
                    if (foundPerm != null) {
                        dictPerm.put(perm.getIdPerm(), foundPerm + 1);
                    }
                }
            }
            //удаление роли, если ее нет в списке выбранных
            for (Role role : currRoles) {
                if (!roleIds.contains(String.valueOf(role.getIdRole()))) {
                    {
                        for (Permission perm : role.getPermissions()) {
                            dictPerm.put(perm.getIdPerm(), dictPerm.get(perm.getIdPerm()) - 1);
                        }
                        upUser.getRoles().remove(role);
                    }
                }
            }
            //устанавливаем новые роли
            for (String id : roleIds) {
                Role r = entityManager.find(Role.class, Integer.valueOf(id));
                for (Permission perm : r.getPermissions()) {
                    Integer foundPerm = dictPerm.put(perm.getIdPerm(), 0);
                    if (foundPerm != null) {
                        //если попытаемся вернуть удаленную роль то foundPerm<0
                        //роль повторно не назначаем, поэтому foundPerm+2.
                        dictPerm.put(perm.getIdPerm(), foundPerm < 0 ? foundPerm + 2 : foundPerm + 1);
                    }
                }
                upUser.getRoles().add(r);
            }
            for (Map.Entry<Integer, Integer> entrySet : dictPerm.entrySet()) {
                Integer key = entrySet.getKey();
                Integer value = entrySet.getValue();
                //если значение value<=0, то разрешение либо удалим, либо добавим
                if (value <= 0) {
                    Permission newPermission = entityManager.find(Permission.class, key);
                    Command command = new Command(id_user, newPermission.getApplication().getIdApp(),
                            newPermission.getPrivelege().getIdPriv(), (value == 0) ? "grant priv" : "revoke priv", new Date());
                    entityManager.persist(command);
                }
            }
            entityManager.merge(upUser);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction() != null) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public static void deleteUser(int id_user) throws Exception {
        EntityManager entityManager = null;
        try {
            entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            User u = entityManager.find(User.class, id_user);
            Set<Integer> delAppsId = new HashSet<Integer>();
            for (Role role : u.getRoles()) {
                for (Permission perm : role.getPermissions()) {
                    int idApp = perm.getApplication().getIdApp();
                    if (!delAppsId.contains(idApp)) {
                        Command c = new Command(u.getIdUser(), idApp, "del user", new Date());
                        delAppsId.add(idApp);
                        entityManager.persist(c);
                    }
                }
            }
            u.getRoles().clear();
            entityManager.remove(u);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction() != null) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
