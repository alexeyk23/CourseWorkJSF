/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.coursework.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Kunakovsky A.
 */
@Entity
@Table(name = "\"Permission\"")
public class Permission implements Serializable { 
    
    @Id
    @Column(name = "id_perm")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id_perm_gen")
    @SequenceGenerator(name = "id_perm_gen", sequenceName = "\"Permission_id_perm_seq\"")
    private int idPerm;
    
    @ManyToOne
    @JoinColumn(name = "id_app")
    private Application application;
    @ManyToOne
    @JoinColumn(name = "id_priv")
    private Privilege privelege;
    
    @ManyToMany(fetch =FetchType.LAZY, mappedBy = "permissions")
    private Set<Role> roles = new HashSet<Role>(); 

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    public Permission(Application application, Privilege privelege) {
        this.application = application;
        this.privelege = privelege;
    }
    
    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Privilege getPrivelege() {
        return privelege;
    }

    public void setPrivelege(Privilege privelege) {
        this.privelege = privelege;
    }
    
    
    /**
     * Get the value of idPerm
     *
     * @return the value of idPerm
     */
    public int getIdPerm() {
        return idPerm;
    }

    /**
     * Set the value of idPerm
     *
     * @param idPerm new value of idPerm
     */    
    public void setIdPerm(int idPerm) {
        this.idPerm = idPerm;
    }

    public Permission() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.idPerm;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Permission other = (Permission) obj;
        return this.idPerm == other.idPerm;
    }
    
}
