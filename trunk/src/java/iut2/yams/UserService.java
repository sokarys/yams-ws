/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iut2.yams;

import Client.Client;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

/**
 * REST Web Service
 *
 * @author root
 */
@Path("/")
public class UserService {

    @Context
    private UriInfo context;
    
    private ArrayList<Client> listeClient = new ArrayList<Client>();

    /** Creates a new instance of UserService */
    public UserService() throws RemoteException {
        listeClient.add(new Client("T1","pass"));
        listeClient.add(new Client("T2","pass2"));
    }

    /**
     * Retrieves representation of an instance of iut2.yams.UserService
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/User/{login}/{password}")
    @Produces(MediaType.APPLICATION_XML)
    public Object getUser(@PathParam("login") String login,@PathParam("password") String password) throws RemoteException {
        //LIre liste fichier renvoyer le client correspondant au nom est password sinon null
        Client client = new Client(login, password);
        for(Client c : listeClient){
            if(c.equals(client)){
                return c;
            }
        }
        return "";
    }
    
    @GET
    @Path("/Test")
    @Produces(MediaType.TEXT_PLAIN)
    public String Test() {
        //LIre liste fichier renvoyer le client correspondant au nom est password sinon null
        return "helloTest";
    }
    
    @GET
    @Path("List")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ArrayList<Client> getListUser() {
        //LIre liste fichier renvoyer le client correspondant au nom est password sinon null
        return listeClient;
    }
    
    @PUT
    @Path("setUser")
    @Consumes(MediaType.APPLICATION_JSON)
    public void setUser(JAXBElement<Client> c) {
        //Sauvegarde le client en le sérialisant.
        int existe = listeClient.indexOf(c.getValue());
        if(existe != -1){
            listeClient.remove(c.getValue());
            listeClient.add(c.getValue());
        }else{
            listeClient.add(c.getValue());
        }
    }
    
}
