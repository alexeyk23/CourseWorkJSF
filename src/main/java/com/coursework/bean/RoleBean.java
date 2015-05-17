/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursework.bean;

import com.coursework.dao.PermissionFacade;
import com.coursework.dao.RoleFacade;
import com.coursework.model.Permission;
import com.coursework.model.Role;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author admin
 */
@ViewScoped
@Named
public class RoleBean implements Serializable {

    private int idRole;
    private String nameRole; 
    private List<String> selectedPermission = new ArrayList<String>();
    @EJB
    RoleFacade roleDao;
    @EJB
    PermissionFacade permissionDao;
    /**
     * Добавить роль
     */
    public void addRole() {       
        Set<Permission> perm = new HashSet<Permission>();
        try {
            for (String permName : selectedPermission) {
                perm.add(permissionDao.find(Integer.valueOf(permName)));
            }
            Role r = new Role(nameRole, perm);
            if (!roleDao.addRole(r)) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("nameInput", new FacesMessage(FacesMessage.SEVERITY_WARN, "Такое имя используется", null));
            }
        } catch (Exception e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("error", new FacesMessage(FacesMessage.SEVERITY_WARN, "Ошибка соединения", null));

        }
    }

    /**
     * Редактировать роль      
     */
    public void updateRole() 
    {
       try{
            if (!roleDao.updateRole(idRole,nameRole,selectedPermission)) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("nameInput", new FacesMessage(FacesMessage.SEVERITY_WARN, "Такое имя используется", null));
            }
        } catch (Exception e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("error", new FacesMessage(FacesMessage.SEVERITY_WARN, "Ошибка соединения", null));
        }
    }     
    
    
    /**
     * Удалить роль
     */
    public void deleteRole()  {
        try{
           roleDao.deleteRole(idRole);
        } catch (Exception e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("error", new FacesMessage(FacesMessage.SEVERITY_WARN, "Ошибка соединения", null));
        }        
    }

    /**
     * Получить список всех ролей
     * @return
     */
    public List<Role> getAllRole() {
        List<Role> listRole =  new ArrayList<Role>();
        try {
            listRole = roleDao.findAll();
        } catch (Exception ex) {
            Logger.getLogger(RoleBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listRole;
    }

    /**
     * Получить список ID(String) выбранных разрешений
     * @return
     */
    public List<String> getSelectedPermission() {
        return selectedPermission;
    }

    /**
     * Установить список ID(String) выбранных разрешений
     * @param selectedPermission
     */
    public void setSelectedPermission(List<String> selectedPermission) {
        this.selectedPermission = selectedPermission;
    }

    /**
     * Получить ID роли
     *
     * @return ID роли
     */
    public int getIdRole() {
        return idRole;
    }

    /**
     * Установть ID роли
     *
     * @param idRole новое значение ID роли
     */
    public void setIdRole(int idRole) {
        this.idRole = idRole;
        //если ID > 0, то такую роль загружаем из базы
        if(idRole>0)
        {
            try {
                Role r =roleDao.find(idRole);
                nameRole=r.getNameRole();
                selectedPermission.clear();
                for (Permission perm : r.getPermissions()) {
                    selectedPermission.add(String.valueOf(perm.getIdPerm()));
                }
            } catch (Exception ex) {
                Logger.getLogger(RoleBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Получить имя роли
     *
     * @return имя роли
     */
    public String getNameRole() {
        return nameRole;
    }

    /**
     * Установить имя роли
     *
     * @param nameRole новое значение имени роли
     */
    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    /**
     * Пустой конструктор
     */
    public RoleBean() {
    }
}
