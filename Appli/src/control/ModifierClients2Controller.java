/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import util.RentryException;

/**
 * JavaFX Controller for ModifierClients2.fxml
 * Handles client information modification
 * @author motaz
 */
public class ModifierClients2Controller extends baseController implements Initializable {
    
    private String email;
    
    @FXML
    private Label EmailL;
    
    @FXML
    private Label Tele;
    
    @FXML
    private Label id;
    
    @FXML
    private Label classL;
    
    @FXML
    private Button IdB;
    
    @FXML
    private TextField IdF;
    
    @FXML
    private ComboBox<String> ClassF;
    
    @FXML
    private Button ClassB;
    
    /**
     * Constructor without email parameter
     */
    public ModifierClients2Controller() {
        // Default constructor
    }
    
    /**
     * Constructor with email parameter
     * @param mail The email of the client to modify
     */
    public ModifierClients2Controller(String mail) {
        this.email = mail;
        System.out.println(mail + "1");
        System.out.println(email + "2");
        System.out.println("in");
    }
    
    /**
     * Set the email of the client to modify
     * @param email The email to set
     */
    public void setEmail(String email) {
        this.email = email;
        update();
    }

    /**
     * Initialize the controller
     */
    @FXML 
    private void backB(){
    try{
                    controlUtil control = new controlUtil();
                    mainFrame.setSize(800,500);
                    control.set(mainFrame,"Admin2.fxml",AdminController.class);
                }catch(RentryException r){}
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize components
        System.out.println("ModifierClients2Controller initialized");
        
        // Set up the ComboBox with class values
        ObservableList<String> classes = FXCollections.observableArrayList(
                "0", "1", "2"
        );
        ClassF.setItems(classes);
        
        // Set up field behaviors
        IdF.setOnMouseClicked(event -> {
            if (IdF.getText().isEmpty() || IdF.getText().equals("New ID")) {
                IdF.setText("");
            }
        });
        
        IdF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && (IdF.getText().isEmpty())) {
                IdF.setText("New ID");
            }
        });
        
        // Set up button actions
        IdB.setOnAction(event -> changeId());
        ClassB.setOnAction(event -> changeClass());
        
        // If email is already set, update the form
        if (email != null && !email.isEmpty()) {
            update();
        }
    }
    
    /**
     * Update the form with client information
     */
    private void update() {
        try {
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();
            String SQL = "SELECT * FROM CLIENTS WHERE EMAIL = '" + email + "'";
            ResultSet rs = stmt.executeQuery(SQL);
            
            if (rs.next()) {
                String clientEmail = rs.getString("EMAIL");
                String tel = rs.getString("TELEPHONE");
                int cl = rs.getInt("CLASS");
                int iD = rs.getInt("ID");
                
                EmailL.setText(clientEmail);
                Tele.setText(tel);
                id.setText(Integer.toString(iD));
                classL.setText(Integer.toString(cl));
                
                // Select the current class in the ComboBox
                ClassF.getSelectionModel().select(Integer.toString(cl));
            }
            
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }
      /**
     * Change the client's ID
     */
    @FXML
    public void changeId() {
        try {
            int ID = Integer.parseInt(IdF.getText());
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            
            String sql = "UPDATE CLIENTS SET ID = ? WHERE EMAIL = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);

            // Set the new email and the id of the client you want to update
            pstmt.setInt(1, ID);
            pstmt.setString(2, this.email);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Updated rows: " + rowsAffected);

            pstmt.close();
            con.close();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format: " + e.getMessage());
        }
        
        // Update the form after changing the ID
        update();
    }
      /**
     * Change the client's class
     */
    @FXML
    public void changeClass() {
        try {
            String classString = ClassF.getValue();
            int cl = 0;
            
            if (classString != null) {
                classString = classString.strip();
                System.out.println(classString);
                
                try {
                    cl = Integer.parseInt(classString);
                } catch (Exception e) {
                    System.out.println("Failed to parse class: " + e.getMessage());
                }
                
                String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
                String uName = "root";
                String uPass = "root";

                Connection con = DriverManager.getConnection(host, uName, uPass);
                
                System.out.println(cl);
                String sql = "UPDATE CLIENTS SET CLASS = ? WHERE EMAIL = ?";
                PreparedStatement pstmt = con.prepareStatement(sql);

                // Set the new class and the email of the client you want to update
                pstmt.setInt(1, cl);
                pstmt.setString(2, this.email);

                int rowsAffected = pstmt.executeUpdate();
                System.out.println("Updated rows: " + rowsAffected);

                pstmt.close();
                con.close();
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        
        // Update the form after changing the class
        update();
    }
}
