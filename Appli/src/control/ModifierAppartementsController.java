package control;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * Controller for modifying apartment information
 * Based on ModifierAppartements.java Swing implementation
 * @author motaz
 */
public class ModifierAppartementsController extends baseController implements Initializable {
    @FXML private Label idLabel;
    @FXML private TextField addressField;
    @FXML private TextField roomsField;
    @FXML private TextField sizeField;
    @FXML private TextField priceField;
    @FXML private Label statusLabel;
    @FXML private ComboBox<String> statusCombo;
    @FXML private TextArea descriptionField;
    @FXML private Button addressBtn;
    @FXML private Button roomsBtn;
    @FXML private Button sizeBtn;
    @FXML private Button priceBtn;
    @FXML private Button descriptionBtn;
    @FXML private Button statusBtn;
    @FXML private Button saveBtn;
    @FXML private Button backBtn;

    private int appartementId = -1;
    
    /**
     * Default constructor
     */
    public ModifierAppartementsController() {
        // Default constructor required for JavaFX
    }
    
    /**
     * Constructor with apartment ID parameter
     * @param id the ID of the apartment to modify
     */
    public ModifierAppartementsController(int id) {
        this.appartementId = id;
    }

    /**
     * Initialize the controller
     */    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize status options
        if (statusCombo != null) {
            statusCombo.getItems().addAll("Available", "Reserved");
        }
        
        // Set up button actions
        if (backBtn != null) {
            backBtn.setOnAction(event -> goBack());
        }
        
        if (addressBtn != null) {
            addressBtn.setOnAction(event -> updateAddress());
        }
        
        if (roomsBtn != null) {
            roomsBtn.setOnAction(event -> updateRooms());
        }
        
        if (sizeBtn != null) {
            sizeBtn.setOnAction(event -> updateSize());
        }
        
        if (priceBtn != null) {
            priceBtn.setOnAction(event -> updatePrice());
        }
        
        if (descriptionBtn != null) {
            descriptionBtn.setOnAction(event -> updateDescription());
        }
        
        if (statusBtn != null) {
            statusBtn.setOnAction(event -> updateStatus());
        }
        
        if (saveBtn != null) {
            saveBtn.setOnAction(event -> saveAll());
        }
        
