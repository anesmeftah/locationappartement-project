/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.swing.JFrame;
/**
 *
 * @author motaz
 */
public class VoireReservations extends javax.swing.JPanel {

    /**
     * Creates new form VoireReservations
     */
    private int id;
    private long calculPen(){
        LocalDate currentDate = LocalDate.now();
        String status = "";
        long daysBetween = 0;
        try {
        String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();
            
            String SQL = "select * from reservation where id_client = "+id;
            ResultSet rs = stmt.executeQuery(SQL);
            if(rs.next()){
                LocalDate endDate  = rs.getDate("DATEFIN").toLocalDate();
                status = rs.getString("Statut");
                daysBetween = ChronoUnit.DAYS.between(endDate,currentDate);
            }
        
        }
        catch(SQLException err){
            System.out.println("SQL Error: " + err.getMessage());
        }
        if(daysBetween >0 && !(status.equals("Paid"))){
        return(daysBetween*20);}else{return(0);}}
        
    public VoireReservations(int ID) {
        this.id = ID;
        initComponents(); 
        boolean hasResults = false;
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(contentPanel);
        
        try {
            long sum = calculPen();
            String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
            String uName = "root";
            String uPass = "root";

            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();
            Statement stmt2 = con.createStatement();
            
            String SQL = "select * from reservation where id_client = "+ID;
            ResultSet rs = stmt.executeQuery(SQL);
            while(rs.next()){
                hasResults = true;
                String id = rs.getString("ID");
                String id_app = rs.getString("ID_appartement");
                String SQLAPP = "select * from appartement where id = "+id_app;
                LocalDate endDate  = rs.getDate("DATEFIN").toLocalDate();
                LocalDate startDate  = rs.getDate("DATEDEBUT").toLocalDate();
                long daysBetween = ChronoUnit.DAYS.between(startDate,endDate);
                
                ResultSet rsp = stmt2.executeQuery(SQLAPP);
                rsp.next();
                long Prix = (long)rsp.getDouble("Prix");
                sum = calculPen() + Prix*daysBetween;
                
                
                JPanel reservationPanel = new JPanel();
                reservationPanel.setPreferredSize(new Dimension(650, 50));
                reservationPanel.setMaximumSize(new Dimension(650, 50));
                reservationPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(200, 200, 200)));
                reservationPanel.setBackground(Color.white);
                reservationPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                
                
                JLabel infoLabel = new JLabel();
                infoLabel.setFont(new Font("Bahnschrift", Font.PLAIN, 14));
                infoLabel.setText("ID: " + id + " - ID appartement : " + id_app + ", Penality " + calculPen() + " DT , Sum = "+sum+ " DT\n");
                
                reservationPanel.add(infoLabel);
                JButton reserveButton = new JButton("Pay");
                reserveButton.setFont(new Font("Bahnschrift", Font.PLAIN, 14));
                reserveButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        new Payment(Integer.parseInt(id), endDate, startDate).setVisible(true);
                        
                        
                    }
                });
                
                
                reservationPanel.add(reserveButton);
                
                contentPanel.add(reservationPanel);
                
                
                JPanel spacer = new JPanel();
                spacer.setPreferredSize(new Dimension(650, 5));
                spacer.setMaximumSize(new Dimension(650, 5));
                spacer.setOpaque(false);
                contentPanel.add(spacer);
                
                System.out.println("Added apartment: " + id_app);
            }
            
            // If no apartments found
            if (!hasResults) {
                JPanel noResultsPanel = new JPanel();
                noResultsPanel.setPreferredSize(new Dimension(650, 50));
                noResultsPanel.setBackground(Color.white);
                
                JLabel noResultsLabel = new JLabel("No reservations found");
                noResultsLabel.setFont(new Font("Bahnschrift", Font.PLAIN, 14));
                noResultsLabel.setForeground(Color.RED);
                
                noResultsPanel.add(noResultsLabel);
                contentPanel.add(noResultsPanel);
            }
            
            contentPanel.revalidate();
            contentPanel.repaint();
        }
        catch(SQLException err){
            System.out.println("SQL Error: " + err.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        List = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();

        List.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(List);

        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 701, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(319, 319, 319)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFrame mainFrame = (JFrame)javax.swing.SwingUtilities.getWindowAncestor(this);
            GererCompte2 search = new GererCompte2();
            mainFrame.getContentPane().removeAll();
            mainFrame.getContentPane().add(search);
                
                
                
            mainFrame.setLayout(new java.awt.FlowLayout());
            mainFrame.setLocationRelativeTo(null);   // Center the window
            mainFrame.repaint();
            mainFrame.revalidate();

    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> List;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
