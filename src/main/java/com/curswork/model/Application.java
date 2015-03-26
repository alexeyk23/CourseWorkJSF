/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.curswork.model;

import com.curswork.bean.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author admin
 */
@ManagedBean
@Entity
@Table(name = "\"Application\"")
public class Application implements Serializable {
    @Id
    @Column(name = "id_app")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id_app_gen")
    @SequenceGenerator(name = "id_app_gen", sequenceName = "\"Application_id_app_seq\"")
    private int idApp;
    @Column(name = "name_app")
    private String nameApp;
    @OneToMany(mappedBy = "application")
    private Set<Permission> permissions = new HashSet<Permission>();

    public Application(String nameApp) {
        this.nameApp = nameApp;
    }
    
    public Application() {
    }
   

    /**
     * Get the value of permissions
     *
     * @return the value of permissions
     */
    public Set<Permission> getPermissions() {
        return permissions;
    }

    /**
     * Set the value of permissions
     *
     * @param permissions new value of permissions
     */
    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }


    public int getIdApp() {
        return idApp;
    }

    public void setIdApp(int idApp) {
        this.idApp = idApp;
    }

    public String getNameApp() {
        return nameApp;
    }

    public void setNameApp(String nameApp) {
        this.nameApp = nameApp;
    }
        
}
