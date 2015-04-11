/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.curswork.restservice;

import com.curswork.dao.CommandDAO;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author admin
 */
@Path("/state")
public class ShowStateREST {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id_app}")
    public Response showResponse(@PathParam("id_app") Integer id_app){
        return Response.status(200).entity(CommandDAO.getLastCommandsForApp(id_app)).build();
    }
}
