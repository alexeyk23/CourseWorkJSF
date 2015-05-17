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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author User
 */
@Stateless
public class RoleFacade extends AbstractFacade<Role> {
    @PersistenceContext(unitName = "manager")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
    public boolean addRole(Role r) {
        //проверяем, есть ли уже такое
        Query q = entityManager.createQuery("SELECT a FROM Role a WHERE a.nameRole=?1 ")
                .setParameter(1, r.getNameRole());
        if (q.getResultList().size() > 0) {
            return false;
        }
        entityManager.merge(r);
        return true;
    }
    public Set<Role> getAllRoleById(List<String> ids){
        Set<Role> roleList = null;
        roleList = new HashSet<Role>();
        for (String id : ids) {
            roleList.add(entityManager.find(Role.class, Integer.valueOf(id)));
        }
        return roleList;
    }
     public  boolean updateRole(int id_role, String nameRole, List<String> permIds){    
            Role r = entityManager.find(Role.class, id_role);            
            r.setNameRole(nameRole);          
            Set<Permission> currPerm = new HashSet<Permission>();
            currPerm.addAll(r.getPermissions());
            Set<User> rolesUsers = r.getUsers();
            //удаляем разрешения
            for (Permission perm : currPerm) {
                if (!permIds.contains(String.valueOf(perm.getIdPerm()))) {
                    for (User user : rolesUsers) {
                        Map<Integer, Integer> dictPerm = UserFacade.createPermMap(user);
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
                    Map<Integer, Integer> dictPerm = UserFacade.createPermMap(user);
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
        return true;
    }

    public  void deleteRole(int id_role) {       
            Role r = entityManager.find(Role.class, id_role);
            for (User u : r.getUsers()) {
                u.getRoles().remove(r);
            }
            r.getPermissions().clear();
            entityManager.remove(r);       
    }
    public RoleFacade() {
        super(Role.class);
    }
    
}
