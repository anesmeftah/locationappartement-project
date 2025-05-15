/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;



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



/**
 *
 * @author motaz
 */
public class ReservationListItemController {
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
    public void buttonAction(){/*ADD CHANGE SCENE*/}
    public void init(String id,String adr){
        label.setText(id);
        this.id = id;
        AdL.setText(adr);
        button.setText("Check");
}
    
}
