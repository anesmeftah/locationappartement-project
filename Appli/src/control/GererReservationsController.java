/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

/**
 *
 * @author motaz
 */

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
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



public class GererReservationsController extends baseController {
    @FXML
    private Button supprimerClient;
    @FXML
    private VBox listContainer;
    @FXML
    private Button showList;
    @FXML
    public void showListAction(){
        try{
            updateList();
            }catch(RentryException e){}
        if(showList.getText().equals("Show List")){
            supprimerClient.setVisible(true);
            System.out.println("list:");
            listContainer.setVisible(true);
            ObservableList<Node> C = listContainer.getChildren();
            for(int i=0;i<C.size();i++){
                System.out.println(i);
            }
            showList.setText("Hide List");
            
        }else{
            
            supprimerClient.setVisible(false);
            listContainer.setVisible(false);
            listContainer.getChildren().clear();
            showList.setText("Show List");
        }
        
    }
    @FXML
    public void supprimerClientAction(){
    String txt = supprimerClient.getText();
        if(txt.equals("Supprimer Reservations")){
            supprimerClient.setText("Confirmer");
        }else{if(txt.equals("Confirmer")){
            try{
                String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
                String uName = "root";
                String uPass = "root";

                Connection con = DriverManager.getConnection(host, uName, uPass);

                ObservableList<Node> C = listContainer.getChildren();
                for(int i=0;i<C.size();i++){
                    if(C.get(i) instanceof Pane){
                        ObservableList<Node> children = ((Pane)C.get(i)).getChildren();
                        String value = null;
                        Boolean verif = null;
                        if(children.get(0) instanceof Label){
                            value = ((Label)children.get(0)).getText();
                        }
                        if(children.get(1) instanceof CheckBox ){
                            verif = ((CheckBox)children.get(1)).isSelected();
                        }
                        if(verif){
                            String sql = "DELETE FROM RESERVATION WHERE id = ?";
                            PreparedStatement stmt = con.prepareStatement(sql);

                            stmt.setString(1, value);

                            int rowsAffected = stmt.executeUpdate();

                            System.out.println("Deleted rows: " + rowsAffected);
                        }

                    }
                }
                supprimerClient.setText("Supprimer Reservations");
            }            catch(SQLException err){
                System.out.println(err.getMessage());
            }
        }
        }
    }
    private boolean listenerEnabled = true;
    private void updateList() throws RentryException{
try{
                String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
                String uName = "root";
                String uPass = "root";

                Connection con = DriverManager.getConnection(host, uName, uPass);
                Statement stmt = con.createStatement();
                Statement stmt2 = con.createStatement();
                String SQL = "SELECT * FROM RESERVATION";
                ResultSet rs = stmt.executeQuery(SQL);
                supprimerClient.textProperty().addListener((obs,old,newv)->{if(!listenerEnabled){return;}
                try{
                if(newv.equals("Supprimer Reservations")){updateList();}}catch(RentryException e){}});
                listContainer.getChildren().clear();
                while(rs.next()){
                    System.out.println("in1");
                    String adr = null;
                    String ID = rs.getString("ID");
                    String id_adr = rs.getString("ID_APPARTEMENT");
                    String SQL2 = "SELECT * FROM APPARTEMENT WHERE ID ="+id_adr;
                    ResultSet rs2 = stmt2.executeQuery(SQL2);
                    while(rs2.next()){
                    adr = rs2.getString("ADDRESS");
                    }
                    rs2.close();
                    try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/ReservationListItem.fxml"));
                Pane pan = loader.load();

                ReservationListItemController controller = loader.getController();
                controller.init(ID, adr);
                ((CheckBox)pan.getChildren().get(1)).selectedProperty().addListener((obs,old,newv)->{listenerEnabled = false;
                supprimerClient.setText("Supprimer Reservations");
                listenerEnabled = true;});
                System.out.println(pan);
                pan.setVisible(true);
                pan.setManaged(true);
                listContainer.getChildren().add(pan);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
                    
                    
            
                }
            listContainer.requestLayout();
            rs.close();
            stmt.close();
            }

            catch(SQLException err){
                System.out.println(err.getMessage());
            }
}

public void init(){
    listContainer.setVisible(false);
    supprimerClient.setVisible(false);
    try{
            updateList();
            }catch(RentryException e){}
    
}
}

