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
    @ManyToMany(fetch =FetchType.LAZY, mappedBy = "roles",cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<User>(); 
     
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

    public Role(String nameRole) {
        this.nameRole = nameRole;
    }

    public Role() {
    }
    
}
