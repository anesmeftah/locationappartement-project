/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import client.VoireReservations;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javax.swing.JFrame;
import util.RentryException;

/**
 * JavaFX Controller for GererCompte2.fxml
 * Handles account management operations
 * @author anasm
 */
public class GererCompte2Controller extends baseController implements Initializable {
    
    // FXML Injected components
    private int id;
    @FXML private Label EmailL;
    @FXML private Label Tele;
    @FXML private Label FirstNameL;
    @FXML private Label LastNameL;
    
    @FXML private TextField Email;
    @FXML private TextField Numero;
    @FXML private TextField FirstNameF;
    @FXML private TextField LastNameF;
    @FXML private PasswordField motdp;
    
    @FXML private Button ChEmail;
    @FXML private Button ChNum;
    @FXML private Button Password;
    @FXML private Button FirstNameB;
    @FXML private Button LastNameB;
    @FXML private Button checkB;
    @FXML private Button backB;
    // Database connection constants
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/locationappartement";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";
    
    
    
    /**
     * Initializes the controller class.
     * This method is automatically called after the FXML file has been loaded
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize values and load current user information
        refreshAllLabels();
    }
    @FXML
    private void onBack(){
    try{
                    controlUtil control = new controlUtil();
                    mainFrame.setSize(600,400);
                    control.set(mainFrame,"HomePage.fxml",HomeController.class);
                }catch(RentryException r){}
    }
    @FXML
    private void onCheck(){
            VoireReservations search = new VoireReservations(id);
            mainFrame.getContentPane().removeAll();
            mainFrame.getContentPane().add(search);
                
                
                
            mainFrame.setLayout(new java.awt.FlowLayout());
            mainFrame.setLocationRelativeTo(null);   // Center the window
            mainFrame.repaint();
            mainFrame.revalidate();
    }
    /**
     * Handles the email change button action
     * @param event The action event
     */
    @FXML
    private void ChEmailChangeEmail(ActionEvent event) {
        String newEmail = Email.getText();
        if (newEmail.isEmpty()) {
            showAlert("Please enter a valid email address.");
            return;
        }
        
        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt = con.createStatement();
            String cookies = GetCookie();
            String sql = "UPDATE CLIENTS SET EMAIL = '" + newEmail + "' WHERE COOKIE = '" + cookies + "'";

            int affected = stmt.executeUpdate(sql);
            if (affected > 0) {
                UpdateLabel("EMAIL", EmailL);
                showAlert(AlertType.INFORMATION, "Success", "Email updated successfully.");
            } else {
                showAlert("Failed to update email. Please try again.");
            }
            
            con.close();
        } catch (SQLException err) {
            showAlert("Database error: " + err.getMessage());
            System.out.println(err.getMessage());
        }
    }
    
    /**
     * Handles the phone number change button action
     * @param event The action event
     */
    @FXML
    private void ChNumChangeNum(ActionEvent event) {
        String newNumber = Numero.getText();
        if (newNumber.isEmpty()) {
            showAlert("Please enter a valid phone number.");
            return;
        }
        
        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt = con.createStatement();
            String cookies = GetCookie();
            String sql = "UPDATE CLIENTS SET TELEPHONE = '" + newNumber + "' WHERE COOKIE = '" + cookies + "'";

            int affected = stmt.executeUpdate(sql);
            if (affected > 0) {
                UpdateLabel("TELEPHONE", Tele);
                showAlert(AlertType.INFORMATION, "Success", "Phone number updated successfully.");
            } else {
                showAlert("Failed to update phone number. Please try again.");
            }
            
            con.close();
        } catch (SQLException err) {
            showAlert("Database error: " + err.getMessage());
            System.out.println(err.getMessage());
        }
    }
    
    /**
     * Handles the password change button action
     * @param event The action event
     */
    @FXML
    private void ChangerPassword(ActionEvent event) {
        String newPassword = motdp.getText();
        if (newPassword.isEmpty()) {
            showAlert("Please enter a valid password.");
            return;
        }
        
        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt = con.createStatement();
            String cookies = GetCookie();
            String sql = "UPDATE CLIENTS SET MDP = '" + newPassword + "' WHERE COOKIE = '" + cookies + "'";

            int affected = stmt.executeUpdate(sql);
            if (affected > 0) {
                showAlert(AlertType.INFORMATION, "Success", "Password updated successfully.");
                motdp.clear(); // Clear the password field for security
            } else {
                showAlert("Failed to update password. Please try again.");
            }
            
            con.close();
        } catch (SQLException err) {
            showAlert("Database error: " + err.getMessage());
            System.out.println(err.getMessage());
        }
    }
    
    /**
     * Handles the first name change button action
     * @param event The action event
     */
    @FXML
    private void ChangerFirstNameB(ActionEvent event) {
        String newFirstName = FirstNameF.getText();
        if (newFirstName.isEmpty()) {
            showAlert("Please enter a valid first name.");
            return;
        }
        
        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt = con.createStatement();
            String cookies = GetCookie();
            String sql = "UPDATE CLIENTS SET FIRSTNAME = '" + newFirstName + "' WHERE COOKIE = '" + cookies + "'";

            int affected = stmt.executeUpdate(sql);
            if (affected > 0) {
                UpdateLabel("FIRSTNAME", FirstNameL);
                showAlert(AlertType.INFORMATION, "Success", "First name updated successfully.");
            } else {
                showAlert("Failed to update first name. Please try again.");
            }
            
            con.close();
        } catch (SQLException err) {
            showAlert("Database error: " + err.getMessage());
            System.out.println(err.getMessage());
        }
    }
    
    /**
     * Handles the last name change button action
     * @param event The action event
     */
    @FXML
    private void ChangerLastNameB(ActionEvent event) {
        String newLastName = LastNameF.getText();
        if (newLastName.isEmpty()) {
            showAlert("Please enter a valid last name.");
            return;
        }
        
        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt = con.createStatement();
            String cookies = GetCookie();
            String sql = "UPDATE CLIENTS SET LASTNAME = '" + newLastName + "' WHERE COOKIE = '" + cookies + "'";

            int affected = stmt.executeUpdate(sql);
            if (affected > 0) {
                UpdateLabel("LASTNAME", LastNameL);
                showAlert(AlertType.INFORMATION, "Success", "Last name updated successfully.");
            } else {
                showAlert("Failed to update last name. Please try again.");
            }
            
            con.close();
        } catch (SQLException err) {
            showAlert("Database error: " + err.getMessage());
            System.out.println(err.getMessage());
        }
    }
    
    /**
     * Updates all labels with current user information from database
     */
    private void refreshAllLabels() {
        UpdateLabel("EMAIL", EmailL);
        UpdateLabel("TELEPHONE", Tele);
        UpdateLabel("FIRSTNAME", FirstNameL);
        UpdateLabel("LASTNAME", LastNameL);
        this.id = setId();
    }
    
    /**
     * Gets the user's cookie from the cookie file
     * @return The cookie value or "-1" if not found
     */
    private String GetCookie() {
        try {
            File myObj = new File("cookie.txt");
            Scanner reader = new Scanner(myObj);
            String cookies = reader.nextLine();
            reader.close();
            return cookies;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return "-1";
        }
    }
    
    /**
     * Updates a specific label with data from the database
     * @param column The database column to query
     * @param label The JavaFX label to update
     */
    private void UpdateLabel(String column, Label label) {
        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement stmt = con.createStatement();
            String cookies = GetCookie();
            
            if (cookies.equals("-1")) {
                label.setText("You Have to login");
            } else {
                String sql = "SELECT " + column + " FROM CLIENTS WHERE COOKIE = '" + cookies + "'";
                ResultSet rs = stmt.executeQuery(sql);
                
                if (rs.next()) {
                    label.setText(rs.getString(column));
                } else {
                    label.setText("You Have to login");
                }
                rs.close();
            }
            
            con.close();
        } catch (SQLException err) {
            label.setText("Error");
            System.out.println(err.getMessage());
        }
    }
    int setId(){
        int Id =-1; 
        try{
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();
            String cookies = GetCookie();
            if(cookies.equals("-1")){

                System.out.println("sad");

            }
            else{
                
                String SQL = "SELECT id FROM CLIENTS WHERE COOKIE = '" + cookies + "'";
                ResultSet rs = stmt.executeQuery(SQL);
                if(rs.next()){
                    Id = rs.getInt("id");
                }
                else{
                    System.out.println("sad");
                }
            }  
        }
        catch(SQLException err){
            System.out.println(err.getMessage());
        }
        
        return(Id);
    }
    
    /**
     * Shows an alert dialog with an error message
     * @param message The message to display
     */
    private void showAlert(String message) {
        showAlert(AlertType.ERROR, "Error", message);
    }
    
    /**
     * Shows an alert dialog with custom type, title, and message
     * @param type The alert type
     * @param title The alert title
     * @param message The message to display
     */
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
