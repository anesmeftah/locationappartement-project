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
                    mainFrame.setSize(600,485);
                    control.set(mainFrame,"GererClients2.fxml",GererClientsController.class);
                }catch(RentryException r){}
    }    @FXML
    public void appartementButtontoAppartementTab() {
        try {
            if (mainFrame == null) {
                mainFrame = findMainFrame();
                System.out.println("AdminController found mainFrame for Appartements: " + mainFrame);
            }
            
            System.out.println("Loading GererAppartements.fxml...");
            
            controlUtil control = new controlUtil();
            mainFrame.setSize(800,500);
            control.set(mainFrame, "GererAppartements.fxml", GererAppartementsController.class);
            System.out.println("GererAppartements.fxml loaded successfully");
        } catch (Exception e) {
            System.err.println("Error loading GererAppartements.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    public void reservationButtontoAppartementTab(){
    try{
                    if (mainFrame == null) {
                        mainFrame = findMainFrame();
                        System.out.println("AdminController found mainFrame: " + mainFrame);
                    }
                    
                    controlUtil control = new controlUtil();
                    mainFrame.setSize(800,500);
                    control.set(mainFrame,"GererReservations.fxml",GererReservationsController.class);
                    
                    System.out.println("AdminController loaded GererReservationsController with mainFrame: " + mainFrame);
                }catch(RentryException r){
                    r.printStackTrace();
                }
    }
}
