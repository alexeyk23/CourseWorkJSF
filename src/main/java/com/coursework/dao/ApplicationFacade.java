/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this tentityManagerplate file, choose Tools | TentityManagerplates
 * and open the tentityManagerplate in the editor.
 */
package com.coursework.dao;

import com.coursework.dao.old.PrivilegeDAO;
import com.coursework.model.Application;
import com.coursework.model.Command;
import com.coursework.model.Permission;
import com.coursework.model.Privilege;
import com.coursework.model.Role;
import com.coursework.model.User;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
public class ApplicationFacade extends AbstractFacade<Application> {
    @PersistenceContext(unitName = "manager")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
    public boolean addApp(Application app) {
        Query q = entityManager.createQuery("SELECT a FROM Application a WHERE a.nameApp=?1 ")
                .setParameter(1, app.getNameApp());
        if (q.getResultList().size() > 0) {
            return false;
        }
        super.edit(app);
        return true;
    }
    public void deleteApp(int id_app)
    {
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
    }
    public boolean updateApp(int id_app, String nameApp, List<String> selectedPrivs) throws Exception
    {
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
        return true;
    }
    public ApplicationFacade() {
        super(Application.class);
    }
    
}
