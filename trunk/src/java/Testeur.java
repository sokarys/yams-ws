


import Client.Client;
import Client.ClientSave;
import Client.Historique;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;


import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author root
 */
public class Testeur {
    public static void main(String args[]) throws Exception{
        
        ClientConfig config = new DefaultClientConfig();
        com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create(config);
        
        WebResource service = client.resource(getBaseURI());
        
	ClientSave sc = new ClientSave();
	sc.setHistorique(new Historique());
	sc.setLogin("tesd");
	sc.setPassword("sdfsdf");
	
	
	GenericType<JAXBElement<ClientSave>> ClientSaveType = new GenericType<JAXBElement<ClientSave>>() {};
	ClientSave cs = service.path("User").path("T1").path("pass").accept(MediaType.APPLICATION_XML).get(ClientSaveType).getValue();
	
	System.out.println(cs);
	
        service.path("setUser").accept(MediaType.APPLICATION_XML_TYPE).post(new JAXBElement<ClientSave>(new QName("ClientSave"),ClientSave.class,sc));
	 
	System.out.println(service.path("List").accept(MediaType.APPLICATION_XML).get(String.class));
        
    }
    public static URI getBaseURI(){
        return UriBuilder.fromUri("http://localhost:8080/testWS").build();
    }
    
}
