/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import util.RentryException;

/**
 *
 * @author motaz
 */
public class InfoReservationController extends baseController implements Initializable{
    private int ID=-1;
    @FXML private Label idLabel;
    @FXML private Label usernameLabel;
    @FXML private Label statusLabel;
    @FXML private Button changeFromBtn;
    @FXML private Button changeTillBtn;
    @FXML private Button changePayBtn;
    @FXML private Button changeStatusBtn;
    @FXML private Button closeBtn;
    @FXML private DatePicker dateFromPicker;
    @FXML private DatePicker dateTillPicker;
    @FXML private DatePicker payLimitPicker;
    @FXML private TextField addressField;
    @FXML private ComboBox statusCombo;  
    public InfoReservationController(){
        System.out.println("default");
    }
    public InfoReservationController(int id){
        this.ID = id;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            System.out.println("InfoReservations initialized");
        try {
            // Initialize the status combo box with possible values
            statusCombo.getItems().addAll("Accepted", "Pending", "Declined");
            
            // Set default selection to Pending
            statusCombo.setValue("Pending");
            
            // Set up date validators for the date pickers
            setupDateValidation();
            
            // If we have an ID, load the reservation data
            if (ID >= 0) {
                update();
            } else {
                System.out.println("Warning: Reservation ID is <0, no data will be loaded");
            }
        } catch (Exception e) {
            System.out.println("Error during initialization: " + e.getMessage());
            e.printStackTrace();
        }

    }
    
    
    @FXML 
    public void onChangeFrom() {
        if (dateFromPicker.getValue() == null) {
            showAlert("Please select a date before changing");
            return;
        }
        
        // Check if the new date is valid (not in the past)
        java.time.LocalDate today = java.time.LocalDate.now();
        if (dateFromPicker.getValue().isBefore(today)) {
            showAlert("Start date cannot be in the past");
            return;
        }
        
        // Check if the new date makes sense with the end date
        if (dateTillPicker.getValue() != null && 
            dateFromPicker.getValue().isAfter(dateTillPicker.getValue())) {
            showAlert("Start date cannot be after end date");
            return;
        }
        
        try {
            // Convert LocalDate to java.sql.Date for database
            java.sql.Date selectedDate = java.sql.Date.valueOf(dateFromPicker.getValue());
            
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            
            // First check if the apartment is available for the new dates
            if (!checkApartmentAvailability(con, dateFromPicker.getValue(), dateTillPicker.getValue())) {
                showAlert("The apartment is not available for these dates");
                con.close();
                return;
            }
            
            String sql = "UPDATE RESERVATION SET DATEDEBUT = ? WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(2, ID);
            pstmt.setDate(1, selectedDate); 

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Updated reservation start date, rows affected: " + rowsAffected);
            
            if (rowsAffected > 0) {
                showAlert("Reservation start date updated successfully");
            }

            pstmt.close();
            con.close();
            
            // Update the UI to reflect changes
            update();
            
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            showAlert("Error updating reservation start date: " + err.getMessage());
        }
    }
    
    /**
     * Check if the apartment is available for the specified dates
     */
    private boolean checkApartmentAvailability(Connection con, java.time.LocalDate startDate, java.time.LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return false;
        }
        
        try {
            // Get the apartment ID for this reservation
            Statement stmt = con.createStatement();
            String sql = "SELECT ID_APPARTEMENT FROM RESERVATION WHERE ID = " + ID;
            ResultSet rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                int apartmentId = rs.getInt("ID_APPARTEMENT");
                
                // Check if there are any other reservations for this apartment that overlap
                String checkSql = "SELECT COUNT(*) AS overlap FROM RESERVATION " +
                                 "WHERE ID_APPARTEMENT = ? " +
                                 "AND ID != ? " + // Exclude this reservation
                                 "AND STATUT != 'Declined' " + // Exclude declined reservations
                                 "AND ((DATEDEBUT <= ? AND DATEFIN >= ?) OR " + // New start date is during existing reservation
                                 "(DATEDEBUT <= ? AND DATEFIN >= ?) OR " +      // New end date is during existing reservation
                                 "(DATEDEBUT >= ? AND DATEFIN <= ?))";          // Existing reservation is completely within new dates
                
                PreparedStatement pstmt = con.prepareStatement(checkSql);
                pstmt.setInt(1, apartmentId);
                pstmt.setInt(2, ID);
                
                // Convert LocalDate to java.sql.Date
                java.sql.Date sqlStartDate = java.sql.Date.valueOf(startDate);
                java.sql.Date sqlEndDate = java.sql.Date.valueOf(endDate);
                
                pstmt.setDate(3, sqlEndDate);
                pstmt.setDate(4, sqlEndDate);
                pstmt.setDate(5, sqlStartDate);
                pstmt.setDate(6, sqlStartDate);
                pstmt.setDate(7, sqlStartDate);
                pstmt.setDate(8, sqlEndDate);
                
                ResultSet checkRs = pstmt.executeQuery();
                if (checkRs.next()) {
                    int overlap = checkRs.getInt("overlap");
                    return overlap == 0; // Return true if no overlaps
                }
                
                checkRs.close();
                pstmt.close();
            }
            
