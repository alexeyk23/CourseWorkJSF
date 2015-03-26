/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.curswork.bean;

import com.curswork.dao.RoleDAO;
import com.curswork.model.Role;
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
public class RoleBean implements Serializable {

    private int idRole;
    private String nameRole; 
    /**
     * Get the value of idRole
     *
     * @return the value of idRole
     */
    public int getIdRole() {
        return idRole;
    }

    /**
     * Set the value of idRole
     *
     * @param idRole new value of idRole
     */
    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    /**
     * Get the value of nameRole
     *
     * @return the value of nameRole
     */
    public String getNameRole() {
        return nameRole;
    }

    /**
     * Set the value of nameRole
     *
     * @param nameRole new value of nameRole
     */
    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    public void addRole() {
        Role r = new Role(nameRole);
        RoleDAO.addRole(r);
    }

    public List<Role> getAllRole() {
        return RoleDAO.getAllRole();
    }

    public RoleBean() {
    }

}
