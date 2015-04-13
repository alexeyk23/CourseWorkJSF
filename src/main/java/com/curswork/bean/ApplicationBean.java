/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.curswork.bean;

import com.curswork.dao.ApplicationDAO;
import com.curswork.dao.PrivilegeDAO;
import com.curswork.model.Application;
import com.curswork.model.Privilege;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
        for (String ids : selectedPrivs) {
            privileges.add(PrivilegeDAO.getPrivilegeById(Integer.valueOf(ids)));
        }
        Application p = new Application(nameApp,privileges);
        ApplicationDAO.addApp(p);
    }
    public void deleteApp() throws Exception
    {
        ApplicationDAO.deleteApp(idApp);
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
    }

    public ApplicationBean() {
    }
        
}
