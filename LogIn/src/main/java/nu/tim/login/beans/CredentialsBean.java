/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.tim.login.beans;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;
import javax.ejb.Stateless;
import nu.tim.login.ConnectionFactory;
import nu.tim.login.entity.Credentials;

/**
 *
 * @author Tim
 */
@Stateless
public class CredentialsBean {
    public Credentials createCredentials(String auth){
        System.out.println(auth);
        auth = auth.substring(6).trim();
        
        byte[] bytes = Base64.getDecoder().decode(auth);
        auth = new String(bytes);
        int colon = auth.indexOf(":");
        String username = auth.substring(0,colon);
        String password = auth.substring(colon+1);
        
        return new Credentials(username, password);
        
    }
    
    public int save(Credentials credentials) {
        String hashed = BCrypt.withDefaults().hashToString(12, credentials.getPassword().toCharArray());
        try(Connection connection = ConnectionFactory.getConnection()){
            Statement stmt = connection.createStatement();
            String sql = String.format("INSERT INTO user VALUES('%s','%s')", credentials.getUserName(),hashed);
            return stmt.executeUpdate(sql);
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            return 0;
        }
    }
    public boolean check(Credentials credentials) {     
        try (Connection connection = ConnectionFactory.getConnection()) {
            Statement stmt = connection.createStatement();
            String sql = String.format("SELECT * FROM user WHERE user_name='%s'", credentials.getUserName());
            ResultSet data = stmt.executeQuery(sql);
            if(data.next()){
                String hashPass = data.getString("hashed_password");
                
                BCrypt.Result result = BCrypt.verifyer().verify(credentials.getPassword().toCharArray(), hashPass);
                return result.verified;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
            return false; 
        }
    } 
    
    public int deleteUser(Credentials credentials){
        System.out.println(credentials.getUserName());
        try(Connection connection = ConnectionFactory.getConnection()){
            Statement stmt = connection.createStatement();
            
            String sql = String.format("DELETE FROM user WHERE user_name = '%s'", credentials.getUserName());
            return stmt.executeUpdate(sql);
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            return 0;
        }
    }
    
    public int changePassword(Credentials credentials){
        String hashed = BCrypt.withDefaults().hashToString(12, credentials.getPassword().toCharArray());
        try(Connection connection = ConnectionFactory.getConnection()){
            Statement stmt = connection.createStatement();
            System.out.println(credentials.getUserName());
            String sql = String.format("UPDATE user SET hashed_password='%s' WHERE user_name = '%s'", hashed,credentials.getUserName());
            return stmt.executeUpdate(sql);
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            return 0;
        }
    }
}
