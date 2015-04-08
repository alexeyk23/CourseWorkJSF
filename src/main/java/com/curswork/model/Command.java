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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


/**
 *
 * @author admin
 */
@Entity
@Table(name = "\"Command\"")
@JsonIgnoreProperties({"id","dateMake","idApplication"}) //игнор ненужных параметров
public class Command implements Serializable {

    @Id
    @Column(name = "id_command")
    @SequenceGenerator(name = "gen_id_comm",sequenceName = "\"Command_id_command_seq\"")
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "gen_id_comm")
    private int id;   
    @Column(name = "id_user")    
    private int idUser;
    @Column(name = "id_app")
    private int idApplication;
    @Column(name = "id_priv")
    @JsonProperty(value = "id_priv")
    private int idPrivilege;
    @Column(name = "action_name")
    @JsonProperty(value = "id_oper")
    private String actionName;    
    @Column(name = "date_make")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateMake;

    public Command(int idUser, int idApplication, int idPrivilege, String actionName, Date dateMake) {
        this.idUser = idUser;
        this.idApplication = idApplication;
        this.idPrivilege = idPrivilege;
        this.actionName = actionName;
        this.dateMake = dateMake;
    }

    public Command() {
    }

    /**
     * Get the value of dateMake
     *
     * @return the value of dateMake
     */
    public Date getDateMake() {
        return dateMake;
    }

    /**
     * Set the value of dateMake
     *
     * @param dateMake new value of dateMake
     */
    public void setDateMake(Date dateMake) {
        this.dateMake = dateMake;
    }

    /**
     * Get the value of operation
     *
     * @return the value of operation
     */
    public String getOperation() {
        return actionName;
    }

    /**
     * Set the value of operation
     *
     * @param actionName new value of operation
     */
    public void setOperation(String actionName) {
        this.actionName = actionName;
    }

    /**
     * Get the value of idPrivilege
     *
     * @return the value of idPrivilege
     */
    public int getIdPrivilege() {
        return idPrivilege;
    }

    /**
     * Set the value of idPrivilege
     *
     * @param idPrivilege new value of idPrivilege
     */
    public void setIdPrivilege(int idPrivilege) {
        this.idPrivilege = idPrivilege;
    }

    /**
     * Get the value of idApplication
     *
     * @return the value of idApplication
     */
    public int getIdApplication() {
        return idApplication;
    }

    /**
     * Set the value of idApplication
     *
     * @param idApplication new value of idApplication
     */
    public void setIdApplication(int idApplication) {
        this.idApplication = idApplication;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the value of idUser
     *
     * @return the value of idUser
     */
    public int getIdUser() {
        return idUser;
    }

    /**
     * Set the value of idUser
     *
     * @param idUser new value of idUser
     */
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
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
        final Command other = (Command) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

}
