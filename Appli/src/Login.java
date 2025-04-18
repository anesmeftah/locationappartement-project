/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author anasm
 */


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import javax.swing.*;
import java.awt.Image;



public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Message = new javax.swing.JLabel();
        Username = new javax.swing.JTextField();
        Login = new javax.swing.JButton();
        Password = new javax.swing.JPasswordField();
        creer = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Msg = new javax.swing.JLabel();
        gmailC = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Username(evt);
            }
        });

        Login.setBackground(new java.awt.Color(33, 150, 243));
        Login.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        Login.setForeground(new java.awt.Color(255, 255, 255));
        Login.setText("Login");
        Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Login(evt);
            }
        });

        Password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PasswordKeyPressed(evt);
            }
        });

        creer.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        creer.setText("Create Account");
        creer.setActionCommand("Creer");
        creer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creer(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        jLabel1.setText("Email/Username :");

        jLabel2.setFont(new java.awt.Font("Bahnschrift", 0, 12)); // NOI18N
        jLabel2.setText("Mot de passe :");

        jLabel3.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        jLabel3.setText("Login");

        Msg.setForeground(new java.awt.Color(255, 0, 0));

        gmailC.setText("With Google account");
        gmailC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gmailCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(Login, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(creer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(8, 8, 8))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(12, 12, 12)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(Password, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(Username, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(Msg, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)))
                        .addGap(62, 62, 62))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(gmailC, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(Login, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(creer, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Msg, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gmailC, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        Username.getAccessibleContext().setAccessibleName("Username");
        creer.getAccessibleContext().setAccessibleName("Creer");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Username(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Username
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_Username

    private void Login(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Login
        // TODO add your handling code here:
        String user = Username.getText();
        String pass = new String(Password.getPassword());
        
        try{
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";
            
            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();
            
            
            
            
            String SQL = "SELECT * FROM CLIENTS WHERE EMAIL = '" + user + "' AND MDP = '" + pass + "'";
            ResultSet rs = stmt.executeQuery(SQL);
            
            if(rs.next()){
                Login mainFrame = this;
                int Class = rs.getInt("class");
                if(Class ==2){
                Admin2 admin = new Admin2();
                mainFrame.getContentPane().removeAll();
                mainFrame.getContentPane().add(admin);
                
                
                
                mainFrame.setLayout(new java.awt.FlowLayout());
                mainFrame.setLocationRelativeTo(null);   // Center the window
                mainFrame.repaint();
                mainFrame.revalidate();
                }else{
                HomePage2 home = new HomePage2();
                mainFrame.getContentPane().removeAll();
                mainFrame.getContentPane().add(home);
                
                
                
                mainFrame.setLayout(new java.awt.FlowLayout());
                mainFrame.setLocationRelativeTo(null);   // Center the window
                mainFrame.repaint();
                mainFrame.revalidate();
                }                                       
                
                
                try(FileWriter cookie = new FileWriter("cookie.txt");){
                    
                    String SQLCOOKIE = "SELECT EMAIL FROM CLIENTS WHERE EMAIL = '" + user + "' AND MDP = '" + pass + "'";
                    ResultSet rsCookie = stmt.executeQuery(SQLCOOKIE);
                    if(rsCookie.next()){
                        Random random = new Random();
                        
                        String Cookies = rsCookie.getString("EMAIL") + (random.nextInt(1000)+1);
                        cookie.write(Cookies);
                        int affected = stmt.executeUpdate("UPDATE CLIENTS SET COOKIE = '" + Cookies + "' WHERE EMAIL = '" + rsCookie.getString("EMAIL") + "'");
                        if(affected > 0){
                            System.out.println("done");
                        }
                        else{
                            System.out.println("!done");
                        }
                    }
                    

                    
                    
                }
                catch(IOException e){
                    System.out.println(e.getMessage());
                }
            }
            else{
                Msg.setText("Email ou mot de passe invalide");
            }
            
            
            
        }
        catch(SQLException err){
            System.out.println(err.getMessage());
        }
        
    }//GEN-LAST:event_Login

    private void creer(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creer
        // TODO add your handling code here:
        String user = Username.getText();
        String pass = new String(Password.getPassword());
        
        try{
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";
            
            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();

            String SQLID = "SELECT MAX(ID) FROM CLIENTS";
            ResultSet rs = stmt.executeQuery(SQLID);
            
            int ID;
            if (rs.next()) {
                ID = rs.getInt(1) + 1;
                
            } else {
                ID = 1;
            }
            
            String SQL = "INSERT INTO CLIENTS(EMAIL,ID,MDP) VALUES ('" + user + "', " + ID + ", '" + pass + "')";
            int rowsAffected = stmt.executeUpdate(SQL);
            
            if(rowsAffected > 0){
                JOptionPane.showMessageDialog(null,"Vous pouvez s'authentifier");
            }
            else{
                Msg.setText("Une erreur s'est produite");
            }
        }
        catch(SQLException err){
            if ("23000".equals(err.getSQLState())) {
                Msg.setText("L'email est déjà utilisé. Veuillez en choisir un autre.");
            }
            else{
                System.out.println(err.getMessage());
            }
            
        }
    }//GEN-LAST:event_creer

    private void PasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PasswordKeyPressed
         if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
         Login.doClick();
         }
    }//GEN-LAST:event_PasswordKeyPressed

    private void gmailCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gmailCActionPerformed
                Random r = new Random();
                String username ="user" + r.nextInt(1000)+1;
                try{
                String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
                String uName = "root";
                String uPass = "root";

                Connection con = DriverManager.getConnection(host, uName, uPass);
                Statement stmt = con.createStatement();
                Credential credential = GmailAuth.authorize(username);
                
                Gmail gmailService = new Gmail.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), 
                GmailAuth.JSON_FACTORY, 
                credential)
                .setApplicationName(GmailAuth.APPLICATION_NAME)
                .build();
                
                Oauth2 oauth2 = new Oauth2.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                credential
                ).setApplicationName(GmailAuth.APPLICATION_NAME).build();
                
                Userinfo userInfo = oauth2.userinfo().get().execute();
                Username.setText(userInfo.getEmail());
                Password.setText(userInfo.getId());
                String SQLUN = "SELECT * FROM CLIENTS WHERE EMAIL = "+"'"+userInfo.getEmail()+"'";
                ResultSet rs = stmt.executeQuery(SQLUN);
                if(rs.next()){Login.doClick();}else{creer.doClick();Login.doClick();}
                String SQL = "UPDATE Clients" +"SET TOKEN ="+ credential.getRefreshToken() +"WHERE EMAIL = "+ userInfo.getEmail();
                int rowsAffected = stmt.executeUpdate(SQL);

                if(rowsAffected > 0){
                    JOptionPane.showMessageDialog(null,"Vous pouvez s'authentifier");
                }
                else{
                    Msg.setText("Une erreur s'est produite");
                }
                
                
                }catch(SQLException e){
                    System.out.println(e.getMessage());
                }catch(Exception e2){
                    System.out.println(e2.getMessage());
                }
                
                
                
                
                
            
    }//GEN-LAST:event_gmailCActionPerformed

    /**
     * @param args the command line arguments
     */
    public void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
        java.awt.EventQueue.invokeLater(() -> {
        Login mainFrame = new Login();// Create your main JFrame
        
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/google_icn.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        mainFrame.gmailC.setIcon(new ImageIcon(scaledImage));
        mainFrame.gmailC.setHorizontalTextPosition(SwingConstants.RIGHT);
        mainFrame.gmailC.setVerticalTextPosition(SwingConstants.CENTER);
        mainFrame.setTitle("Rentry");
        ImageIcon logo = new ImageIcon(getClass().getResource("/images/Rentry.png"));
        mainFrame.setIconImage(logo.getImage());
        
        

        
        
        
        mainFrame.pack();                        // Resize to fit content
        mainFrame.setLocationRelativeTo(null);   // Center the window
        mainFrame.setVisible(true);              // Show the window
    });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Login;
    private javax.swing.JLabel Message;
    private javax.swing.JLabel Msg;
    private javax.swing.JPasswordField Password;
    private javax.swing.JTextField Username;
    private javax.swing.JButton creer;
    private javax.swing.JButton gmailC;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
