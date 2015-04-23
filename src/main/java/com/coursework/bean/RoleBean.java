/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursework.bean;

import com.coursework.dao.PermissionDAO;
import com.coursework.dao.RoleDAO;
import com.coursework.model.Permission;
import com.coursework.model.Role;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author admin
 */
@ManagedBean
@SessionScoped
public class RoleBean implements Serializable {

    private int idRole;
    private String nameRole; 
    private List<String> selectedPermission = new ArrayList<String>();
  
    public void addRole() {
        
        Set<Permission> perm = new HashSet<Permission>();
        try {
            for (String permName : selectedPermission) {
                perm.add(PermissionDAO.getPermissionById(Integer.valueOf(permName)));
            }
            Role r = new Role(nameRole, perm);
            RoleDAO.addRole(r);
        } catch (Exception e) {
        }
       
    }
    public void updateRole() throws Exception
    {
        RoleDAO.updateRole(idRole, nameRole, selectedPermission);        
    }
    
    public void deleteRole() throws Exception
    {
        RoleDAO.deleteRole(idRole);        
    }
    public List<Role> getAllRole() {
        return RoleDAO.getAllRole();
    }

    public List<String> getSelectedPermission() {
        return selectedPermission;
    }

    public void setSelectedPermission(List<String> selectedPermission) {
        this.selectedPermission = selectedPermission;
    }

    /**
     * Get the value of idRole
     *
     * @return the value of idRole
     */
    public int getIdRole() {
        return idRole;
    }

    /**
     * Set the value of idRole
     *
     * @param idRole new value of idRole
     */
    public void setIdRole(int idRole) {
        this.idRole = idRole;
        if(idRole>0)
        {
            Role r =RoleDAO.getRoleById(idRole);
            nameRole=r.getNameRole();
            selectedPermission.clear();
            for (Permission perm : r.getPermissions()) {
                selectedPermission.add(String.valueOf(perm.getIdPerm()));
            }
        }
    }

    /**
     * Get the value of nameRole
     *
     * @return the value of nameRole
     */
    public String getNameRole() {
        return nameRole;
    }

    /**
     * Set the value of nameRole
     *
     * @param nameRole new value of nameRole
     */
    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    public RoleBean() {
    }

}
