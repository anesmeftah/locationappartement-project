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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import util.RentryException;


public class AdminController extends baseController{
    @FXML
    private Label adminName;
    @FXML
    private Button clienButton;
    @FXML
    private Button reservationbutton;
    @FXML
    private Button appartementButton;
    @FXML
    public void clienButtontoClientTab(){
    try{
                    controlUtil control = new controlUtil();
                    control.set(mainFrame,"GererClients2.fxml",GererClientsController.class);
                }catch(RentryException r){}
    }
    @FXML
    public void appartementButtontoAppartementTab(){/*
    try{
                    controlUtil control = new controlUtil();
                    control.set(mainFrame,"GererAppartements.fxml",GererAppartementsController.class);
                }catch(RentryException r){}*/
    }
    @FXML
    public void reservationButtontoAppartementTab(){
    try{
                    controlUtil control = new controlUtil();
                    control.set(mainFrame,"GererReservations.fxml",GererReservationsController.class);
                }catch(RentryException r){}
    }
}
