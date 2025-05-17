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
import javax.swing.JFrame;
import java.util.ResourceBundle;

public class GererReservationsController extends baseController implements Initializable {

    @FXML
    private Button supprimerClient;
    @FXML
    private VBox listContainer;
    @FXML
    private Button showList;
    @FXML
    private ScrollPane scroll;
    
    // Keep a list of all sub controllers
    private java.util.List<ReservationListItemController> subControllers = new java.util.ArrayList<>();
    
    private boolean listenerEnabled = true;

    /**
     * Appelée automatiquement par JavaFX après l'injection des @FXML
     */    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listContainer.setVisible(false);
        supprimerClient.setVisible(false);
        
        // Add a listener to wait until the scene is attached to get the mainFrame
        showList.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                // Now we can get window and try to find the mainFrame
                newScene.windowProperty().addListener((obs2, oldWindow, newWindow) -> {
                    if (newWindow != null) {
                        // Wait until everything is set up
                        javafx.application.Platform.runLater(() -> {
                            try {
                                updateList();
                            } catch (RentryException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                });
            }
        });
        
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
    @FXML
    private void onBack(){
    try{
                    controlUtil control = new controlUtil();
                    mainFrame.setSize(800,500);
                    control.set(mainFrame,"Admin2.fxml",AdminController.class);
                }catch(RentryException r){}
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
                    
                    // Pass the mainFrame to the ReservationListItemController
                    controller.setMainFrame(this.mainFrame);
                    
                    // Add to our list of controllers
                    subControllers.add(controller);
                    
                    // If we have a mainFrame, log it for troubleshooting
                    if (this.mainFrame != null) {
                        System.out.println("Setting mainFrame for controller: " + this.mainFrame);
                    } else {
                        System.out.println("Warning: mainFrame is null in GererReservationsController");
                        
                        // Try to find the mainFrame
                        JFrame frame = findMainFrame();
                        if (frame != null) {
                            System.out.println("Found mainFrame: " + frame);
                            this.mainFrame = frame;
                            controller.setMainFrame(frame);
                        }
                    }

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

    /**
     * Update the mainFrame reference in all sub-controllers
     * @param frame The JFrame to set
     */
    @Override
    public void setMainFrame(JFrame frame) {
        super.setMainFrame(frame);
        
        // Also update all sub controllers
        if (subControllers != null) {
            for (ReservationListItemController controller : subControllers) {
                controller.setMainFrame(frame);
            }
        }
        
        System.out.println("MainFrame updated in GererReservationsController: " + frame);
    }
}
