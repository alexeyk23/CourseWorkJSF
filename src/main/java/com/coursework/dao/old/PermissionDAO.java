/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursework.dao.old;

import com.coursework.model.Command;
import com.coursework.model.Permission;
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
public class PermissionDAO {

    public static boolean addPermission(Permission r)throws Exception  {
        EntityManager entityManager = null;
        try {
            entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            //проверяем, есть ли уже такое
            Query q = entityManager.createQuery("SELECT u FROM Permission u WHERE u.application=?1 and u.privelege=?2")
                    .setParameter(1, r.getApplication())
                    .setParameter(2, r.getPrivelege());
            if (q.getResultList().size() > 0) {
                return false;
            }
            entityManager.merge(r);
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

    public static List<Permission> getAllPermission()throws Exception  {
        List<Permission> permissionList = null;
        EntityManager entityManager = null;
        try {
            entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createQuery("SELECT u FROM Permission u");
            permissionList = (List<Permission>) q.getResultList();
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
        return permissionList;
    }

    public static Permission getPermissionById(int id_perm)throws Exception  {
        Permission res = null;
        EntityManager entityManager = null;
        try {
            entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            res = entityManager.find(Permission.class, id_perm);
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

    public static void deletePermission(int id_perm) throws Exception {
        EntityManager entityManager = null;
        try {
            entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            Permission perm = entityManager.find(Permission.class, id_perm);
            for (Role role : perm.getRoles()) {
                for (User user : role.getUsers()) {
                    Command c = new Command(user.getIdUser(), perm.getApplication().getIdApp(),
                            perm.getPrivelege().getIdPriv(), "revoke priv", new Date());
                    entityManager.persist(c);
                }
                role.getPermissions().remove(perm);
            }
            entityManager.remove(perm);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if ( entityManager!=null && entityManager.getTransaction() != null) {
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
