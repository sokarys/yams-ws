


import Client.Client;
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
        
        System.out.println(service.path("User").path("T1").path("pass").accept(MediaType.APPLICATION_XML).get(String.class));
        System.out.println(service.path("List").accept(MediaType.APPLICATION_XML).get(String.class));
        
        String repUser = service.path("User").path("T1").path("pass").accept(MediaType.APPLICATION_XML).get(String.class);
        String repUser2 = service.path("User").path("T2").path("pass1").accept(MediaType.APPLICATION_XML).get(String.class);
        String repUser3 = service.path("User").path("T3/plop").accept(MediaType.APPLICATION_XML).get(String.class);
        String repList = service.path("List").accept(MediaType.APPLICATION_XML).get(String.class);
        
        System.out.println(repUser2);
        System.out.println(" --"  + repUser3);
        System.out.println("\n\n\n");
        
        
        JAXBContext mar = JAXBContext.newInstance(Client.class);
        javax.xml.bind.Unmarshaller un = mar.createUnmarshaller();
        StringBuilder xmlstr = new StringBuilder(repUser);
        System.out.println("\n\n\n");
        //String rep = service.path("/getUser/T1/pass").get(String.class).toString();
        //System.out.println(rep);
        
        JAXBElement<Client> r = (JAXBElement<Client>) un.unmarshal(new StreamSource(new StringReader(xmlstr.toString())),Client.class);
        
        mar = JAXBContext.newInstance(ArrayList.class);
        un = mar.createUnmarshaller();
        xmlstr = new StringBuilder(repUser);
        System.out.println("\n\n\n");
        //String rep = service.path("/getUser/T1/pass").get(String.class).toString();
        //System.out.println(rep);
        
        JAXBElement<ArrayList> r2 = (JAXBElement<ArrayList>) un.unmarshal(new StreamSource(new StringReader(xmlstr.toString())),ArrayList.class);
        
        System.out.println(r.getValue()); 
        System.out.println(r2.getValue().size());        
      
        

        
        /*JAXBContext jc = JAXBContext.newInstance(Client.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        JAXBElement<Client> c = (JAXBElement<Client>) unmarshaller.unmarshal(new StreamSource(new StringReader(xmlstr.toString())),Client.class);*/
        
    }
    public static URI getBaseURI(){
        return UriBuilder.fromUri("http://localhost:8080/testWS").build();
    }
    
}