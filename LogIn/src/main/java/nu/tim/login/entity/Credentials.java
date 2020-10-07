/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.tim.login.entity;

/**
 *
 * @author Tim
 */
public class Credentials {
    private String userName;
    private String password;
    private String address;

    public Credentials() {
    }
    
    

    public Credentials(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Credentials(String userName, String password, String address) {
        this.userName = userName;
        this.password = password;
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
}
