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
import javax.faces.context.FacesContext;

/**
 *
 * @author admin
 */
@ManagedBean
@SessionScoped
public class ApplicationBean implements Serializable {  
    
    private int idApp;
    private String nameApp;
    private Set<Privilege> privileges = new HashSet<Privilege>();
    private List<String> selectedPrivs = new ArrayList<String>();

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
    public void updateApp()
    {
        try {
            ApplicationDAO.updateApp(idApp, nameApp, selectedPrivs);
        } catch (Exception ex) {
            Logger.getLogger(ApplicationBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("errors", new FacesMessage(FacesMessage.SEVERITY_WARN, "Ошибка соединения", null)); 

        }
    }
    public void deleteApp() 
    {
        try{
           ApplicationDAO.deleteApp(idApp);
        }
        catch(Exception e)
        {
            Logger.getLogger(ApplicationBean.class.getName()).log(Level.SEVERE, null, e);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("errors", new FacesMessage(FacesMessage.SEVERITY_WARN, "Ошибка соединения", null));
        }
    }
    public List<Application> getListApplication() {
        return ApplicationDAO.getAllApp();
    }
    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public List<String> getSelectedPrivs() {
        return selectedPrivs;
    }

    public void setSelectedPrivs(List<String> selectedPrivs) {
        this.selectedPrivs = selectedPrivs;
    }
    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges = privileges;
    }
    /**
     * Get the value of nameApp
     *
     * @return the value of nameApp
     */
    public String getNameApp() {
        return nameApp;
    }

    /**
     * Set the value of nameApp
     *
     * @param nameApp new value of nameApp
     */
    public void setNameApp(String nameApp) {
        this.nameApp = nameApp;
    }

    /**
     * Get the value of idApp
     *
     * @return the value of idApp
     */
    public int getIdApp() {
        return idApp;
    }

    /**
     * Set the value of idApp
     *
     * @param idApp new value of idApp
     */
    public void setIdApp(int idApp) {
        this.idApp = idApp;
        if(idApp>0){
            Application application= ApplicationDAO.getAppById(idApp);
            nameApp=application.getNameApp();
            selectedPrivs.clear();
            for (Privilege privilege : application.getPrivs()) {
                selectedPrivs.add(String.valueOf(privilege.getIdPriv()));
            }
        }
    }

    public ApplicationBean() {
    }
        
}
