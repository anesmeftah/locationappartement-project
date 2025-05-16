package control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RechercheController extends baseController {
    // Apartment class to store search results
    public static class Apartment {
        private int id;
        private String address;
        private double price;
        private int numberOfPersons;
        private String description;
        private String imageUrl;
        
        public Apartment(int id, String address, double price, int numberOfPersons, String description, String imageUrl) {
            this.id = id;
            this.address = address;
            this.price = price;
            this.numberOfPersons = numberOfPersons;
            this.description = description;
            this.imageUrl = imageUrl;
        }
        
        public int getId() { return id; }
        public String getAddress() { return address; }
        public double getPrice() { return price; }
        public int getNumberOfPersons() { return numberOfPersons; }
        public String getDescription() { return description; }
        public String getImageUrl() { return imageUrl; }
    }

    // List to store found apartments
    private List<Apartment> foundApartments = new ArrayList<>();
    private LocalDate searchStartDate;
    private LocalDate searchEndDate;
    
    @FXML
    private TextField locField;
    @FXML
    private TextField locField1;
    @FXML
    private TextField dayIn;
    @FXML
    private TextField monthIn;
    @FXML
    private TextField yearIn;
    @FXML
    private TextField dayOut;
    @FXML
    private TextField monthOut;
    @FXML
    private TextField yearOut;
    @FXML
    private Button searchButton;
    @FXML
    private Label errorMsg;

    @FXML
    public void handleSearch() {
        // Clear previous error
        errorMsg.setText("");

        String location = locField.getText().trim();
        String personsStr = locField1.getText().trim();
        String checkIn = yearIn.getText().trim() + "-" + monthIn.getText().trim() + "-" + dayIn.getText().trim();
        String checkOut = yearOut.getText().trim() + "-" + monthOut.getText().trim() + "-" + dayOut.getText().trim();

        // Validate dates
        LocalDate dateDebut;
        LocalDate dateFin;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-M-d");
        try {
            dateDebut = LocalDate.parse(checkIn, dtf);
            dateFin = LocalDate.parse(checkOut, dtf);
            if (dateDebut.isAfter(dateFin)) {
                errorMsg.setText("Check-out date must be after check-in date");
                return;
            }
        } catch (DateTimeParseException e) {
            errorMsg.setText("Invalid date format. Please use DD, MM, YYYY");
            return;
        }

        // Validate number of persons
        int persons;
        try {
            persons = Integer.parseInt(personsStr);
            if (persons <= 0) {
                errorMsg.setText("Number of persons must be positive");
                return;
            }
        } catch (NumberFormatException e) {
            errorMsg.setText("Please enter a valid number of persons");
            return;
        }

        // Database credentials
        String url = "jdbc:mysql://127.0.0.1:3306/locationappartement";
        String user = "root";
        String pass = "root";        // Build SQL query using PreparedStatement to prevent SQL injection
        String sql = "SELECT * FROM appartement a WHERE a.ADDRESS LIKE ?"
                   + " AND a.NombreOfPerson >= ?"
                   + " AND NOT EXISTS ("
                   + " SELECT 1 FROM reservation r2"
                   + " WHERE r2.ID_APPARTEMENT = a.ID"
                   + " AND ((? BETWEEN r2.DATEDEBUT AND r2.DATEFIN)"
                   + " OR (? BETWEEN r2.DATEDEBUT AND r2.DATEFIN)"
                   + " OR (r2.DATEDEBUT BETWEEN ? AND ?)"
                   + " OR (r2.DATEFIN BETWEEN ? AND ?)) )";

        foundApartments.clear();
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            // Set parameters safely
            stmt.setString(1, "%" + location + "%");
            stmt.setInt(2, persons);
            stmt.setString(3, dateDebut.toString());
            stmt.setString(4, dateFin.toString());
            stmt.setString(5, dateDebut.toString());
            stmt.setString(6, dateFin.toString());
            stmt.setString(7, dateDebut.toString());
            stmt.setString(8, dateFin.toString());
            
            // Execute query
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Handle null values gracefully
                    String description = rs.getString("DESCRIPTION");
                    if (description == null) {
                        description = "No description available";
                    }
                    
                    String imageUrl = rs.getString("IMAGE_URL");
                    if (imageUrl == null) {
                        imageUrl = "default.jpg";
                    }
                    
                    Apartment apartment = new Apartment(
                        rs.getInt("ID"),
                        rs.getString("ADDRESS"),
                        rs.getDouble("PRICE"),
                        rs.getInt("NombreOfPerson"),
                        description,
                        imageUrl
                    );
                    foundApartments.add(apartment);
                }
            } catch (SQLException ex) {
                errorMsg.setText("Database error: " + ex.getMessage());
                return;
            }
        // Store the search dates for reservation purposes
        this.searchStartDate = dateDebut;
        this.searchEndDate = dateFin;
        
        if (foundApartments.isEmpty()) {
            errorMsg.setText("No apartments found for these criteria.");
        } else {
            errorMsg.setText(foundApartments.size() + " apartments available.");
            // Display the results in a new window
            displaySearchResults();
        }
        } catch (SQLException e) {
            errorMsg.setText("Database connection error: " + e.getMessage());
            e.printStackTrace();}
    }
    
    /**
     * Displays the search results in a new window
     */
    private void displaySearchResults() {
        try {
            // Create a new stage for displaying results
            Stage resultsStage = new Stage();
            resultsStage.setTitle("Available Apartments");
            
            // Create a scrollable container for apartment items
            ScrollPane scrollPane = new ScrollPane();
            VBox resultsContainer = new VBox(10);
            resultsContainer.setPadding(new Insets(20));
            scrollPane.setContent(resultsContainer);
            scrollPane.setFitToWidth(true);
            
            // Add header
            Text headerText = new Text("Available Apartments");
            headerText.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
            resultsContainer.getChildren().add(headerText);
            
            // Add search summary
            String searchSummary = String.format(
                "Found %d apartments in %s from %s to %s for %s person(s)",
                foundApartments.size(),
                locField.getText().trim(),
                searchStartDate.toString(),
                searchEndDate.toString(),
                locField1.getText().trim()
            );
            Text summaryText = new Text(searchSummary);
            summaryText.setStyle("-fx-font-size: 14px;");
            resultsContainer.getChildren().add(summaryText);
            
            // Add separator
            resultsContainer.getChildren().add(new javafx.scene.control.Separator());
            
            // Add each apartment as a card
            for (Apartment apt : foundApartments) {
                HBox apartmentCard = createApartmentCard(apt);
                resultsContainer.getChildren().add(apartmentCard);
                resultsContainer.getChildren().add(new javafx.scene.control.Separator());
            }
            
            // Set up the scene
            Scene scene = new Scene(scrollPane, 800, 600);
            resultsStage.setScene(scene);
            resultsStage.show();
            
        } catch (Exception e) {
            errorMsg.setText("Error displaying results: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Creates a visual card for an apartment listing
     * 
     * @param apartment The apartment to display
     * @return An HBox containing the apartment information
     */
    private HBox createApartmentCard(Apartment apartment) {
        // Main container
        HBox card = new HBox(15);
        card.setStyle("-fx-background-color: white; -fx-padding: 15px; -fx-border-radius: 5px;");
        card.setPadding(new Insets(10));
        
        // Apartment details container
        VBox detailsBox = new VBox(5);
        detailsBox.setPrefWidth(600);
        
        // Apartment address (title)
        Text addressText = new Text(apartment.getAddress());
        addressText.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        // Price and capacity
        Text priceText = new Text(String.format("Price: $%.2f per night", apartment.getPrice()));
        priceText.setStyle("-fx-font-size: 16px;");
        
        Text capacityText = new Text(String.format("Capacity: %d persons", apartment.getNumberOfPersons()));
        capacityText.setStyle("-fx-font-size: 14px;");
        
        // Description
        Text descriptionText = new Text("Description: " + apartment.getDescription());
        descriptionText.setStyle("-fx-font-size: 14px; -fx-wrap-text: true;");
        
        // Book button
        Button bookButton = new Button("Book Now");
        bookButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
        bookButton.setOnAction(event -> handleBooking(apartment));
        
        // Add all elements to the details box
        detailsBox.getChildren().addAll(
            addressText,
            priceText,
            capacityText,
            descriptionText,
            bookButton
        );
        
        // Add everything to the main card
        card.getChildren().add(detailsBox);
        
        return card;
    }
    
    /**
     * Handles the booking process for a selected apartment
     * 
     * @param apartment The apartment to book
     */
    private void handleBooking(Apartment apartment) {
        try {
            // Check if user is logged in (you may need to implement session management)
            boolean isLoggedIn = checkUserLoggedIn();
            
            if (!isLoggedIn) {
                // Redirect to login
                showLoginPrompt();
                return;
            }
            
            // Here you would typically:
            // 1. Get the current user ID
            // 2. Create a reservation entry
            // 3. Navigate to a confirmation page
            
            // For now, we'll just show a success message
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Booking Initiated");
            alert.setHeaderText("Booking Process Started");
            alert.setContentText("You have initiated a booking for apartment at " + 
                                apartment.getAddress() + " from " + 
                                searchStartDate + " to " + searchEndDate);
            alert.showAndWait();
            
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Booking Error");
            alert.setHeaderText("Error Processing Booking");
            alert.setContentText("An error occurred: " + e.getMessage());
            alert.showAndWait();
        }
    }
    
    /**
     * Check if user is logged in
     * This is a placeholder - implement according to your authentication system
     */
    private boolean checkUserLoggedIn() {
        // Placeholder - implement based on your authentication system
        // For example, check if a user ID is stored in a session
        return true;  // Assume logged in for now
    }
    
    /**
     * Show login prompt
     */
    private void showLoginPrompt() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Login Required");
        alert.setHeaderText("Please Log In");
        alert.setContentText("You need to be logged in to book an apartment.");
        alert.showAndWait();
        
        // Redirect to login page
        // This would typically navigate to your login screen
    }
}
