/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.curswork.converters;

import com.curswork.dao.RoleDAO;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Kunakovsky A.
 */
@FacesConverter("roleConverter")
public class RoleConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) 
    {
       return RoleDAO.getRoleById(Integer.valueOf(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
       return o.toString();
    }

}
