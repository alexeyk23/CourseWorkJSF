/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursework.restservice;

import com.coursework.dao.CommandDAO;
import com.coursework.model.Command;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author admin
 */
@Path("/state")
public class ShowStateREST {
    @GET
    @Produces("application/json")
    @Path("/{id_app}/{year}/{month}/{day}/{h}/{m}")
    public Response showResponse(
            @PathParam("id_app") Integer id_app,
            @PathParam("year") Integer year,
            @PathParam("month") Integer month,
            @PathParam("day") Integer day,
            @PathParam("h") Integer hour,
            @PathParam("m") Integer min){
        Calendar calendar = new GregorianCalendar(year, month-1, day, hour, min);
        List<Command> res=null;
        try {
            res = CommandDAO.getLastCommandsForApp(id_app, calendar.getTime());
        } catch (Exception ex) {
            Logger.getLogger(ShowStateREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(200).entity(res).build();
    }
}