        // Load apartment data if ID is already set (from constructor)
        if (appartementId > 0) {
            // Use Platform.runLater to ensure UI components are fully initialized
            Platform.runLater(() -> loadAppartement());
        }
    }

    /**
     * Set the apartment ID and load its data
     */
    public void setAppartementId(int id) {
        this.appartementId = id;
        loadAppartement();
    }    /**
     * Load apartment data from database
     */
    private void loadAppartement() {
        System.out.println(appartementId);
        if (appartementId <= 0) {
            System.out.println("Cannot load apartment: Invalid ID " + appartementId);
            return;
        }
        
        try {
            // Verify UI components are initialized
            if (idLabel == null || addressField == null || roomsField == null || 
                sizeField == null || priceField == null || statusLabel == null || 
                statusCombo == null) {
                System.out.println("UI components not initialized yet. Trying again later...");
                // Try again after a short delay
                Platform.runLater(() -> {
                    try {
                        Thread.sleep(1);
                        loadAppartement();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
                return;
            }
            
            // Connect to database and load data
            try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/locationappartement", "root", "root")) {
                String sql = "SELECT * FROM APPARTEMENT WHERE ID = ?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, appartementId);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    System.out.println("In DB");
                    idLabel.setText(String.valueOf(appartementId));
                    addressField.setText(rs.getString("ADDRESS"));
                    roomsField.setText(String.valueOf(rs.getInt("NUMBEROFROOMS")));
                    sizeField.setText(String.valueOf(rs.getInt("SIZEINSQUAREMETERS")));
                    priceField.setText(String.valueOf(rs.getInt("PRIX")));
                    
                    // Check for description field in either UI or database
                    if (descriptionField != null) {
                        String description = rs.getString("DESCRIP");
                        if (description != null) {
                            descriptionField.setText(description);
                        }
                    }
                    statusLabel.setText(rs.getString("STATUT"));
                    statusCombo.setValue(rs.getString("STATUT"));
                } else {
                    showAlert("Error", "Apartment Not Found", 
                        "Could not find apartment with ID: " + appartementId);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading apartment: " + e.getMessage());
            e.printStackTrace();
            showAlert("Error", "Database Error", 
                    "Could not load apartment data: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error loading apartment: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Go back to apartment management screen
     */
    private void goBack() {
        try {
            if (mainFrame != null) {
                controlUtil control = new controlUtil();
                control.set(mainFrame, "GererAppartements.fxml", GererAppartementsController.class);
            }
        } catch (Exception e) {
            System.err.println("Error navigating back: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Update apartment address
     */
    @FXML
    public void updateAddress() {
        if (appartementId > 0 && addressField != null) {
            String address = addressField.getText();
            updateField("ADDRESS", address);
        }
    }
    
    /**
     * Update number of rooms
     */
    @FXML
    public void updateRooms() {
        if (appartementId > 0 && roomsField != null) {
            try {
                int rooms = Integer.parseInt(roomsField.getText());
                updateField("NUMBEROFROOMS", rooms);
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Invalid Number", 
                        "Please enter a valid integer for number of rooms.");
            }
        }
    }
    
    /**
     * Update apartment size
     */
    @FXML
    public void updateSize() {
        if (appartementId > 0 && sizeField != null) {
            try {
                int size = Integer.parseInt(sizeField.getText());
                updateField("SIZEINSQUAREMETERS", size);
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Invalid Number", 
                        "Please enter a valid integer for size in square meters.");
            }
        }
    }
    
    /**
     * Update apartment price
     */
    @FXML
    public void updatePrice() {
        if (appartementId > 0 && priceField != null) {
            try {
                int price = Integer.parseInt(priceField.getText());
                updateField("PRIX", price);
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Invalid Price", 
                        "Please enter a valid integer for price.");
            }
        }
    }
    
    /**
     * Update apartment description
     */
    @FXML
    public void updateDescription() {
        if (appartementId > 0 && descriptionField != null) {
            String description = descriptionField.getText();
            updateField("DESCRIP", description);
        }
    }
    
    /**
     * Update apartment status
     */
    @FXML
    public void updateStatus() {
        if (appartementId > 0 && statusCombo != null) {
            String status = statusCombo.getValue();
            if (status != null && !status.isEmpty()) {
                updateField("STATUT", status);
            }
        }
    }
    
    /**
     * Save all apartment details at once
     */
    @FXML
    public void saveAll() {
        try {
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            
            String sql = "UPDATE APPARTEMENT SET ADDRESS = ?, NUMBEROFROOMS = ?, " +
                        "SIZEINSQUAREMETERS = ?, PRIX = ?, DESCRIP = ?, STATUT = ? WHERE ID = ?";
            
            PreparedStatement pstmt = con.prepareStatement(sql);
            
            // Set parameters
            pstmt.setString(1, addressField.getText());
            pstmt.setInt(2, Integer.parseInt(roomsField.getText()));
            pstmt.setInt(3, Integer.parseInt(sizeField.getText()));
            pstmt.setInt(4, Integer.parseInt(priceField.getText()));
            pstmt.setString(5, descriptionField != null ? descriptionField.getText() : "");
            pstmt.setString(6, statusCombo.getValue());
            pstmt.setInt(7, appartementId);
            
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Updated apartment, rows affected: " + rowsAffected);
            
            pstmt.close();
            con.close();
            
            showAlert("Success", "Apartment Updated", 
                     "All apartment details have been updated successfully.");
            
        } catch (SQLException e) {
            System.err.println("Error updating apartment: " + e.getMessage());
            showAlert("Error", "Database Error", 
                     "An error occurred while updating apartment details: " + e.getMessage());
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Invalid Number", 
                     "Please make sure all numeric fields contain valid numbers.");
        }
    }
    
    /**
     * Update a specific field in the apartment record
     */
    private void updateField(String fieldName, Object value) {
        try {
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            
            String sql = "UPDATE APPARTEMENT SET " + fieldName + " = ? WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            
            // Set the appropriate parameter type
            if (value instanceof String) {
                pstmt.setString(1, (String)value);
            } else if (value instanceof Integer) {
                pstmt.setInt(1, (Integer)value);
            } else {
                pstmt.setObject(1, value);
            }
            
            pstmt.setInt(2, appartementId);
            
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Updated " + fieldName + ", rows affected: " + rowsAffected);
            
            pstmt.close();
            con.close();
            
            showAlert("Success", "Field Updated", 
                     "The " + fieldName + " field has been updated successfully.");
            
            // Refresh data
            loadAppartement();
            
        } catch (SQLException e) {
            System.err.println("Error updating field: " + e.getMessage());
            showAlert("Error", "Database Error", 
                     "An error occurred while updating " + fieldName + ": " + e.getMessage());
        }
    }
    
    /**
     * Display an alert message
     */
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    /**
     * Handle update address button click
     */
    @FXML
    private void onUpdateAddress() {
        if (addressField != null) {
            updateAddress();
        }
    }

    /**
     * Handle update rooms button click
     */
    @FXML
    private void onUpdateRooms() {
        if (roomsField != null) {
            updateRooms();
        }
    }

    /**
     * Handle update size button click
     */
    @FXML
    private void onUpdateSize() {
        if (sizeField != null) {
            updateSize();
        }
    }

    /**
     * Handle update price button click
     */
    @FXML
    private void onUpdatePrice() {
        if (priceField != null) {
            updatePrice();
        }
    }

    /**
     * Handle update status button click
     */
    @FXML
    private void onUpdateStatus() {
        if (statusCombo != null) {
            updateStatus();
        }
    }

    /**
     * Handle save button click
     */
    @FXML
    private void onSave() {
        saveAll();
    }

    /**
     * Handle delete button click
     */
    @FXML
    private void onDelete() {
        try {
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            
            String sql = "DELETE FROM APPARTEMENT WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, appartementId);
            
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Deleted apartment, rows affected: " + rowsAffected);
            
            pstmt.close();
            con.close();
            
            showAlert("Success", "Apartment Deleted", 
                     "The apartment has been deleted successfully.");
                     
            // Navigate back to apartment management screen
            goBack();
            
        } catch (SQLException e) {
            System.err.println("Error deleting apartment: " + e.getMessage());
            showAlert("Error", "Database Error", 
                     "An error occurred while deleting the apartment: " + e.getMessage());
        }
    }

    /**
     * Handle close/back button click
     */
    @FXML
    private void onClose() {
        goBack();
    }
}
