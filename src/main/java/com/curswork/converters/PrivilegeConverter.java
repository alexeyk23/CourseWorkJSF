/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.curswork.converters;

import com.curswork.model.Privilege;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Kunakovsky A.
 */
@FacesConverter("privilegeConverter")
public class PrivilegeConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) 
    {
      List<Privilege> privs = (List<Privilege>) fc.getApplication().evaluateExpressionGet(fc, "#{privilegeBean.listPrivilege}", List.class);
      for (Privilege p : privs) {
            if(p.toString().equals(string))
                return p;
        }
      return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
       return o.toString();
    }

}
