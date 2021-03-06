/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursework.dao.old;

import com.coursework.model.Application;
import com.coursework.model.Command;
import com.coursework.model.Permission;
import com.coursework.model.Privilege;
import com.coursework.model.Role;
import com.coursework.model.User;
import com.coursework.util.UtilHibernate;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * 
 * @author Kunakovsky A.
 */
public class PrivilegeDAO {

    /**
     * Добавить привилегию
     * @param r - привилегия
     * @return true если добавление, false если привилегия с таким именем есть
     * @throws Exception
     */
    public static boolean addPrivilege(Privilege r) throws Exception {
        EntityManager entityManager = null;
        try {
            entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
             //проверяем, есть ли уже такое
            Query q = entityManager.createQuery("SELECT p FROM Privilege p WHERE p.namePriv=?1 ")
                    .setParameter(1, r.getNamePriv());
            if (q.getResultList().size()>0) {
                return false;
            }
            entityManager.persist(r);
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
        return true;
    }

    /**
     * Получить список всех привилегий
     * @return список всех привилегий
     * @throws Exception
     */
    public static List<Privilege> getAllPrivilege() throws Exception {
        List<Privilege> privList = null;
        EntityManager entityManager = null;
        try {
            entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createQuery("SELECT u FROM Privilege u");
            privList = (List<Privilege>) q.getResultList();
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

        return privList;
    }

    /**
     * Получить привилегию по ID
     * @param id_priv - ID привилегии
     * @return найденная привилегия
     * @throws Exception
     */
    public static Privilege getPrivilegeById(int id_priv) throws Exception {
        Privilege res = null;
        EntityManager entityManager = null;
        try {
            entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            res = entityManager.find(Privilege.class, id_priv);
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

    /**
     * Удаление привилегии по ID
     * @param id_priv ID привилегии
     * @throws Exception
     */
    public static void deletePrivilege(int id_priv) throws Exception {
        EntityManager entityManager = null;
        try {
            entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            Privilege privilege = entityManager.find(Privilege.class, id_priv);
            for (Application app : privilege.getApps()) {
                for (Permission perm : app.getPermissions()) {
                    for (Role role : perm.getRoles()) {
                        for (User user : role.getUsers()) {
                            Command c = new Command(user.getIdUser(), perm.getApplication().getIdApp(),
                                    perm.getPrivelege().getIdPriv(), "revoke priv", new Date());
                            entityManager.persist(c);
                        }
                    }
                }
                app.getPrivs().remove(privilege);
            }
            entityManager.remove(privilege);
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
