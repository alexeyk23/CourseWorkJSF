/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.curswork.model;

import com.curswork.bean.*;
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
@Table(name = "\"Privilege\"")
public class Privilege 
{
    @Id
    @Column(name = "id_priv")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id_priv_gen")
    @SequenceGenerator(name = "id_priv_gen", sequenceName = "\"Privilege_id_priv_seq\"")
    private int idPriv;
    @Column(name = "name_priv")
    private String namePriv;
    @OneToMany(mappedBy = "privelege")
    private Set<Permission> permissions = new HashSet<Permission>();

    public Privilege(String namePriv) {
        this.namePriv = namePriv;
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

    public Privilege() {
    }

    public int getIdPriv() {
        return idPriv;
    }

    public void setIdPriv(int idPriv) {
        this.idPriv = idPriv;
    }

    public String getNamePriv() {
        return namePriv;
    }

    public void setNamePriv(String namePriv) {
        this.namePriv = namePriv;
    }
    
}