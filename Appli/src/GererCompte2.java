/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 *
 * @author motaz
 */
public class GererCompte2 extends javax.swing.JPanel {

    /**
     * Creates new form GererCompte2
     */
    public GererCompte2() {
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

        jLabel1 = new javax.swing.JLabel();
        EmailL = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Email = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        Tele = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Numero = new javax.swing.JTextField();
        ChEmail = new javax.swing.JButton();
        ChNum = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        Password = new javax.swing.JButton();
        motdp = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        FirstNameF = new javax.swing.JTextField();
        LastNameF = new javax.swing.JTextField();
        FirstNameB = new javax.swing.JButton();
        LastNameB = new javax.swing.JButton();
        FirstNameL = new javax.swing.JLabel();
        LastNameL = new javax.swing.JLabel();

        jLabel1.setText("Current Email : ");

        EmailL.setText("Email");

        jLabel3.setText("New Email : ");

        Email.setText("Enter Email");

        jLabel4.setText("Current Numero : ");

        Tele.setText("Telephone");

        jLabel6.setText("New Numero : ");

        Numero.setText("Enter Num");

        ChEmail.setText("Change");
        ChEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChEmailChangeEmail(evt);
            }
        });

        ChNum.setText("Change");
        ChNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChNumChangeNum(evt);
            }
        });

        jLabel7.setText("New Password : ");

        Password.setText("Change");
        Password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangerPassword(evt);
            }
        });

        jLabel2.setText("FirstName : ");

        jLabel5.setText("LastName : ");

        FirstNameF.setText("Enter First Name");

        LastNameF.setText("Enter Last Name");

        FirstNameB.setText("Change");
        FirstNameB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangerFirstNameB(evt);
            }
        });

        LastNameB.setText("Change");
        LastNameB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChangerLastNameB(evt);
            }
        });

        FirstNameL.setText("First");

        LastNameL.setText("Last");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LastNameL, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FirstNameL, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Tele, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(EmailL, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Email, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ChEmail))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Numero, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(motdp, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FirstNameF)
                            .addComponent(LastNameF))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ChNum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Password, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(FirstNameB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(LastNameB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(EmailL))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ChEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(Tele))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(Numero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ChNum, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(Password)
                    .addComponent(motdp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(FirstNameF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(FirstNameB)
                    .addComponent(FirstNameL))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(LastNameF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LastNameB)
                    .addComponent(LastNameL))
                .addContainerGap(18, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void ChEmailChangeEmail(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChEmailChangeEmail
        String NEMAIL = Email.getText();
        try{
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();
            String Cookies = GetCookie();
            String SQLMDP = "UPDATE CLIENTS SET EMAIL = '" + NEMAIL + "' WHERE COOKIE = '" + Cookies + "'";

            int affected = stmt.executeUpdate(SQLMDP);
            if(affected > 0){
                System.out.println("done");
                UpdateLabel("EMAIL",EmailL);
            }
            else{
                System.out.println("!done");
            }

        }
        catch(SQLException err){
            System.out.println(err.getMessage());
        }
    }//GEN-LAST:event_ChEmailChangeEmail

    private void ChNumChangeNum(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChNumChangeNum
        String Num = Numero.getText(); //verification du numero
        try{
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();
            String Cookies = GetCookie();
            String SQLMDP = "UPDATE CLIENTS SET TELEPHONE = '" + Num + "' WHERE COOKIE = '" + Cookies + "'";

            int affected = stmt.executeUpdate(SQLMDP);
            if(affected > 0){
                UpdateLabel("TELEPHONE",Tele);
            }
            else{
                System.out.println("!done");
            }

        }
        catch(SQLException err){
            System.out.println(err.getMessage());
        }
    }//GEN-LAST:event_ChNumChangeNum

    private void ChangerPassword(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChangerPassword
        String NMDP = new String(motdp.getPassword());
        try{
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();
            String Cookies = GetCookie();
            String SQLMDP = "UPDATE CLIENTS SET MDP = '" + NMDP + "' WHERE COOKIE = '" + Cookies + "'";

            int affected = stmt.executeUpdate(SQLMDP);
            if(affected > 0){
                System.out.println("done");
            }
            else{
                System.out.println("!done");
            }

        }
        catch(SQLException err){
            System.out.println(err.getMessage());
        }
    }//GEN-LAST:event_ChangerPassword

    private void ChangerFirstNameB(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChangerFirstNameB
        String Text = FirstNameF.getText();
        try{
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();
            String Cookies = GetCookie();
            String SQLMDP = "UPDATE CLIENTS SET FIRSTNAME = '" + Text + "' WHERE COOKIE = '" + Cookies + "'";

            int affected = stmt.executeUpdate(SQLMDP);
            if(affected > 0){
                UpdateLabel("FIRSTNAME",FirstNameL);
            }
            else{
                System.out.println("!done");
            }

        }
        catch(SQLException err){
            System.out.println(err.getMessage());
        }

    }//GEN-LAST:event_ChangerFirstNameB

    private void ChangerLastNameB(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChangerLastNameB
        String Text = LastNameF.getText();
        try{
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();
            String Cookies = GetCookie();
            String SQLMDP = "UPDATE CLIENTS SET LASTNAME = '" + Text + "' WHERE COOKIE = '" + Cookies + "'";

            int affected = stmt.executeUpdate(SQLMDP);
            if(affected > 0){
                UpdateLabel("LASTNAME",LastNameL);
            }
            else{
                System.out.println("!done");
            }

        }
        catch(SQLException err){
            System.out.println(err.getMessage());
        }
    }//GEN-LAST:event_ChangerLastNameB
String GetCookie(){
        try{
                File myObj = new File("cookie.txt");
                Scanner Reader = new Scanner(myObj);
                String cookies = Reader.nextLine();
                Reader.close();
                return cookies;
            }
            catch(FileNotFoundException e){
                System.out.println(e.getMessage());
                return ("-1");
            }
        } 
    
    void UpdateLabel(String column, javax.swing.JLabel label){
        
                
        try{
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();
            String cookies = GetCookie();
            if(cookies.equals("-1")){

                label.setText("You Have to login");

            }
            else{
                
                String SQL = "SELECT " + column + " FROM CLIENTS WHERE COOKIE = '" + cookies + "'";
                ResultSet rs = stmt.executeQuery(SQL);
                if(rs.next()){
                    label.setText(rs.getString(column));
                }
                else{
                    label.setText("You Have to login");
                }
            }  
        }
        catch(SQLException err){
            System.out.println(err.getMessage());
        }
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ChEmail;
    private javax.swing.JButton ChNum;
    private javax.swing.JTextField Email;
    private javax.swing.JLabel EmailL;
    private javax.swing.JButton FirstNameB;
    private javax.swing.JTextField FirstNameF;
    private javax.swing.JLabel FirstNameL;
    private javax.swing.JButton LastNameB;
    private javax.swing.JTextField LastNameF;
    private javax.swing.JLabel LastNameL;
    private javax.swing.JTextField Numero;
    private javax.swing.JButton Password;
    private javax.swing.JLabel Tele;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPasswordField motdp;
    // End of variables declaration//GEN-END:variables
}
