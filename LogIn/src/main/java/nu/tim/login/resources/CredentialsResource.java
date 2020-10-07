/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.tim.login.resources;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nu.tim.login.beans.CredentialsBean;
import nu.tim.login.entity.Credentials;

/**
 *
 * @author Tim
 */
@Path("user")
public class CredentialsResource {
    @EJB
    CredentialsBean credentialsBean;
    
    @GET
    public Response login(@HeaderParam("Authorization") String authorization){
        Credentials credentials = credentialsBean.createCredentials(authorization);
        if(credentialsBean.check(credentials)){
            return Response.ok("Welcome").build();
        }else{
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
    @POST
    public Response createUser(@HeaderParam("Authorization") String authorization){
        Credentials credentials = credentialsBean.createCredentials(authorization);
        if(credentialsBean.save(credentials) == 1){
            return Response.ok("AND WHAT DO YOU GET????").build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @PUT
    public Response changePassword(@HeaderParam("Authorization") String auth){
        Credentials credentials = credentialsBean.createCredentials(auth);
        if(credentialsBean.changePassword(credentials) == 1){
            return Response.ok("AND WHAT DO YOU GET????").build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
     @PUT
     @Consumes(MediaType.TEXT_PLAIN)
    public Response changePassword(String password,@HeaderParam("Authorization") String auth){
        Credentials credentials = credentialsBean.createCredentials(auth);
        credentials.setPassword(password);
        if(credentialsBean.changePassword(credentials) == 1){
            return Response.ok("AND WHAT DO YOU GET????").build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @DELETE
    public Response deleteUser(@HeaderParam("Authorization") String auth){
        Credentials credentials = credentialsBean.createCredentials(auth);
        System.out.println(credentials.getUserName());
        if(credentialsBean.deleteUser(credentials) == 1){
            return Response.ok("DELETED!").build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
