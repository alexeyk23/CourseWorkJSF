/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursework.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.faces.bean.ManagedBean;
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
import javax.persistence.Temporal;

/**
 *  User 
 * @author Kunakovsky A. 
 */
@Entity
@Table(name = "\"User\"")
public class User implements Serializable {

    @Id
    @Column(name = "id_user")   
    @SequenceGenerator(name = "id_user_gen", sequenceName = "\"User_id_user_seq\"")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_user_gen")
    private int idUser;
    @Column(name = "name_user")
    private String nameUser;
    @Column(name = "date_of_birthday")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOfBirthday;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "\"User_role\"",
            joinColumns = {@JoinColumn(name = "id_user", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "id_role", nullable = false, updatable = false)})
    private Set<Role> roles = new HashSet<Role>();

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    public Date getDateOfBirthday() {
        return dateOfBirthday;
    }

    public void setDateOfBirthday(Date dateOfBirthday) {
        this.dateOfBirthday = dateOfBirthday;
    }

    /**
     * Get User ID
     * @return idUser
     */
    public int getIdUser() {
        return idUser;
    }

    /**
     * Set id for user
     * @param idUser - id of user
     */
    public void setIdUser(int idUser) {
       this.idUser = idUser;
    }

    /**
     * Get name of user
     * @return name user
     */
    public String getNameUser() {
        return nameUser;
    }

    /**
     * Set user name
     * @param nameUser - name of user
     */
    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }
    
    /**
     * Get date of b-day
     * @return dateOfBirthday
     */
    public Date getDateOfBirhday() {
        return dateOfBirthday;
    }

    /**
     * Set Date of B-day
     * @param dateOfBirthday - date
     */
    public void setDateOfBirhday(Date dateOfBirthday) {
        this.dateOfBirthday = dateOfBirthday;
    }

    /**
     * Empty constructor
     */
    public User() {
    }

    public User(String nameUser, Date dateOfBirthday, Set<Role> roles) {       
        this.nameUser = nameUser;
        this.dateOfBirthday = dateOfBirthday;
        this.roles = roles;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.idUser;
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
        final User other = (User) obj;
        return this.idUser == other.idUser;
    }
    
    
}