/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;



import control.GererClientsController;
import control.baseController;
import control.controlUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import java.io.IOException;
import util.RentryException;
import javafx.collections.ObservableList;
import javax.swing.JFrame;



/**
 *
 * @author motaz
 */
public class ReservationListItemController extends baseController{
    @FXML
    private Label label;
    @FXML
    private Label AdL;
    @FXML
    private CheckBox CbC;
    @FXML
    private Button button;
    @FXML
    private String id;
    private JFrame mainFrame;

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }    public void buttonAction(){
        try{
            controlUtil control = new controlUtil();
            
            if (mainFrame == null) {
                System.out.println("Attempting to find mainFrame from parent controllers...");
                  // Try to use the findMainFrame method from baseController
                mainFrame = findMainFrame();
                
                // Also try to find the mainFrame from the window hierarchy
                if (mainFrame == null && button.getScene() != null) {
                    javafx.stage.Window window = button.getScene().getWindow();
                    if (window instanceof javafx.stage.Stage) {
                        javafx.stage.Stage stage = (javafx.stage.Stage) window;
                        System.out.println("Found JavaFX Stage: " + stage);
                    }
                }
                
                // If we still don't have a mainFrame, use a different approach
                if (mainFrame == null) {
                    System.out.println("Still unable to find mainFrame, will use workaround...");
                          // Direct approach using InfoReservationController in a new window
                    javafx.application.Platform.runLater(() -> {
                        try {
                            // Create and configure a new Stage for the InfoReservation view
                            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                                getClass().getResource("../FXML/InfoReservation.fxml"));
                            
                            // Create a new controller with the ID
                            InfoReservationController controller = new InfoReservationController(Integer.parseInt(id));
                            loader.setController(controller);
                            
                            // Load the scene
                            javafx.scene.Parent root = loader.load();
                            javafx.scene.Scene scene = new javafx.scene.Scene(root);
                            
                            // Create and show the stage
                            javafx.stage.Stage stage = new javafx.stage.Stage();
                            stage.setTitle("Reservation Details - ID: " + id);
                            stage.setScene(scene);
                            
                            // Set a close handler to return focus to the parent window
                            stage.setOnCloseRequest(event -> {
                                if (button.getScene() != null && button.getScene().getWindow() != null) {
                                    button.getScene().getWindow().requestFocus();
                                }
                            });
                            
                            stage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                                javafx.scene.control.Alert.AlertType.ERROR, 
                                "Unable to open reservation details. Please try again. Error: " + e.getMessage()
                            );
                            alert.showAndWait();
                        }
                    });
                    return;
                }
            }
            
            // If we have a mainFrame, use the normal approach
            control.set(mainFrame, "InfoReservation.fxml", InfoReservationController.class, Integer.parseInt(id));
        } catch(RentryException r){
            r.printStackTrace();
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.ERROR, 
                "An error occurred: " + r.getMessage()
            );
            alert.showAndWait();
        } catch(Exception e) {
            e.printStackTrace();
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.ERROR, 
                "An unexpected error occurred: " + e.getMessage()
            );
            alert.showAndWait();
        }
    }

    public void init(String id,String adr){
        label.setText(id);
        this.id = id;
        AdL.setText(adr);
        button.setText("Check");
    }
    
}
