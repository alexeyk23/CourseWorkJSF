/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.curswork.restservice;

import com.curswork.dao.CommandDAO;
import com.curswork.model.Command;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.richfaces.json.JSONArray;

/**
 *
 * @author admin
 */
@Path("/state")
public class ShowStateREST {

    @GET
    @Produces("application/json")
    public Response showResponse() throws JSONException {      
        List<Command> list = CommandDAO.getAllCommand();
      
        return Response.status(200).entity(list).build();
    }
}
