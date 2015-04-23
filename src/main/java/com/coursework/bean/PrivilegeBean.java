/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursework.bean;

import com.coursework.dao.PrivilegeDAO;
import com.coursework.model.Privilege;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author admin
 */
@ManagedBean
@SessionScoped
public class PrivilegeBean implements Serializable {

    private int idPriv;
    private String namePriv;

    public void deletePrivilege() throws Exception {
        PrivilegeDAO.deletePrivilege(idPriv);
    }

    public void addPrivilege() {
        Privilege p = new Privilege(namePriv);
        try {
            PrivilegeDAO.addPrivilege(p);
        } catch (Exception ex) {
            Logger.getLogger(PrivilegeBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Privilege> getListPrivilege() {
        List<Privilege> res = new ArrayList<Privilege>();
        try {
           res= PrivilegeDAO.getAllPrivilege();
        } catch (Exception ex) {
            Logger.getLogger(PrivilegeBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  res;
    }

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
     * Get the value of namePriv
     *
     * @return the value of namePriv
     */
    public String getName_priv() {
        return namePriv;
    }

    /**
     * Set the value of namePriv
     *
     * @param namePriv new value of namePriv
     */
    public void setName_priv(String namePriv) {
        this.namePriv = namePriv;
    }

    public PrivilegeBean() {
    }
}
