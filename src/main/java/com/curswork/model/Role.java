/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.curswork.model;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "\"Role\"")
public class Role implements Serializable {
    @Id
    @Column(name = "id_role")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id_role_gen")
    @SequenceGenerator(name = "id_role_gen", sequenceName = "\"Role_id_role_seq\"")
    private int idRole;
    @Column(name = "name_role")
    private String nameRole;
    
    @ManyToMany(fetch =FetchType.LAZY, mappedBy = "roles")
//    @JoinTable(name = "\"User_role\"",
//            joinColumns = {@JoinColumn(name = "id_role", nullable = false, updatable = false)},
//            inverseJoinColumns = {@JoinColumn(name = "id_user", nullable = false, updatable = false)})
    private Set<User> users = new HashSet<User>(); 
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "\"Role_permission\"",
            joinColumns = {@JoinColumn(name = "id_role", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "id_perm", nullable = false, updatable = false)})
    private Set<Permission> permissions;

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

    public Set<User>  getUsers() {
        return users;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.idRole;
        return hash;
    }
    @Override
    public String toString()
    {
        return String.valueOf(idRole);
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Role other = (Role) obj;
        return this.idRole == other.idRole;
    }

    public void setUsers(Set<User>  users) {
        this.users = users;
    }
    
    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    public Role(String nameRole,Set<Permission> permissions) {
        this.nameRole = nameRole;
        this.permissions=permissions;
    }

    public Role() {
    }
    
}
