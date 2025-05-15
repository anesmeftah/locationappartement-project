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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.RentryException;

/**
 * Controller for managing apartments in the system
 * @author motaz
 */
public class GererAppartementsController extends baseController implements Initializable {
    
    @FXML
    private Button showList;
    
    @FXML
    private Button supprimerClient;
    
    @FXML
    private ScrollPane scroll;
    
    @FXML
    private VBox listContainer;
    
    // Back button for navigation
    @FXML 
    private Button backButton;
    
    // Currently selected apartment for deletion
    private Button selectedApartmentButton = null;
    
    // List of apartment IDs
    private ObservableList<Integer> apartmentIds = FXCollections.observableArrayList();
      /**
     * Back button action handler - returns to Admin panel
     */
    @FXML
    public void backButtonAction() {
        try {
            if (mainFrame != null) {
                controlUtil control = new controlUtil();
                control.set(mainFrame, "Admin2.fxml", AdminController.class);
            }
        } catch (Exception e) {
            System.err.println("Error returning to Admin panel: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Toggle the visibility of the apartment list
     */
    @FXML
    public void showListAction() {
        // Toggle the visibility of the scroll pane
        boolean isVisible = scroll.isVisible();
        scroll.setVisible(!isVisible);
        
        // Toggle the button text based on the visibility status
        showList.setText(isVisible ? "Show List" : "Hide List");
        
        // Show/hide the delete button based on the scroll visibility
        supprimerClient.setVisible(!isVisible);
        
        // Load the apartments if showing the list
        if (!isVisible) {
            loadApartments();
        }
    }
      
    /**
     * Delete selected apartment action
     */
    @FXML
    public void supprimerClientAction() {
        System.out.println("Delete apartment action triggered");
        
        // Check if an apartment is selected for deletion
        if (selectedApartmentButton != null) {
            try {
                // Get the apartment ID
                String buttonText = selectedApartmentButton.getText();
                int apartmentId = extractApartmentId(buttonText);
                
                if (apartmentId > 0) {
                    // Delete the apartment
                    deleteApartment(apartmentId);
                    
                    // Reset selection
                    selectedApartmentButton = null;
                    
                    // Refresh the apartment list
                    loadApartments();
                }
            } catch (Exception e) {
                System.err.println("Error deleting apartment: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert("No Selection", "No apartment selected", 
                     "Please select an apartment to delete first (click on an apartment from the list).");
        }
    }
    
    /**
     * Initialize the controller
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize components
        System.out.println("GererAppartementsController initialized");
        
        // Make sure initially the list is hidden
        if (scroll != null) {
            scroll.setVisible(false);
        }
        
        if (supprimerClient != null) {
            supprimerClient.setVisible(false);
        }
    }
    
    /**
     * Load apartments from database and display in list
     */
    private void loadApartments() {
        try {
            // Clear existing items
            apartmentIds.clear();
            if (listContainer != null) {
                listContainer.getChildren().clear();
            }
            
            // Database connection
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();
            String SQL = "SELECT * FROM APPARTEMENT";
            ResultSet rs = stmt.executeQuery(SQL);
            
            while (rs.next()) {
                int id = rs.getInt("ID");
                String address = rs.getString("ADDRESS");
                int rooms = rs.getInt("NUMBEROFROOMS");
                int price = rs.getInt("PRIX");
                String status = rs.getString("STATUT");
                
                apartmentIds.add(id);
                
                // Create a list item for each apartment
                HBox apartmentItemBox = createApartmentItemBox(id, address, rooms, price, status);
                
                // Add to the list container
                if (listContainer != null) {
                    listContainer.getChildren().add(apartmentItemBox);
                }
            }
            
            // Close resources
            rs.close();
            stmt.close();
            con.close();
            
        } catch (SQLException e) {
            System.err.println("Error loading apartments: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Create a HBox containing apartment information for display in the list
     */
    private HBox createApartmentItemBox(int id, String address, int rooms, int price, String status) {
        HBox hbox = new HBox(10); // 10px spacing
        hbox.getStyleClass().add("apartment-item");
        
        // Apartment ID and address
        Button apartmentBtn = new Button("ID: " + id + " - " + address + 
                                        " (" + rooms + " rooms, " + price + "€, " + status + ")");
        apartmentBtn.setPrefWidth(380);
        apartmentBtn.getStyleClass().add("apartment-button");
        
        // Set selection behavior
        apartmentBtn.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) {
                // Single click selects for deletion
                selectApartmentForDeletion(apartmentBtn);
            } else if (e.getClickCount() == 2) {
                // Double click opens modification form
                openModifierAppartement(id);
            }
        });
        
        // Add button to HBox with growth priority
        HBox.setHgrow(apartmentBtn, Priority.ALWAYS);
        hbox.getChildren().add(apartmentBtn);
        
        return hbox;
    }
    
    /**
     * Select an apartment for deletion
     */
    private void selectApartmentForDeletion(Button apartmentBtn) {
        // Reset previous selection style
        if (selectedApartmentButton != null) {
            selectedApartmentButton.getStyleClass().remove("selected-apartment");
        }
        
        // Set new selection
        selectedApartmentButton = apartmentBtn;
        apartmentBtn.getStyleClass().add("selected-apartment");
        
        // Extract ID for logging
        int id = extractApartmentId(apartmentBtn.getText());
        System.out.println("Selected apartment for deletion: ID = " + id);
    }
    
    /**
     * Extract apartment ID from button text
     */
    private int extractApartmentId(String buttonText) {
        try {
            // Extract ID from format "ID: X - ..."
            String idStr = buttonText.substring(4, buttonText.indexOf(" -"));
            return Integer.parseInt(idStr);
        } catch (Exception e) {
            System.err.println("Error extracting apartment ID: " + e.getMessage());
            return -1;
        }
    }
    
    /**
     * Delete an apartment from the database
     */
    private void deleteApartment(int apartmentId) {
        try {
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            
            // Check for related reservations first
            String checkSql = "SELECT COUNT(*) FROM RESERVATION WHERE APPARTEMENT_ID = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkSql);
            checkStmt.setInt(1, apartmentId);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                showAlert("Cannot Delete", "Apartment has reservations",
                         "This apartment has active reservations and cannot be deleted.");
                rs.close();
                checkStmt.close();
                con.close();
                return;
            }
            
            // Delete the apartment
            String sql = "DELETE FROM APPARTEMENT WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, apartmentId);
            
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Deleted apartment: ID = " + apartmentId + ", Rows affected: " + rowsAffected);
            
            pstmt.close();
            con.close();
            
            showAlert("Success", "Apartment Deleted", 
                     "The apartment (ID: " + apartmentId + ") was successfully deleted.");
            
        } catch (SQLException e) {
            System.err.println("Error deleting apartment: " + e.getMessage());
            showAlert("Error", "Database Error", 
                     "An error occurred while deleting the apartment: " + e.getMessage());
        }
    }
    
    /**
     * Open modifier form for an apartment
     */
    private void openModifierAppartement(int apartmentId) {
        try {
            // Two options to open the form:
            // 1. Using controlUtil class with the mainFrame
            if (mainFrame != null) {
                controlUtil control = new controlUtil();
                control.set(mainFrame, "ModifierAppartements.fxml", ModifierAppartementsController.class, apartmentId);
            } 
            // 2. Alternative: Open in a new window
            else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ModifierAppartements.fxml"));
                Parent root = loader.load();
                
                ModifierAppartementsController controller = loader.getController();
                controller.setAppartementId(apartmentId);
                
                Stage stage = new Stage();
                stage.setTitle("Modifier Appartement: " + apartmentId);
                stage.setScene(new Scene(root));
                stage.show();
            }
        } catch (Exception e) {
            System.err.println("Error opening ModifierAppartements form: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Display an alert message
     */
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
