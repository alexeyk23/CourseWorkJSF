/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.coursework.bean;

import com.coursework.dao.ApplicationDAO;
import com.coursework.dao.PrivilegeDAO;
import com.coursework.model.Application;
import com.coursework.model.Privilege;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author admin
 */
@ManagedBean
@ViewScoped
public class ApplicationBean implements Serializable {  
    
    private int idApp;
    private String nameApp;
    private Set<Privilege> privileges = new HashSet<Privilege>();
    private List<String> selectedPrivs = new ArrayList<String>();

    /**
     * Добавить приложение
     */
    public void addApplication()
    {
        privileges.clear();
        try {
            for (String ids : selectedPrivs) {
                privileges.add(PrivilegeDAO.getPrivilegeById(Integer.valueOf(ids)));
            }
             Application p = new Application(nameApp,privileges);
             if (!ApplicationDAO.addApp(p)) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("nameInput", new FacesMessage(FacesMessage.SEVERITY_WARN, "Такое имя используется", null));
            }
        } catch (Exception e) {
             FacesContext fc = FacesContext.getCurrentInstance();
             fc.addMessage("errors", new FacesMessage(FacesMessage.SEVERITY_WARN, "Ошибка соединения", null)); 
        }

    }

    /**
     * Редактировать приложение
     */
    public void updateApp() {
        try {
            if (!ApplicationDAO.updateApp(idApp, nameApp, selectedPrivs)) {
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage("nameInput", new FacesMessage(FacesMessage.SEVERITY_WARN, "Такое имя используется", null));
            }
        } catch (Exception ex) {
            Logger.getLogger(ApplicationBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("errors", new FacesMessage(FacesMessage.SEVERITY_WARN, "Ошибка соединения", null));
        }
    }

    /**
     * Удалить приложение
     */
    public void deleteApp() 
    {
        try{
           ApplicationDAO.deleteApp(idApp);
        }
        catch(Exception e){
            Logger.getLogger(ApplicationBean.class.getName()).log(Level.SEVERE, null, e);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("errors", new FacesMessage(FacesMessage.SEVERITY_WARN, "Ошибка соединения", null));
        }
    }

    /**
     * Получить список приложений
     * @return список приложений    
     */
    public List<Application> getListApplication() {
        List<Application> listApp = new ArrayList<Application>();
        try {
             listApp =  ApplicationDAO.getAllApp();
        } catch (Exception ex) {
            Logger.getLogger(ApplicationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listApp;
    }

    /**
     * Получить привилегии для приложения
     * @return привилегии для приложения
     */
    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    /**
     * Получить выбранные ID привилегий
     * @return выбранные ID привилегий
     */
    public List<String> getSelectedPrivs() {
        return selectedPrivs;
    }

    /**
     * Установить выбранные ID привилегий
     * @param selectedPrivs новые выбранные ID привилегий
     */
    public void setSelectedPrivs(List<String> selectedPrivs) {
        this.selectedPrivs = selectedPrivs;
    }

    /**
     * Установить привилегии для приложения
     * @param privileges новые привилегии для приложения
     */
    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges = privileges;
    }
    /**
     * Получить имя приложения
     *
     * @return имя приложения
     */
    public String getNameApp() {
        return nameApp;
    }

    /**
     * Установить имя приложения
     *
     * @param nameApp новое имя приложения
     */
    public void setNameApp(String nameApp) {
        this.nameApp = nameApp;
    }

    /**
     * Получить ID приложения
     *
     * @return ID приложения
     */
    public int getIdApp() {
        return idApp;
    }

    /**
     * Установить ID приложения
     *
     * @param idApp новое ID приложения
     */
    public void setIdApp(int idApp) {
        this.idApp = idApp;
        //если ID > 0, то загружаем из базы приложение
        if(idApp>0){
            try {
                Application application= ApplicationDAO.getAppById(idApp);
                nameApp=application.getNameApp();
                selectedPrivs.clear();
                for (Privilege privilege : application.getPrivs()) {
                    selectedPrivs.add(String.valueOf(privilege.getIdPriv()));
                }
            } catch (Exception ex) {
                Logger.getLogger(ApplicationBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Пустой конструктор
     */
    public ApplicationBean() {
    }
        
}
