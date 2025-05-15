package control;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author motaz
 */

import clienp.BecomeHost2;
import client.RechercheAppartement2;
import java.awt.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.embed.swing.JFXPanel;

import javax.swing.*;

import java.awt.event.*;

import control.baseController;
import control.controlUtil;
import util.RentryException;

public class HomeController extends baseController{
    
    private void showDialoge(){
    JOptionPane optionPane = new JOptionPane(
                "You can now login with this account",
                JOptionPane.INFORMATION_MESSAGE
            );

            JDialog dialog = optionPane.createDialog("i");
            dialog.setAlwaysOnTop(true);
                Timer timer = new Timer(3000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });
            timer.setRepeats(false); // Only execute once
            timer.start();
            dialog.setVisible(true);
    }
    @FXML
    private Button searchB;

    @FXML
    private Button hostB;

    // Tes méthodes d'action ici
    @FXML
    private void SearchAction() {
        try{
        controlUtil control = new controlUtil();
        mainFrame.setSize(800,500);
        control.set(mainFrame,"RechercheAppartement2.fxml",RechercheController.class);
        }catch(RentryException r){}

    }

    @FXML
    private void hostAction() {
        
        try{
        controlUtil control = new controlUtil();
        mainFrame.setSize(800,500);
        control.set(mainFrame,"BecomeHost2.fxml",HostController.class);
        }catch(RentryException r){}
    }
    
}