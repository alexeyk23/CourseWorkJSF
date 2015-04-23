/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursework.bean;

import com.coursework.dao.RoleDAO;
import com.coursework.dao.UserDAO;
import com.coursework.model.Role;
import com.coursework.model.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *  User 
 * @author Kunakovsky A. 
 */
@ManagedBean
@SessionScoped
public class UserBean implements Serializable {
    
    private int idUser;  
    private String nameUser;  
    private Date dateOfBirthday;
    private Set<Role>  roles = new HashSet<Role>();
    private List<String> selectedRoles = new ArrayList<String>();

    public void  addUser() throws Exception
    {
        roles.clear();
        for (String id:selectedRoles) {
            roles.add(RoleDAO.getRoleById(Integer.valueOf(id)));
        }
        User  u = new User(nameUser, dateOfBirthday, roles);
        UserDAO.addUser(u);        
    }  
    public void updateUser() throws Exception
    {               
        UserDAO.updateUser(idUser, nameUser, dateOfBirthday,selectedRoles);
    }
    public void deleteUser() throws Exception
    {      
        UserDAO.deleteUser(idUser);        
    }
    public List<User> getListUser()
    {
        return UserDAO.getAllUsers();
    }
    
    public List<String> getSelectedRoles() {
        return selectedRoles;
    }

    public void setSelectedRoles(List<String> selectedRoles) {
        this.selectedRoles = selectedRoles;
    }
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
       if(idUser>0)
       {
           User u = UserDAO.getUserById(idUser);
           nameUser=u.getNameUser();
           dateOfBirthday = u.getDateOfBirhday();
           selectedRoles.clear();
           for(Role r : u.getRoles())           
               selectedRoles.add(String.valueOf(r.getIdRole()));       
       }
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
    public Date getDateOfBirthday() {
        return dateOfBirthday;
    }

    /**
     * Set Date of B-day
     * @param dateOfBirthday - date
     */
    public void setDateOfBirthday(Date dateOfBirthday) {
        this.dateOfBirthday = dateOfBirthday;
    }

    /**
     * Empty constructor
     */
    public UserBean() {
    }
}