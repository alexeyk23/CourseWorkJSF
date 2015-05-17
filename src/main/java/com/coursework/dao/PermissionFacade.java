/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this tentityManagerplate file, choose Tools | TentityManagerplates
 * and open the tentityManagerplate in the editor.
 */
package com.coursework.dao;

import com.coursework.model.Command;
import com.coursework.model.Permission;
import com.coursework.model.Role;
import com.coursework.model.User;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author User
 */
@Stateless
public class PermissionFacade extends AbstractFacade<Permission> {
    @PersistenceContext(unitName = "manager")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
    
    public void deletePermission(int id_perm) throws Exception {
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
    }
    
    public  boolean addPermission(Permission r)throws Exception  {
            //проверяем, есть ли уже такое
            Query q = entityManager.createQuery("SELECT u FROM Permission u WHERE u.application=?1 and u.privelege=?2")
                    .setParameter(1, r.getApplication())
                    .setParameter(2, r.getPrivelege());
            if (q.getResultList().size() > 0) {
                return false;
            }
            entityManager.merge(r);        
        return true;
    }
    
    public PermissionFacade() {
        super(Permission.class);
    }
    
}
