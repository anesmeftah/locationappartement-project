package control;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.sql.*;

public class ModifierAppartementsController {
    @FXML private Label idLabel;
    @FXML private TextField addressField;
    @FXML private TextField roomsField;
    @FXML private TextField sizeField;
    @FXML private TextField priceField;
    @FXML private Label statusLabel;
    @FXML private ComboBox<String> statusCombo;
    @FXML private Button addressBtn;
    @FXML private Button roomsBtn;
    @FXML private Button sizeBtn;
    @FXML private Button priceBtn;
    @FXML private Button statusBtn;
    @FXML private Button saveBtn;
    @FXML private Button deleteBtn;
    @FXML private Button closeBtn;
    @FXML private Label hiddenId;

    private int appartementId = -1;

    public void initialize() {
        statusCombo.getItems().addAll("Disponible", "Réservé");
    }

    public void setAppartementId(int id) {
        this.appartementId = id;
        loadAppartement();
    }

    private void loadAppartement() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/locationappartement", "root", "root")) {
            String sql = "SELECT * FROM APPARTEMENT WHERE ID = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, appartementId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                idLabel.setText(String.valueOf(appartementId));
                addressField.setText(rs.getString("ADDRESS"));
                roomsField.setText(String.valueOf(rs.getInt("NUMBEROFROOMS")));
                sizeField.setText(String.valueOf(rs.getInt("SIZEINSQUAREMETERS")));
                priceField.setText(String.valueOf(rs.getInt("PRIX")));
                statusLabel.setText(rs.getString("STATUT"));
                statusCombo.setValue(rs.getString("STATUT"));
                hiddenId.setText(String.valueOf(appartementId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onUpdateAddress() {
        updateField("ADDRESS", addressField.getText());
    }

    @FXML
    private void onUpdateRooms() {
        updateField("NUMBEROFROOMS", roomsField.getText());
    }

    @FXML
    private void onUpdateSize() {
        updateField("SIZEINSQUAREMETERS", sizeField.getText());
    }

    @FXML
    private void onUpdatePrice() {
        updateField("PRIX", priceField.getText());
    }

    @FXML
    private void onUpdateStatus() {
        updateField("STATUT", statusCombo.getValue());
    }

    @FXML
    private void onSave() {
        // Optionally, update all fields at once
        onUpdateAddress();
        onUpdateRooms();
        onUpdateSize();
        onUpdatePrice();
        onUpdateStatus();
    }

    @FXML
    private void onDelete() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/locationappartement", "root", "root")) {
            String sql = "DELETE FROM APPARTEMENT WHERE ID = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, appartementId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onClose() {
        // Close the window or dialog
        // Implementation depends on how the FXML is loaded
    }

    private void updateField(String field, String value) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/locationappartement", "root", "root")) {
            String sql = "UPDATE APPARTEMENT SET " + field + " = ? WHERE ID = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, value);
            stmt.setInt(2, appartementId);
            stmt.executeUpdate();
            loadAppartement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
