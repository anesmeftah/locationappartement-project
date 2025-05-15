/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.RentryException;

/**
 * JavaFX Controller for GererClients2.fxml
 * @author motaz
 */
public class GererClientsController extends baseController implements Initializable {
      @FXML
    private Button showList;
    
    @FXML
    private Button suppB;
    
    @FXML
    private Button backButton;
    
    @FXML
    private ScrollPane scroll;
    
    @FXML
    private VBox listContainer;
    
    // Track the currently selected client for deletion
    private Button selectedClientButton = null;
    
    // List of client emails
    private ObservableList<String> clientsList = FXCollections.observableArrayList();    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize UI
        System.out.println("GererClientsController initialized");
          // Set up the button actions
        if (showList != null) {
            showList.setOnAction(event -> showListAction());
        }
        
        if (suppB != null) {
            suppB.setOnAction(event -> suppBAction());
        }
        
        if (backButton != null) {
            backButton.setOnAction(event -> backButtonAction());
        }
        
        // Initially hide the scrollpane
        if (scroll != null) {
            scroll.setVisible(false);
            
            if (suppB != null) {
                suppB.setVisible(false);
            }
        }
        
        // Load client data
        loadClients();
    }
    
    /**
     * Load the list of clients from database
     */
    private void loadClients() {
        try {
            clientsList.clear();
            if (listContainer != null) {
                listContainer.getChildren().clear();
            }
            
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();
            String SQL = "SELECT * FROM CLIENTS";
            ResultSet rs = stmt.executeQuery(SQL);
            
            while (rs.next()) {
                String email = rs.getString("EMAIL");
                clientsList.add(email);
                  // Create a list item for each client
                Button clientButton = new Button(email);
                clientButton.setPrefWidth(380);
                clientButton.getStyleClass().add("client-item");
                
                // Add selection capability for deletion
                clientButton.setOnMouseClicked(e -> {
                    if (e.getClickCount() == 1) {
                        // Single click selects for deletion
                        selectClientForDeletion(clientButton);
                    } else if (e.getClickCount() == 2) {
                        // Double click opens modification form
                        openModifierClient(email);
                    }
                });
                
                // Add to the container
                if (listContainer != null) {
                    listContainer.getChildren().add(clientButton);
                }
            }
            
            rs.close();
            stmt.close();
            con.close();
            
        } catch (SQLException e) {
            System.err.println("Error loading clients: " + e.getMessage());
        }
    }
    
    /**
     * Show/hide client list
     */    @FXML
    public void showListAction() {
        // Toggle the visibility of the scroll pane
        boolean isVisible = scroll.isVisible();
        scroll.setVisible(!isVisible);
        
        // Toggle the button text based on the visibility status
        showList.setText(isVisible ? "Show List" : "Hide List");
        
        // Show/hide the delete button based on the scroll visibility
        suppB.setVisible(!isVisible);
    }
      /**
     * Select a client for deletion
     * @param clientButton The button representing the client
     */
    private void selectClientForDeletion(Button clientButton) {
        // Reset previous selection style
        if (selectedClientButton != null) {
            selectedClientButton.getStyleClass().remove("selected-client");
        }
        
        // Set new selection
        selectedClientButton = clientButton;
        clientButton.getStyleClass().add("selected-client");
        System.out.println("Selected client for deletion: " + clientButton.getText());
    }
    
    /**
     * Back button action - returns to Admin panel
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
     * Delete client action
     */    
    @FXML
    public void suppBAction() {
        System.out.println("Delete client action triggered");
        
        // Check if a client is selected
        if (selectedClientButton != null) {
            String email = selectedClientButton.getText();
            
            if (email != null && !email.isEmpty()) {
                try {
                    // Delete the client from the database
                    deleteClient(email);
                    
                    // Reset selection
                    selectedClientButton = null;
                    
                    // Refresh the client list
                    loadClients();
                } catch (Exception e) {
                    System.err.println("Error deleting client: " + e.getMessage());
                }
            }
        } else {
            System.out.println("Please select a client to delete first (single-click on a client)");
        }
    }
    
    /**
     * Delete a client from the database
     * @param email Email of the client to delete
     */
    private void deleteClient(String email) {
        try {
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            
            // Delete the client
            String sql = "DELETE FROM CLIENTS WHERE EMAIL = ?";
            java.sql.PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);
            
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Deleted client rows: " + rowsAffected);
            
            pstmt.close();
            con.close();
        } catch (SQLException e) {
            System.err.println("Error in database operation: " + e.getMessage());
        }
    }
    
    /**
     * Open the ModifierClients2 view for a specific client
     * @param email The email of the client to modify
     */
    private void openModifierClient(String email) {
        try {            // Two options to open the form:
            // 1. Using the controlUtil class like other controllers with email parameter
            if (mainFrame != null) {
                controlUtil control = new controlUtil();
                control.setWithEmail(mainFrame, "ModifierClients2.fxml", ModifierClients2Controller.class, email);
            } 
            // 2. Alternative: Open in a new window
            else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ModifierClients2.fxml"));
                Parent root = loader.load();
                
                ModifierClients2Controller controller = loader.getController();
                controller.setEmail(email);
                
                Stage stage = new Stage();
                stage.setTitle("Modifier Client: " + email);
                stage.setScene(new Scene(root));
                stage.show();
            }
        } catch (Exception e) {
            System.err.println("Error opening ModifierClients2 view: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
