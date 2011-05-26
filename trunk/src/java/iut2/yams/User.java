/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iut2.yams;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@XmlRootElement(name="User")
public class User {
    private String nom;
    private String password;

    public User() {
    }

    
    public User(String nom, String password) {
        this.nom = nom;
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public String getPassword() {
        return password;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
