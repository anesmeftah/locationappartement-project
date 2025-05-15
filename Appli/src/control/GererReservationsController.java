package control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.collections.ObservableList;
import util.RentryException;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class GererReservationsController implements Initializable {

    @FXML
    private Button supprimerClient;
    @FXML
    private VBox listContainer;
    @FXML
    private Button showList;
    @FXML
    private ScrollPane scroll;
    

    private boolean listenerEnabled = true;

    /**
     * Appelée automatiquement par JavaFX après l'injection des @FXML
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listContainer.setVisible(false);
        supprimerClient.setVisible(false);
        try {
            updateList();
        } catch (RentryException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showListAction() {
        try {
            updateList();
        } catch (RentryException e) {
        }
        if (showList.getText().equals("Show List")) {
            supprimerClient.setVisible(true);
            scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            // Show the scroll pane (not just the VBox)
            if (scroll != null) scroll.setVisible(true);
            listContainer.setVisible(true);
            showList.setText("Hide List");
        } else {
            supprimerClient.setVisible(false);
            if (scroll != null) scroll.setVisible(false);
            listContainer.setVisible(false);
            listContainer.getChildren().clear();
            showList.setText("Show List");
        }
    }

    @FXML
    private void supprimerClientAction() {
        String txt = supprimerClient.getText();
        if (txt.equals("Supprimer Reservations")) {
            supprimerClient.setText("Confirmer");
        } else if (txt.equals("Confirmer")) {
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";
            try (Connection con = DriverManager.getConnection(host, uName, uPass)) {
                ObservableList<Node> children = listContainer.getChildren();
                for (Node node : children) {
                    if (node instanceof Pane) {
                        ObservableList<Node> paneChildren = ((Pane) node).getChildren();
                        String value = null;
                        boolean verif = false;
                        if (paneChildren.get(0) instanceof Label) {
                            value = ((Label) paneChildren.get(0)).getText();
                        }
                        if (paneChildren.get(1) instanceof CheckBox) {
                            verif = ((CheckBox) paneChildren.get(1)).isSelected();
                        }
                        if (verif && value != null) {
                            String sql = "DELETE FROM RESERVATION WHERE id = ?";
                            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                                stmt.setString(1, value);
                                int rowsAffected = stmt.executeUpdate();
                                System.out.println("Deleted rows: " + rowsAffected);
                            }
                        }
                    }
                }
                supprimerClient.setText("Supprimer Reservations");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateList() throws RentryException {
        String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
        String uName = "root";
        String uPass = "root";
        try (Connection con = DriverManager.getConnection(host, uName, uPass);
             Statement stmt = con.createStatement();
             Statement stmt2 = con.createStatement()) {

            String SQL = "SELECT * FROM RESERVATION";
            ResultSet rs = stmt.executeQuery(SQL);

            // Réaction au retour en mode "Supprimer"
            supprimerClient.textProperty().addListener((obs, oldVal, newVal) -> {
                if (!listenerEnabled) return;
                try {
                    if (newVal.equals("Supprimer Reservations")) {
                        updateList();
                    }
                } catch (RentryException e) {
                    e.printStackTrace();
                }
            });

            listContainer.getChildren().clear();
            while (rs.next()) {
                String ID = rs.getString("ID");
                String id_adr = rs.getString("ID_APPARTEMENT");
                String adr = null;

                String SQL2 = "SELECT ADDRESS FROM APPARTEMENT WHERE ID = " + id_adr;
                ResultSet rs2 = stmt2.executeQuery(SQL2);
                if (rs2.next()) {
                    adr = rs2.getString("ADDRESS");
                }
                rs2.close();

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ReservationListItem.fxml"));
                    Pane pan = loader.load();

                    ReservationListItemController controller = loader.getController();
                    controller.init(ID, adr);
                    

                    ((CheckBox) pan.getChildren().get(1)).selectedProperty().addListener((obs, oldVal, newVal) -> {
                        listenerEnabled = false;
                        supprimerClient.setText("Supprimer Reservations");
                        listenerEnabled = true;
                    });

                    listContainer.getChildren().add(pan);
                    if(listContainer.getChildren().size()>9){
                        listContainer.setPrefHeight(listContainer.getPrefHeight()+48);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            rs.close();
            listContainer.requestLayout();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
