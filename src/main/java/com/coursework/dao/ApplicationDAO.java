/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursework.dao;

import com.coursework.model.Application;
import com.coursework.model.Command;
import com.coursework.model.Permission;
import com.coursework.model.Privilege;
import com.coursework.model.Role;
import com.coursework.model.User;
import com.coursework.util.UtilHibernate;
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
public class ApplicationDAO {

    public static boolean addApp(Application r) throws Exception {
        EntityManager entityManager = null;
        try {
            entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
             //проверяем, есть ли уже такое
            Query q = entityManager.createQuery("SELECT a FROM Application a WHERE a.nameApp=?1 ")
                    .setParameter(1, r.getNameApp());
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
        return  true;
    }

    public static List<Application> getAllApp() throws Exception {
        EntityManager entityManager = null;
        List<Application> appList = null;
        try {
            entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createQuery("SELECT u FROM Application u");
            appList = q.getResultList();
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
        return appList;
    }

    public static Application getAppById(int id_app) throws Exception {
        Application res = null;
        EntityManager entityManager = null;
        try {
            entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            res = entityManager.find(Application.class, id_app);
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

    public static boolean updateApp(int id_app, String nameApp, List<String> selectedPrivs) throws Exception {
        EntityManager entityManager = null;
        try {
            entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            Application app = entityManager.find(Application.class, id_app);
            if(app.getNameApp().equals(nameApp))
                return false;
            app.setNameApp(nameApp);
            Set<Privilege> currPrivs = new HashSet<Privilege>();
            currPrivs.addAll(app.getPrivs());
            //удаляем привилегию
            for (Privilege privilege : currPrivs) {
                if (!selectedPrivs.contains(String.valueOf(privilege.getIdPriv()))) {
                    for (Permission permission : app.getPermissions()) {
                        //если удаляем привилегию у приложения, на которое есть разрешение,
                        //то следует записать команду для каждого пользователя
                        if (permission.getPrivelege().getIdPriv() == privilege.getIdPriv()) {
                            for (Role role : permission.getRoles()) {
                                for (User user : role.getUsers()) {
                                    Command command = new Command(user.getIdUser(), permission.getApplication().getIdApp(), permission.getPrivelege().getIdPriv(), "revoke priv", new Date());
                                    entityManager.persist(command);
                                }
                            }
                            app.getPermissions().remove(permission);
                        }
                    }
                    app.getPrivs().remove(privilege);
                }
            }
            //добавляем привилегию
            for (String idPriv : selectedPrivs) {
                Privilege p = PrivilegeDAO.getPrivilegeById(Integer.valueOf(idPriv));
                app.getPrivs().add(p);
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
        return true;
    }

    public static void deleteApp(int id_app) throws Exception {
        EntityManager entityManager = null;
        try {
            entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            Application app = entityManager.find(Application.class, id_app);
            for (Permission p : app.getPermissions()) {
                for (Role role : p.getRoles()) {
                    role.getPermissions().remove(p);
                }
                entityManager.remove(p);
            }
            for (Privilege p : app.getPrivs()) {
                p.getApps().remove(app);
            }
            entityManager.remove(app);
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