            rs.close();
            stmt.close();
            
        } catch (SQLException e) {
            System.out.println("Error checking apartment availability: " + e.getMessage());
            e.printStackTrace();
        }
        
        return true; // Default to true if we can't check
    }    @FXML 
    public void onChangeTill() {
        if (dateTillPicker.getValue() == null) {
            showAlert("Please select a date before changing");
            return;
        }
        
        // Check if the new date is valid (not in the past)
        java.time.LocalDate today = java.time.LocalDate.now();
        if (dateTillPicker.getValue().isBefore(today)) {
            showAlert("End date cannot be in the past");
            return;
        }
        
        // Check if the new date makes sense with the start date
        if (dateFromPicker.getValue() != null && 
            dateTillPicker.getValue().isBefore(dateFromPicker.getValue())) {
            showAlert("End date cannot be before start date");
            return;
        }
        
        try {
            // Convert LocalDate to java.sql.Date for database
            java.sql.Date selectedDate = java.sql.Date.valueOf(dateTillPicker.getValue());
            
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            
            // First check if the apartment is available for the new dates
            if (!checkApartmentAvailability(con, dateFromPicker.getValue(), dateTillPicker.getValue())) {
                showAlert("The apartment is not available for these dates");
                con.close();
                return;
            }
            
            String sql = "UPDATE RESERVATION SET DATEFIN = ? WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(2, ID);
            pstmt.setDate(1, selectedDate); 

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Updated reservation end date, rows affected: " + rowsAffected);
            
            if (rowsAffected > 0) {
                showAlert("Reservation end date updated successfully");
            }

            pstmt.close();
            con.close();
            
            // Update the UI to reflect changes
            update();
            
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            showAlert("Error updating reservation end date: " + err.getMessage());
        }
    }@FXML 
    public void onChangeStatus() {
        if (statusCombo.getValue() == null) {
            showAlert("Please select a status before changing");
            return;
        }
        
        if (ID < 0) {
            showAlert("Reservation ID is not valid");
            return;
        }
        
        try {
            String selectedStatus = statusCombo.getValue().toString();
            
            // Validate status value
            if (!selectedStatus.equals("Accepted") && 
                !selectedStatus.equals("Pending") && 
                !selectedStatus.equals("Declined")) {
                showAlert("Invalid status value: " + selectedStatus);
                return;
            }
            
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            
            String sql = "UPDATE RESERVATION SET STATUT = ? WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(2, ID);
            pstmt.setString(1, selectedStatus); 

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Updated reservation status, rows affected: " + rowsAffected);
            
            if (rowsAffected > 0) {
                showAlert("Reservation status updated successfully");
                statusLabel.setText(selectedStatus);
                
                // If status is changed to "Declined", you might want to notify the user
                if (selectedStatus.equals("Declined")) {
                    System.out.println("Reservation " + ID + " has been declined");
                    // Further notification logic could be added here
                }
            } else {
                showAlert("No changes made to reservation status");
            }

            pstmt.close();
            con.close();
            
            // Update the UI to reflect changes
            update();
            
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            showAlert("Error updating reservation status: " + err.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            showAlert("Unexpected error: " + ex.getMessage());
        }
    }    @FXML 
    public void onChangePay() {
        if (payLimitPicker.getValue() == null) {
            showAlert("Please select a payment limit date before changing");
            return;
        }
        
        // Check if the payment date makes sense
        // For payment dates, we can allow past dates for record-keeping purposes
        // But it should generally be before the start date of the reservation
        if (dateFromPicker.getValue() != null && 
            payLimitPicker.getValue().isAfter(dateFromPicker.getValue())) {
            // Just a warning, not an error - payment should ideally be before stay
            System.out.println("Warning: Payment due date is after reservation start date");
        }
        
        try {
            // Convert LocalDate to java.sql.Date for database
            java.sql.Date selectedDate = java.sql.Date.valueOf(payLimitPicker.getValue());
            
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            
            String sql = "UPDATE RESERVATION SET PAYBEFORE = ? WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(2, ID);
            pstmt.setDate(1, selectedDate); 

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Updated payment limit date, rows affected: " + rowsAffected);
            
            if (rowsAffected > 0) {
                showAlert("Payment limit date updated successfully");
                
                // If payment is due soon (within 7 days), show a warning
                java.time.LocalDate today = java.time.LocalDate.now();
                java.time.LocalDate sevenDaysLater = today.plusDays(7);
                
                if (payLimitPicker.getValue().isBefore(sevenDaysLater) && 
                    payLimitPicker.getValue().isAfter(today)) {
                    showAlert("Warning: Payment is due within the next 7 days");
                } else if (payLimitPicker.getValue().isBefore(today)) {
                    showAlert("Warning: Payment due date is in the past");
                }
            }

            pstmt.close();
            con.close();
            
            // Update the UI to reflect changes
            update();
            
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            showAlert("Error updating payment limit date: " + err.getMessage());
        }
    }
    @FXML 
    public void onClose(){
        try{
                    if (mainFrame == null) {
                        mainFrame = findMainFrame();
                        System.out.println("AdminController found mainFrame: " + mainFrame);
                    }
                    
                    controlUtil control = new controlUtil();
                    mainFrame.setSize(800,500);
                    control.set(mainFrame,"GererReservations.fxml",GererReservationsController.class);
                    
                    System.out.println("AdminController loaded GererReservationsController with mainFrame: " + mainFrame);
                }catch(RentryException r){
                    r.printStackTrace();
                }
    }    
    @FXML 
    public void onChangeAddress() {
        updateAddress();
    }    
    
    /**
     * Set up date validation for the date pickers
     */
    private void setupDateValidation() {
        // Ensure end date is after start date
        dateFromPicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && dateTillPicker.getValue() != null && 
                newVal.isAfter(dateTillPicker.getValue())) {
                // If from date is after till date, reset it
                dateFromPicker.setValue(oldVal);
                showAlert("Start date must be before end date");
            }
        });
        
        dateTillPicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && dateFromPicker.getValue() != null && 
                newVal.isBefore(dateFromPicker.getValue())) {
                // If till date is before from date, reset it
                dateTillPicker.setValue(oldVal);
                showAlert("End date must be after start date");
            }
        });
    }
    
    private void update(){
    try{
                String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
                String uName = "root";
                String uPass = "root";

                Connection con = DriverManager.getConnection(host, uName, uPass);
                Statement stmt = con.createStatement();
                Statement stmt2 = con.createStatement();
                Statement stmt3 = con.createStatement();
                
                String SQL = "SELECT * FROM RESERVATION WHERE ID ="+ID;
                ResultSet rs = stmt.executeQuery(SQL);
                
                while(rs.next()){
                    idLabel.setText(rs.getString("ID"));
                    int clientID = rs.getInt("ID_CLIENT");
                    int appID = rs.getInt("ID_APPARTEMENT");
                    String SQL2 = "SELECT * FROM CLIENTS WHERE ID = "+clientID;
                    ResultSet rs2 = stmt2.executeQuery(SQL2);
                    rs2.next();
                    String SQL3 = "SELECT * FROM APPARTEMENT WHERE ID ="+appID;
                    ResultSet rs3 = stmt3.executeQuery(SQL3);
                    rs3.next();                      // Set address
                    String address = rs3.getString("ADDRESS");
                    if (address != null) {
                        addressField.setText(address);
                    }
                    
                    // Set dates with null checks
                    java.sql.Date startDate = rs.getDate("DATEDEBUT");
                    if (startDate != null) {
                        dateFromPicker.setValue(startDate.toLocalDate());
                    }
                    
                    java.sql.Date endDate = rs.getDate("DATEFIN");
                    if (endDate != null) {
                        dateTillPicker.setValue(endDate.toLocalDate());
                    }
                    
                    // Update status values
                    String status = rs.getString("STATUT");
                    if (status != null) {
                        statusLabel.setText(status);
                        statusCombo.setValue(status);
                    }
                    
                    // Update payment limit if available
                    java.sql.Date payDate = rs.getDate("PAYBEFORE");
                    if (payDate != null) {
                        payLimitPicker.setValue(payDate.toLocalDate());
                    }

                    usernameLabel.setText(rs2.getString("EMAIL"));
                    rs2.close();stmt2.close();
                    rs3.close();stmt3.close();
                
                    

                }
                rs.close();stmt.close();
        }catch(SQLException err){
                System.out.println(err.getMessage());
            }
    }
    /**
     * Updates the apartment address
     */
    private void updateAddress() {
        if (addressField.getText() == null || addressField.getText().trim().isEmpty()) {
            showAlert("Please enter a valid address");
            return;
        }
        
        try {
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            
            // First get the apartment ID from the reservation
            Statement stmt = con.createStatement();
            String sqlQuery = "SELECT ID_APPARTEMENT FROM RESERVATION WHERE ID = " + ID;
            ResultSet rs = stmt.executeQuery(sqlQuery);
            
            if (rs.next()) {
                int apartmentId = rs.getInt("ID_APPARTEMENT");
                
                // Now update the apartment address
                String sql = "UPDATE APPARTEMENT SET ADDRESS = ? WHERE ID = ?";
                PreparedStatement pstmt = con.prepareStatement(sql);
                
                pstmt.setString(1, addressField.getText().trim());
                pstmt.setInt(2, apartmentId);

                int rowsAffected = pstmt.executeUpdate();
                System.out.println("Updated apartment address, rows affected: " + rowsAffected);
                
                if (rowsAffected > 0) {
                    showAlert("Apartment address updated successfully");
                }

                pstmt.close();
            }
            
            rs.close();
            stmt.close();
            con.close();
            
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            showAlert("Error updating apartment address: " + err.getMessage());
        }
    }
    /**
     * Show an alert message to the user
     * 
     * @param message The message to display
     */
    private void showAlert(String message) {
        javafx.application.Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.INFORMATION,
                message
            );
            alert.showAndWait();
        });
    }
    /**
     * Format a date for display
     * 
     * @param date The date to format
     * @return A formatted date string
     */
    private String formatDate(java.time.LocalDate date) {
        if (date == null) {
            return "N/A";
        }
        
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}
