/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursework.bean;

import com.coursework.dao.ApplicationDAO;
import com.coursework.dao.PermissionDAO;
import com.coursework.dao.PrivilegeDAO;
import com.coursework.model.Application;
import com.coursework.model.Permission;
import com.coursework.model.Privilege;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Kunakovsky A.
 */
@ViewScoped
@Named
public class PermissionBean implements Serializable{

    private int idPerm;
    private String appId;
    private String privilegeId;
    Application app;
    Privilege priv;

    /**
     * Удалить разрешение
     * @throws Exception
     */
    public void deletePermission() throws Exception {
        PermissionDAO.deletePermission(idPerm);
    }

    /**
     * Добавить разрешение
     */
    public void addPermission() {
        try {
            priv = PrivilegeDAO.getPrivilegeById(Integer.valueOf(privilegeId));
            Permission p = new Permission(app, priv);
            if (!PermissionDAO.addPermission(p)) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("messPerm", new FacesMessage(FacesMessage.SEVERITY_WARN, "Такое разрешение уже существует!", null));
            }
        } catch (Exception ex) {
            Logger.getLogger(PermissionBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("errors", new FacesMessage(FacesMessage.SEVERITY_WARN, "Ошибка соединения", null));
        }
    }

    /**
     * Получить список разрешений
     * @return список разрешений
     */ 
    public List<Permission> getListPermission() {
        List<Permission> res = new ArrayList<Permission>();
        try {
           res  = PermissionDAO.getAllPermission();
        } catch (Exception ex) {
            Logger.getLogger(PermissionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  res;
    }

    /**
     * Получить приложение, учавствующее в разрешении
     * @return приложение
     */
    public Application getApp() {
        return app;
    }

    /**
     * Установить приложение, учавствующее в разрешении
     * @param app - новое приложение
     */
    public void setApp(Application app) {
        this.app = app;
    }

    /**
     * Получить привилегию, учавствующую в разрешении
     * @return привилегия
     */
    public Privilege getPriv() {
        return priv;
    }

    /**
     * Установить привилегию, учавствующую в разрешении
     * @param priv - новая привилегия
     */
    public void setPriv(Privilege priv) {
        this.priv = priv;       
    }

    /**
     * Получить значение ID выбранной привилегии
     * @return значение ID
     */
    public String getPrivilegeId() {
        return privilegeId;
    }

    /**
     * Установить значение ID выбранной привилегии
     *
     * @param privilegeId новое значение ID выбранной привилегии
     */
    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }

    /**
     * Получить значение ID выбранного приложения
     * @return значение ID выбранного приложения
     */
    public String getAppId() {
        return appId;
    }

    /**
     * Устнановить значение ID выбранного приложения
     *
     * @param appId новое значение ID выбранного приложения
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * Получить значение ID разрешения
     *
     * @return значение ID разрешения
     */
    public int getIdPerm() {
        return idPerm;
    }

    /**
     * Установить значение ID разрешения
     *
     * @param idPerm новое значение ID разрешения
     */
    public void setIdPerm(int idPerm) {
        this.idPerm = idPerm;
    }

    /**
     * Обработик события ValueChangeEvent
     * @param event
     */
    public void changeApp(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            try {
                app = ApplicationDAO.getAppById(Integer.valueOf((String) event.getNewValue()));
            } catch (Exception ex) {
                Logger.getLogger(PermissionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else {
            app=null;
        }
    }

    /**
     * Пустой конструктор
     */
    public PermissionBean() {
    }

}
