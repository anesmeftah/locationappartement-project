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
import javax.swing.SwingUtilities;
import java.awt.FlowLayout;

import util.RentryException;

/**
 *
 * @author motaz
 */
public class controlUtil {
    static {
    // Ensures JavaFX Platform is initialized
    new JFXPanel(); // Initializes JavaFX environment
    Platform.setImplicitExit(false); // Optional: prevents JVM shutdown on FX exit
}
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

public <T> FXMLLoader loadparaEmail(Class<T> clazz, String email, String fxml) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/" + fxml));

    loader.setControllerFactory(param -> {
        try {
            if (clazz.equals(param)) {
                return clazz.getConstructor(String.class).newInstance(email);
            } else {
                return param.getDeclaredConstructor().newInstance();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create controller instance", e);
        }
    });

    loader.load();
    return loader;
}private <T> JFXPanel load(JFrame mainFrame, String fxmlFile, Class<T> clazz) throws RentryException {
    JFXPanel fxPanel = new JFXPanel(); // This is OK to create on the Swing thread

    Platform.runLater(() -> {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/" + fxmlFile));
            loader.setControllerFactory(param -> {
                try {
                    if (clazz.equals(param)) {
                        return clazz.getDeclaredConstructor().newInstance();
                    } else {
                        return param.getDeclaredConstructor().newInstance();
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Failed to create controller instance", e);
                }
            });

            Parent root = loader.load();
            T ctr = loader.getController();
            if (ctr instanceof baseController) {
                ((baseController) ctr).setMainFrame(mainFrame);
            }

            Scene scene = new Scene(root);
            fxPanel.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    });

    return fxPanel;
}

    private <T> JFXPanel loadWithId(JFrame mainFrame,String fxmlFile,Class<T> clazz,int id) throws RentryException{
    if(!Platform.isFxApplicationThread()){
    throw(new RentryException("Not in a thread , which causes future errors so check that",23,true));
    }
    JFXPanel fxPanel = null;
            try {
                fxPanel = new JFXPanel();
                // Load FXML
                FXMLLoader loader = loadpara(clazz,id,fxmlFile);
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
    public <T> void set(JFrame mainFrame, String fxmlFile, Class<T> clazz) throws RentryException {
    // ⚠️ Initializes JavaFX toolkit
    JFXPanel fxPanel = new JFXPanel();

    Platform.runLater(() -> {
        try {
            // Load FXML on JavaFX thread
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/" + fxmlFile));
            loader.setControllerFactory(param -> {
                try {
                    if (clazz.equals(param)) {
                        return clazz.getDeclaredConstructor().newInstance();
                    } else {
                        return param.getDeclaredConstructor().newInstance();
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Failed to create controller instance", e);
                }
            });

            Parent root = loader.load();
            T ctr = loader.getController();
            if (ctr instanceof baseController) {
                ((baseController) ctr).setMainFrame(mainFrame);
            }

            Scene scene = new Scene(root);
            fxPanel.setScene(scene);
            

            // 🔁 Now switch back to Swing thread to update JFrame
            SwingUtilities.invokeLater(() -> {
                mainFrame.getContentPane().removeAll();
                mainFrame.getContentPane().add(fxPanel);
                mainFrame.setLayout(new FlowLayout());
                mainFrame.pack(); 
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setAlwaysOnTop(true);
                mainFrame.toFront();
                mainFrame.setAlwaysOnTop(false);
                mainFrame.revalidate();
                mainFrame.repaint();
                System.out.println("Frame is set to fxml file: " + fxmlFile);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    });
}

    
    public <T> void setWithId(JFrame mainFrame,String fxmlFile,Class<T> clazz,int id) throws RentryException{
        controlUtil control = new controlUtil();
        JFXPanel pan = control.loadWithId(mainFrame,fxmlFile,clazz,id);
    
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().add(pan);
        mainFrame.setAlwaysOnTop(true);
        mainFrame.toFront();
        mainFrame.setLayout(new FlowLayout());
        mainFrame.repaint();
        mainFrame.setAlwaysOnTop(false);
                
                
                
        mainFrame.setLayout(new java.awt.FlowLayout());
        mainFrame.setLocationRelativeTo(null);   // Center the window
        mainFrame.repaint();
        mainFrame.revalidate();
        Platform.runLater(() -> {
        mainFrame.setSize(pan.getPreferredSize());});
        
    }
    
    private <T> JFXPanel loadWithEmail(JFrame mainFrame, String fxmlFile, Class<T> clazz, String email) throws RentryException {
        if(!Platform.isFxApplicationThread()) {
            throw(new RentryException("Not in a thread, which causes future errors so check that", 23, true));
        }
        JFXPanel fxPanel = null;
        try {
            fxPanel = new JFXPanel();
            // Load FXML
            FXMLLoader loader = loadparaEmail(clazz, email, fxmlFile);
            Parent root = loader.getRoot();
            T ctr = loader.getController();
            if(ctr instanceof baseController) {
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
    
    public <T> void setWithEmail(JFrame mainFrame, String fxmlFile, Class<T> clazz, String email) throws RentryException {
        controlUtil control = new controlUtil();
        JFXPanel pan = control.loadWithEmail(mainFrame, fxmlFile, clazz, email);
    
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().add(pan);
        mainFrame.setAlwaysOnTop(true);
        mainFrame.toFront();
        mainFrame.setLayout(new FlowLayout());
        mainFrame.repaint();
        mainFrame.setAlwaysOnTop(false);
                
                
                
        mainFrame.setLayout(new java.awt.FlowLayout());
        mainFrame.setLocationRelativeTo(null);   // Center the window
        mainFrame.repaint();
        mainFrame.revalidate();
        Platform.runLater(() -> {
        mainFrame.setSize(pan.getPreferredSize());});
    }
}
