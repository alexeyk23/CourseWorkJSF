/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.curswork.bean;

import com.curswork.dao.ApplicationDAO;
import com.curswork.model.Application;
import java.io.Serializable;
import java.util.List;
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
    private String name_app;
    
    public void addApplication()
    {
        Application p = new Application(name_app);
        ApplicationDAO.addApp(p);
    }
    public List<Application> getListApplication() {
        return ApplicationDAO.getAllApp();
    }
    /**
     * Get the value of name_app
     *
     * @return the value of name_app
     */
    public String getName_app() {
        return name_app;
    }

    /**
     * Set the value of name_app
     *
     * @param name_app new value of name_app
     */
    public void setName_app(String name_app) {
        this.name_app = name_app;
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