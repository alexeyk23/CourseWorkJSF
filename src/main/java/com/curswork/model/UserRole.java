/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.curswork.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Kunakovsky A.
 */
@Entity
@Table(name = "\"User_role\"")
public class UserRole implements Serializable{
    @Id
    @Column(name = "id_user_role")
    @SequenceGenerator(name = "gen_id_user_role",sequenceName ="\"User_role_id_user_role_seq\"" )
    @GeneratedValue(generator = "gen_id_user_role",strategy = GenerationType.AUTO)    
    private int idUserRole;
    @ManyToOne
    @JoinColumn(name = "id_user",referencedColumnName = "id_user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "id_role",referencedColumnName = "id_role")
    private Role role;
    @Column(name = "date_make")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date dateMake;
    
    public int getIdUserRole() {
        return idUserRole;
    }

    public void setIdUserRole(int idUserRole) {
        this.idUserRole = idUserRole;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getDate() {
        return dateMake;
    }

    public void setDate(Date date) {
        this.dateMake = date;
    }
    
}
