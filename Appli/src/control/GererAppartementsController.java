/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 *
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
    
    @FXML
    public void showListAction() {
        // Toggle the visibility of the scroll pane
        boolean isVisible = scroll.isVisible();
        scroll.setVisible(!isVisible);
        
        // Toggle the button text based on the visibility status
        showList.setText(isVisible ? "Show List" : "Hide List");
        
        // Show/hide the delete button based on the scroll visibility
        supprimerClient.setVisible(!isVisible);
    }
      @FXML
    public void supprimerClientAction() {
        // Implement the functionality to delete selected apartments
        System.out.println("Delete apartments action triggered");
        // Add your code to handle apartment deletion here
    }
    
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
}
