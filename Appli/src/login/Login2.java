package login;

import control.loginController;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.fxml.FXML;

import javax.swing.*;
import java.io.IOException;

public class Login2 extends JFrame {
    
    public Login2() {
        setTitle("Rentry");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        ImageIcon logo = new ImageIcon(getClass().getResource("../images/Rentry.png"));
        this.setIconImage(logo.getImage());

        JFXPanel fxPanel = new JFXPanel(); // This is the bridge
        add(fxPanel); // Add to JFrame like a JPanel

        Platform.runLater(() -> {
            try {
                // Load FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/login.fxml"));
                Parent root = loader.load();
                loginController ctr = loader.getController();
                ctr.setMainFrame(this);

                // Set scene to fxPanel
                Scene scene = new Scene(root);
                fxPanel.setScene(scene);
                scene.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    ctr.Login.fire();  // triggers the button's action
                }
});
                

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login2 window = new Login2();
            window.setVisible(true);
        });
    }
}
