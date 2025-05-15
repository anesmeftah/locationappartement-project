/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;

import util.RentryException;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
/**
 *
 * @author motaz
 */
public class InfoReservationController extends baseController{
    private int ID;
    @FXML private Label idLabel;
    @FXML private Label usernameLabel;
    @FXML private Label statusLabel;
    @FXML private Button changeFromBtn;
    @FXML private Button changeTillBtn;
    @FXML private Button changePayBtn;
    @FXML private Button changeStatusBtn;
    @FXML private Button closeBtn;
    @FXML private DatePicker dateFromPicker;
    @FXML private DatePicker dateTillPicker;
    @FXML private DatePicker payLimitPicker;
    @FXML private TextField addressField;
    @FXML private ComboBox StatusCombo;
    @FXML 
    public void onChangeFrom(){}
    @FXML 
    public void onChangeTill(){}
    @FXML 
    public void onChangeStatus(){}
    @FXML 
    public void onChangePay(){}
    @FXML 
    public void onClose(){
        // Close the window
        if (closeBtn != null && closeBtn.getScene() != null && closeBtn.getScene().getWindow() != null) {
            closeBtn.getScene().getWindow().hide();
        }
    }
    @FXML
    public void initialize() {
        if (ID != 0) {
            update();
        }
    }
    public InfoReservationController(int id){
        this.ID = id;
    }
    public InfoReservationController(){}
    private void update(){
    try{
                String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
                String uName = "root";
                String uPass = "root";

                Connection con = DriverManager.getConnection(host, uName, uPass);
                Statement stmt = con.createStatement();
                Statement stmt2 = con.createStatement();
                Statement stmt3 = con.createStatement();
                
                String SQL = "SELECT * FROM RESERVATION WHERE ID ="+ID;
                ResultSet rs = stmt.executeQuery(SQL);
                
                while(rs.next()){
                    idLabel.setText(rs.getString("ID"));
                    int clientID = rs.getInt("ID_CLIENT");
                    int appID = rs.getInt("ID_APPARTEMENT");
                    String SQL2 = "SELECT * FROM CLIENTS WHERE ID = "+clientID;
                    ResultSet rs2 = stmt2.executeQuery(SQL2);
                    rs2.next();
                    String SQL3 = "SELECT * FROM APPARTEMENT WHERE ID ="+appID;
                    ResultSet rs3 = stmt3.executeQuery(SQL3);
                    rs3.next();
                    addressField.setText(rs3.getString("ADDRESS"));
                    dateFromPicker.setValue(rs.getDate("DATEDEBUT").toLocalDate());
                    dateTillPicker.setValue(rs.getDate("DATEFIN").toLocalDate());
                    statusLabel.setText(rs.getString("STATUT"));
                    
                    /*PbL.setText(rs.getDate("PAYBEFORE")); */

                    usernameLabel.setText(rs2.getString("EMAIL"));
                    rs2.close();stmt2.close();
                    rs3.close();stmt3.close();
                
                    

                }
                rs.close();stmt.close();
        }catch(SQLException err){
                System.out.println(err.getMessage());
            }
    }
}
