/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iut2.yams;

import Client.Client;
import Client.ClientSave;
import Client.Historique;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

/**
 * REST Web Service
 *
 * @author root
 */
@Path("/")
public class UserService {

    @Context
    private UriInfo context;
    
    
    private ArrayList<ClientSave> listeClient = new ArrayList<ClientSave>();

    /** Creates a new instance of UserService */
    public UserService() throws RemoteException {
	deserialiserBanque();
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
        ClientSave client = new ClientSave();
        client.setLogin(login);
        client.setPassword(password);
        for(ClientSave c : listeClient){
            if(c.equals(client)){
		    
                return new JAXBElement<ClientSave>(new QName("ClientSave"),ClientSave.class,c);
            }
        }
        return new JAXBElement<ClientSave>(new QName("ClientSave"),ClientSave.class,new ClientSave("","",null));
    }
    
    @GET
    @Path("/Test")
    @Produces(MediaType.TEXT_PLAIN)
    public String Test() {
        //LIre liste fichier renvoyer le client correspondant au nom est password sinon null
        return "helloTest";
    }
    
    @GET
    @Path("/List")
    @Produces(MediaType.APPLICATION_XML)
    //@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ArrayList<ClientSave> getListUser() {
        //LIre liste fichier renvoyer le client correspondant au nom est password sinon null
        return listeClient;
    }
    
    @POST
    @Path("/setUser")
    @Consumes(MediaType.APPLICATION_XML)
    public synchronized void setUser(JAXBElement<ClientSave> c) {
        //Sauvegarde le client en le sérialisant.
	    System.out.println(c.getValue());
	int existe = listeClient.indexOf(c.getValue());
        if(existe != -1){
            listeClient.remove(c.getValue());
            listeClient.add(c.getValue());
	    serialiserBanque();
        }else{
            listeClient.add(c.getValue());
	    serialiserBanque();
        }
    }
    
    private void serialiserBanque()
	{
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream("save.data");
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(fout);
			oos.writeObject(listeClient);
			oos.close();
		} catch (IOException ex) {
			Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
		}finally {
			try {
				fout.close();
			} catch (IOException ex) {
				Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
            
	}

	/**
	 * Cette m�thode doit permettre de d�-s�rialiser la banque
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void deserialiserBanque()
	{
		FileInputStream fin = null;
		{
			ObjectInputStream ois = null;
			try {
				File f = new File("save.data");
				if(!f.exists()){
					f.createNewFile();
				}
				System.out.println(f.getAbsolutePath());
				fin = new FileInputStream("save.data");
				ois = new ObjectInputStream(fin);
				listeClient = (ArrayList<ClientSave>) ois.readObject();
				ois.close();
				fin.close();
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IOException ex) {
				Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
         }
}
