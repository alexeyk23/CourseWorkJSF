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
import java.util.ArrayList;
import java.util.Date;
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
public class RoleDAO {

    public static boolean addRole(Role r) throws Exception {
        EntityManager entityManager = null; 
        try {
            entityManager=UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
             //проверяем, есть ли уже такое
            Query q = entityManager.createQuery("SELECT a FROM Role a WHERE a.nameRole=?1 ")
                    .setParameter(1, r.getNameRole());
            if (q.getResultList().size() > 0) {
                return false;
            }
            entityManager.merge(r);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager!=null && entityManager.getTransaction() != null) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            if(entityManager!=null) {
                entityManager.close();
            }
        }
        return true;
    }

    public static List<Role> getAllRole() throws Exception {
        EntityManager entityManager = null;
        List<Role> roleList=null;
        try {          
            entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createQuery("SELECT u FROM Role u");
            roleList = (List<Role>) q.getResultList();
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
        return roleList;
    }
    public static Set<Role> getAllRoleById(List<String> ids) throws Exception {
        EntityManager entityManager = null;
        Set<Role> roleList=null;
        try {          
            entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();          
            roleList = new HashSet<Role>();
            for (String id : ids) {
                roleList.add(entityManager.find(Role.class, Integer.valueOf(id)));
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
        return roleList;
    }
    public static Role getRoleById(int id_role) throws Exception {       
        Role res =null;
        EntityManager entityManager = null;        
        try {          
            entityManager = UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            res = entityManager.find(Role.class, id_role);
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

    public static boolean updateRole(int id_role, String nameRole, List<String> permIds) throws Exception {
        EntityManager entityManager = null; 
        try {
            entityManager=UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            Role r = entityManager.find(Role.class, id_role);
            if(r.getNameRole().equals(nameRole))
                return false;
            r.setNameRole(nameRole);          
            Set<Permission> currPerm = new HashSet<Permission>();
            currPerm.addAll(r.getPermissions());
            Set<User> rolesUsers = r.getUsers();
            //удаляем разрешения
            for (Permission perm : currPerm) {
                if (!permIds.contains(String.valueOf(perm.getIdPerm()))) {
                    for (User user : rolesUsers) {
                        Map<Integer, Integer> dictPerm = UserDAO.createPermMap(user);
                        Integer value = dictPerm.get(perm.getIdPerm());
                        //если это разрешение один раз встречалось, то его мы удалим
                        if (value != null && value == 1) {
                            Command c = new Command(user.getIdUser(), perm.getApplication().getIdApp(),
                                    perm.getPrivelege().getIdPriv(), "revoke priv", new Date());
                            entityManager.persist(c);
                        }
                    }
                    r.getPermissions().remove(perm);
                }
            }
            //добавляем разрешение
            for (String permid : permIds) {
                Permission perm = entityManager.find(Permission.class, Integer.valueOf(permid));                
                for (User user : rolesUsers) {
                    Map<Integer, Integer> dictPerm = UserDAO.createPermMap(user);
                    Integer value = dictPerm.get(perm.getIdPerm());
                    //если такого разрешения заведомо не было, то добавим его
                    if (value == null) {
                        Command c = new Command(user.getIdUser(), perm.getApplication().getIdApp(),
                                perm.getPrivelege().getIdPriv(), "add", new Date());
                        entityManager.persist(c);
                    }                    
                }
                r.getPermissions().add(perm);
            }
            entityManager.merge(r);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
           if (entityManager!=null && entityManager.getTransaction() != null) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            if(entityManager!=null) {
                entityManager.close();
            }
        }
        return true;
    }

    public static void deleteRole(int id_role) throws Exception {
        EntityManager entityManager = null; 
        try {
            entityManager=UtilHibernate.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            Role r = entityManager.find(Role.class, id_role);
            for (User u : r.getUsers()) {
                u.getRoles().remove(r);
            }
            r.getPermissions().clear();
            entityManager.remove(r);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
           if (entityManager!=null && entityManager.getTransaction() != null) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            if(entityManager!=null) {
                entityManager.close();
            }
        }
    }
}
