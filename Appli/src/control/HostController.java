/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.event.Event;

import login.HomePage2;
import control.baseController;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import util.RentryException;
import control.controlUtil;
/**
 *
 * @author motaz
 */

public class HostController extends baseController{
    
    @FXML
    private TextField apartmentNameField;
    
    @FXML
    private TextField locationField;
    
    @FXML
    private TextField priceField;
    
    @FXML
    private TextField sizeField;
    
    @FXML
    private TextField roomsField;
    
    @FXML
    private TextArea descriptionArea;
    
    @FXML
    private Button submitButton;
    
    @FXML
    private Button cancelButton;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    /**
     * Initializes the controller
     */
    @FXML
    public void initialize() {
        // Add listeners or initial setup if needed
    }
    
    /**
     * Handles the submit button action
     * Saves the apartment listing to the database
     */
    @FXML
    public void handleSubmitAction(javafx.event.ActionEvent event) {
        try {
            String apartmentName = apartmentNameField.getText();
            String location = locationField.getText();
            double price = Double.parseDouble(priceField.getText());
            int rooms = Integer.parseInt(roomsField.getText());
            float size = Float.parseFloat(sizeField.getText());
            String description = descriptionArea.getText();
            
            // Validate input
            if (apartmentName == null || apartmentName.trim().isEmpty() ||
                location == null || location.trim().isEmpty() ||
                description == null || description.trim().isEmpty()) {
                showMessage("Error", "All fields must be filled out", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            saveToDatabase(apartmentName, location, price, rooms, size, description);
            
            // Show success message
            showMessage("Success", "Your apartment has been listed successfully and is pending approval", JOptionPane.INFORMATION_MESSAGE);
            
            // Navigate back to home page
            navigateToHome(event);
            
        } catch (NumberFormatException e) {
            showMessage("Error", "Please enter valid numeric values for price, size, and rooms", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            showMessage("Error", "An error occurred: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Handles the cancel button action
     * Returns to the homepage
     */
    @FXML
    public void handleCancelAction(javafx.event.ActionEvent event) {
        navigateToHome(event);
    }
    
    /**
     * Saves the apartment listing to the database
     */
    private void saveToDatabase(String apartmentName, String location, double price, int rooms, float size, String description) {
        try {
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();
            
            // Get next available ID
            String SQLID = "SELECT COUNT(*) FROM APPARTEMENT";
            ResultSet rs = stmt.executeQuery(SQLID);
            int ID;
            if (rs.next()) {
                ID = rs.getInt(1) + 1;
            } else {
                ID = 1; // Default to 1 if no records exist
            }
              // Insert new apartment listing
            String SQL = "INSERT INTO APPARTEMENT(ID, ADDRESS, PRIX, DESCRIP, NUMBEROFROOMS, SIZEINSQUAREMETERS, STATUT) VALUES (" + 
                ID + ", '" + location + "', " + price + ", '" + description + "', " + 
                rooms + ", " + size + ", 'pending')";
            
            int affected = stmt.executeUpdate(SQL);
            if(affected <= 0) {
                throw new SQLException("Failed to insert record");
            }
            
            con.close();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            throw new RuntimeException("Database error: " + err.getMessage());
        }
    }
      /**
     * Navigate back to the homepage
     */
    private void navigateToHome(javafx.event.ActionEvent event) {
       try{
            controlUtil control = new controlUtil();
                    control.set(mainFrame,"HomePage.fxml",HomeController.class);
        }catch(RentryException r){}
    }
    
    /**
     * Shows a message dialog
     */
    private void showMessage(String title, String message, int messageType) {
        JOptionPane optionPane = new JOptionPane(message, messageType);
        JDialog dialog = optionPane.createDialog(title);
        dialog.setAlwaysOnTop(true);
        
        if (messageType == JOptionPane.INFORMATION_MESSAGE) {
            // Auto-close success messages after 3 seconds
            Timer timer = new Timer(3000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
        
        dialog.setVisible(true);
    }
}
