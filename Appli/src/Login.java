/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author anasm
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Login {
    public static void main(String[] args){
        try{
            String host = "jdbc:derby://localhost:1527/LocAPP";
            String uName = "Root";
            String uPass = "root";
            Connection con = DriverManager.getConnection(host, uName, uPass);
            
        }
        catch(SQLException err){
            System.out.println(err.getMessage());
        }
    }
}
