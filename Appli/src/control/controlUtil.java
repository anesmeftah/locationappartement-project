/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.io.IOException;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javax.swing.JFrame;

import util.RentryException;

/**
 *
 * @author motaz
 */
public class controlUtil {
public <T> FXMLLoader loadpara(Class<T> clazz, int id, String fxml) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/" + fxml));

    loader.setControllerFactory(param -> {
        try {
            if (clazz.equals(param)) {
                return clazz.getConstructor(int.class).newInstance(id);
            } else {
                return param.getDeclaredConstructor().newInstance();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create controller instance", e);
        }
    });

    loader.load();
    return loader;
}
    private <T> JFXPanel load(JFrame mainFrame,String fxmlFile,Class<T> clazz) throws RentryException{
    if(!Platform.isFxApplicationThread()){
    throw(new RentryException("Not in a thread , which causes future errors so check that",23,true));
    }
    JFXPanel fxPanel = null;
            try {
                fxPanel = new JFXPanel();
                // Load FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/"+fxmlFile));
                Parent root = loader.load();
                T ctr = loader.getController();
                if(ctr instanceof baseController){
                ((baseController)ctr).setMainFrame(mainFrame);
                }

                // Set scene to fxPanel
                Scene scene = new Scene(root);
                fxPanel.setScene(scene);
                scene.setOnKeyPressed(e -> {
                
});
                

            } catch (IOException e) {
                e.printStackTrace();
                
            }
     
    return(fxPanel);
    }
    private <T> JFXPanel load(JFrame mainFrame,String fxmlFile,Class<T> clazz,int id) throws RentryException{
    if(!Platform.isFxApplicationThread()){
    throw(new RentryException("Not in a thread , which causes future errors so check that",23,true));
    }
    JFXPanel fxPanel = null;
            try {
                fxPanel = new JFXPanel();
                // Load FXML
                FXMLLoader loader = loadpara(clazz.getClass(),id,fxmlFile);
                Parent root = loader.getRoot();
                T ctr = loader.getController();
                if(ctr instanceof baseController){
                ((baseController)ctr).setMainFrame(mainFrame);
                }

                // Set scene to fxPanel
                Scene scene = new Scene(root);
                fxPanel.setScene(scene);
                scene.setOnKeyPressed(e -> {
                
});
                

            } catch (IOException e) {
                e.printStackTrace();
                
            }
     
    return(fxPanel);
    }
    public <T> void set(JFrame mainFrame,String fxmlFile,Class<T> clazz) throws RentryException{
        controlUtil control = new controlUtil();
        JFXPanel pan = control.load(mainFrame,fxmlFile,clazz);
    
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().add(pan);
        mainFrame.setAlwaysOnTop(true);
        mainFrame.toFront();
        mainFrame.repaint();
        mainFrame.setAlwaysOnTop(false);
                
                
                
        mainFrame.setLayout(new java.awt.FlowLayout());
        mainFrame.setLocationRelativeTo(null);   // Center the window
        mainFrame.repaint();
        mainFrame.revalidate();
    }
    public <T> void set(JFrame mainFrame,String fxmlFile,Class<T> clazz,int id) throws RentryException{
        controlUtil control = new controlUtil();
        JFXPanel pan = control.load(mainFrame,fxmlFile,clazz,id);
    
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().add(pan);
        mainFrame.setAlwaysOnTop(true);
        mainFrame.toFront();
        mainFrame.repaint();
        mainFrame.setAlwaysOnTop(false);
                
                
                
        mainFrame.setLayout(new java.awt.FlowLayout());
        mainFrame.setLocationRelativeTo(null);   // Center the window
        mainFrame.repaint();
        mainFrame.revalidate();
    }
}
