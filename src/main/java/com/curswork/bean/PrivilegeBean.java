/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.curswork.bean;

import com.curswork.dao.PrivilegeDAO;
import com.curswork.model.Privilege;
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
public class PrivilegeBean implements Serializable 
{   
    
    private int idPriv;
    private String name_priv;
    /**
     * Get the value of idPriv
     *
     * @return the value of idPriv
     */
    public int getIdPriv() {
        return idPriv;
    }

    /**
     * Set the value of idPriv
     *
     * @param idPriv new value of idPriv
     */
    public void setIdPriv(int idPriv) {
        this.idPriv = idPriv;
    }

   

    /**
     * Get the value of name_priv
     *
     * @return the value of name_priv
     */
    public String getName_priv() {
        return name_priv;
    }

    /**
     * Set the value of name_priv
     *
     * @param name_priv new value of name_priv
     */
    public void setName_priv(String name_priv) {
        this.name_priv = name_priv;
    }
    public void addPrivilege()
    {
        Privilege p = new Privilege(name_priv);
        PrivilegeDAO.addPrivilege(p);
    }
    public List<Privilege> getListPrivilege() {
        return PrivilegeDAO.getAllPrivilege();
    }
    public PrivilegeBean() {
    }
}
