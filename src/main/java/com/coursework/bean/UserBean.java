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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *  User 
 * @author Kunakovsky A. 
 */
@ManagedBean
@ViewScoped
public class UserBean implements Serializable {
    
    private int idUser;  
    private String nameUser;  
    private Date dateOfBirthday;
    private Set<Role>  roles = new HashSet<Role>();
    private List<String> selectedRoles = new ArrayList<String>();//выбранные роли в UI

    /**
     * Добавление пользователя
     * 
     */
    public void  addUser()
    {
        try {
            roles = RoleDAO.getAllRoleById(selectedRoles);
            User u = new User(nameUser, dateOfBirthday, roles);
            UserDAO.addUser(u);
        } catch (Exception e) {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("error", new FacesMessage(FacesMessage.SEVERITY_WARN, "Ошибка соединения", null));
        }
    }  

    /**
     * Редактирование пользователя      
     */
    public void updateUser()
    {               
        try {
            UserDAO.updateUser(idUser, nameUser, dateOfBirthday,selectedRoles);
        } catch (Exception ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage("error", new FacesMessage(FacesMessage.SEVERITY_WARN, "Ошибка соединения", null));
        }
    }

    /**
     * Удаление прользователя
     * @throws Exception
     */
    public void deleteUser() throws Exception
    {      
        UserDAO.deleteUser(idUser);        
    }

    /**
     * Получить список пользователей
     * @return список пользователей
     */
    public List<User> getListUser()
    {
        List<User> res = new ArrayList<User>();
        try {
            res = UserDAO.getAllUsers();
        } catch (Exception ex) {
            Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    /**
     * Получить ID(String) выбранныех ролей
     * @return
     */
    public List<String> getSelectedRoles() {
        return selectedRoles;
    }

    /**
     * Установить ID(String) выбранныех ролей
     * @param selectedRoles
     */
    public void setSelectedRoles(List<String> selectedRoles) {
        this.selectedRoles = selectedRoles;
    }

    /**
     * Получить роли
     * @return
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     *Установить роли
     * @param roles
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    /**
     * Получить User ID
     * @return idUser
     */    
    public int getIdUser() {
        return idUser;
    }

    /**
     * Установить ID для пользователя
     * @param idUser - ID пользователя
     */
    public void setIdUser(int idUser) {
       this.idUser = idUser;
       //если id>0, то следует загрузить из базы пользователя
       if(idUser>0)
       {
           User u;
           try {
               u = UserDAO.getUserById(idUser);
               nameUser = u.getNameUser();
               dateOfBirthday = u.getDateOfBirhday();
               selectedRoles.clear();
               for (Role r : u.getRoles()) {
                   selectedRoles.add(String.valueOf(r.getIdRole()));
               }
           } catch (Exception ex) {
               Logger.getLogger(UserBean.class.getName()).log(Level.SEVERE, null, ex);
           }
                 
       }
    }

    /**
     * Получить имя пользователя
     * @return имя пользователя
     */
    public String getNameUser() {
        return nameUser;
    }

    /**
     * Установить имя пользователя
     * @param nameUser - имя пользователя
     */
    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }
    
    /**
     * Получить дату рождения
     * @return дата рождения
     */
    public Date getDateOfBirthday() {
        return dateOfBirthday;
    }

    /**
     * Установить дату рождения
     * @param dateOfBirthday - дата рождения
     */
    public void setDateOfBirthday(Date dateOfBirthday) {
        this.dateOfBirthday = dateOfBirthday;
    }

    /**
     * Пустой конструктор 
     */
    public UserBean() {
    }
}
