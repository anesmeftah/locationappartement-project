package login;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author motaz
 */
import util.GmailAuth;
import login.HomePage2;
import admin.Admin2;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Hyperlink;
import javax.swing.*;
import java.util.Random;
import java.awt.event.*;

public class loginController {
    private JFrame mainFrame;
    public void setMainFrame(JFrame F){
        this.mainFrame = F;
    }
    private void showDialoge(){
    JOptionPane optionPane = new JOptionPane(
                "You can now login with this account",
                JOptionPane.INFORMATION_MESSAGE
            );

            JDialog dialog = optionPane.createDialog("i");
            dialog.setAlwaysOnTop(true);
                Timer timer = new Timer(3000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });
            timer.setRepeats(false); // Only execute once
            timer.start();
            dialog.setVisible(true);
    }
    @FXML
    public Button Login;
    @FXML
    private Hyperlink SignUp;
    @FXML
    private Button Gmail;
    @FXML
    private TextField Username;
    @FXML
    private PasswordField Password;

    @FXML
    private void Login() {                       
        // TODO add your handling code here:
        String user = Username.getText();
        String pass = Password.getText();
        
        try{
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";
            
            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();
            
            
            
            
            String SQL = "SELECT * FROM CLIENTS WHERE EMAIL = '" + user + "' AND MDP = '" + pass + "'";
            ResultSet rs = stmt.executeQuery(SQL);
            
            if(rs.next()){
                
                int Class = rs.getInt("class");
                if(Class ==2){
                Admin2 admin = new Admin2();
                mainFrame.getContentPane().removeAll();
                mainFrame.getContentPane().add(admin);
                mainFrame.setAlwaysOnTop(true);
                mainFrame.toFront();
                mainFrame.repaint();
                mainFrame.setAlwaysOnTop(false);
                
                
                
                mainFrame.setLayout(new java.awt.FlowLayout());
                mainFrame.setLocationRelativeTo(null);   // Center the window
                mainFrame.repaint();
                mainFrame.revalidate();
                }else{
                HomePage2 home = new HomePage2();
                mainFrame.getContentPane().removeAll();
                mainFrame.getContentPane().add(home);
                
                
                
                mainFrame.setLayout(new java.awt.FlowLayout());
                mainFrame.setLocationRelativeTo(null);   // Center the window
                mainFrame.repaint();
                mainFrame.revalidate();
                }                                       
                
                
                try(FileWriter cookie = new FileWriter("cookie.txt");){
                    
                    String SQLCOOKIE = "SELECT EMAIL FROM CLIENTS WHERE EMAIL = '" + user + "' AND MDP = '" + pass + "'";
                    ResultSet rsCookie = stmt.executeQuery(SQLCOOKIE);
                    if(rsCookie.next()){
                        Random random = new Random();
                        
                        String Cookies = rsCookie.getString("EMAIL") + (random.nextInt(1000)+1);
                        cookie.write(Cookies);
                        int affected = stmt.executeUpdate("UPDATE CLIENTS SET COOKIE = '" + Cookies + "' WHERE EMAIL = '" + rsCookie.getString("EMAIL") + "'");
                        if(affected > 0){
                            System.out.println("done");
                        }
                        else{
                            System.out.println("!done");
                        }
                    }
                    

                    
                    
                }
                catch(IOException e){
                    System.out.println(e.getMessage());
                }
            }
            else{
                System.out.println("Email ou mot de passe invalide");
            }
            
            
            
        }
        catch(SQLException err){
            System.out.println(err.getMessage());
        }
        
    }
    @FXML
    private void creer() {                       
        // TODO add your handling code here:
        String user = Username.getText();
        String pass = Password.getText();
        
        try{
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";
            
            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();

            String SQLID = "SELECT MAX(ID) FROM CLIENTS";
            ResultSet rs = stmt.executeQuery(SQLID);
            
            int ID;
            if (rs.next()) {
                ID = rs.getInt(1) + 1;
                
            } else {
                ID = 1;
            }
            
            String SQL = "INSERT INTO CLIENTS(EMAIL,ID,MDP) VALUES ('" + user + "', " + ID + ", '" + pass + "')";
            int rowsAffected = stmt.executeUpdate(SQL);
            
            if(rowsAffected > 0){
                javax.swing.Timer timer = new javax.swing.Timer(300, e -> {
                showDialoge();
                });
                timer.setRepeats(false);
                timer.start();
            }
            else{
                System.out.println("Une erreur s'est produite");
            }
        }
        catch(SQLException err){
            if ("23000".equals(err.getSQLState())) {
                System.out.println("L'email est déjà utilisé. Veuillez en choisir un autre.");
            }
            else{
                System.out.println(err.getMessage());
            }
            
        }
    }
    @FXML
    private void gmailActionPerformed() {
                Random r = new Random();
                String username ="user" + r.nextInt(1000)+1;
                try{
                String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
                String uName = "root";
                String uPass = "root";

                Connection con = DriverManager.getConnection(host, uName, uPass);
                Statement stmt = con.createStatement();
                Credential credential = GmailAuth.authorize(username);
                
                Gmail gmailService = new Gmail.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), 
                GmailAuth.JSON_FACTORY, 
                credential)
                .setApplicationName(GmailAuth.APPLICATION_NAME)
                .build();
                
                Oauth2 oauth2 = new Oauth2.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                credential
                ).setApplicationName(GmailAuth.APPLICATION_NAME).build();
                
                Userinfo userInfo = oauth2.userinfo().get().execute();
                Username.setText(userInfo.getEmail());
                Password.setText(userInfo.getId());
                String SQLUN = "SELECT * FROM CLIENTS WHERE EMAIL = "+"'"+userInfo.getEmail()+"'";
                ResultSet rs = stmt.executeQuery(SQLUN);
                mainFrame.setAlwaysOnTop(true);
                mainFrame.toFront();
                mainFrame.repaint();
                mainFrame.setAlwaysOnTop(false);
                if(rs.next()){Login();}else{creer();Login();}
                String SQL = "UPDATE Clients" +"SET TOKEN ="+ credential.getRefreshToken() +"WHERE EMAIL = "+ userInfo.getEmail();
                int rowsAffected = stmt.executeUpdate(SQL);

                if(rowsAffected > 0){
                    JOptionPane.showMessageDialog(null,"Vous pouvez s'authentifier");
                }
                else{
                    System.out.println("Une erreur s'est produite");
                }
                
                
                }catch(SQLException e){
                    System.out.println(e.getMessage());
                }catch(Exception e2){
                    System.out.println(e2.getMessage());
                }
                
                
                
                
                
            
    } 
}