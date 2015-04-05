/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.curswork.bean;

import com.curswork.dao.ApplicationDAO;
import com.curswork.dao.PermissionDAO;
import com.curswork.dao.PrivilegeDAO;
import com.curswork.model.Application;
import com.curswork.model.Permission;
import com.curswork.model.Privilege;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Kunakovsky A.
 */
@ManagedBean
@SessionScoped
public class PermissionBean {

    private int idPerm;
    private String appId;
    private String privilegeId;
    Application app;
    Privilege priv;

    public void deletePermission() throws Exception {
        PermissionDAO.deletePermission(idPerm);
    }

    public void addPermission() {
        priv = PrivilegeDAO.getPrivilegeById(Integer.valueOf(privilegeId));
        Permission p = new Permission(app, priv);
        PermissionDAO.addPermission(p);
    }

    public List<Permission> getListPermission() {
        return PermissionDAO.getAllPermission();
    }

    public Application getApp() {
        return app;
    }

    public void setApp(Application app) {
        this.app = app;
    }

    public Privilege getPriv() {
        return priv;
    }

    public void setPriv(Privilege priv) {
        this.priv = priv;       
    }

    /**
     * Get the value of privilegeId
     *
     * @return the value of privilegeId
     */
    public String getPrivilegeId() {
        return privilegeId;
    }

    /**
     * Set the value of privilegeId
     *
     * @param privilegeId new value of privilegeId
     */
    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }

    /**
     * Get the value of appId
     *
     * @return the value of appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * Set the value of appId
     *
     * @param appId new value of appId
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * Get the value of idPerm
     *
     * @return the value of idPerm
     */
    public int getIdPerm() {
        return idPerm;
    }

    /**
     * Set the value of idPerm
     *
     * @param idPerm new value of idPerm
     */
    public void setIdPerm(int idPerm) {
        this.idPerm = idPerm;
//        if(idPerm>0)
//        {
//            Permission p = PermissionDAO.getPermissionById(idPerm);            
//        }
    }

    public void changeApp(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            app = ApplicationDAO.getAppById(Integer.valueOf((String) event.getNewValue()));
        }
        else app=null;
    }

    public PermissionBean() {
    }

}
