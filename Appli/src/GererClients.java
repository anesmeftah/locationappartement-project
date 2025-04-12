/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
/**
 *
 * @author motaz
 */
public class GererClients extends javax.swing.JFrame {

    /**
     * Creates new form GererClients
     */
    public GererClients() {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        showList = new javax.swing.JButton();
        supprimerClient = new javax.swing.JButton();
        List = new javax.swing.JPanel();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Gestion de clients");

        showList.setText("Show List");
        showList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showListActionPerformed(evt);
            }
        });

        supprimerClient.setText("Supprimer Clients");
        supprimerClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supprimerClientActionPerformed(evt);
            }
        });
        supprimerClient.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                supprimerClientPropertyChange(evt);
            }
        });

        List.setBackground(new java.awt.Color(242, 242, 5));

        javax.swing.GroupLayout ListLayout = new javax.swing.GroupLayout(List);
        List.setLayout(ListLayout);
        ListLayout.setHorizontalGroup(
            ListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 324, Short.MAX_VALUE)
        );
        ListLayout.setVerticalGroup(
            ListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 260, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 134, Short.MAX_VALUE)
                                .addComponent(showList)
                                .addGap(4, 4, 4)
                                .addComponent(supprimerClient))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(List, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(showList)
                    .addComponent(supprimerClient))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(List, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void showListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showListActionPerformed
        String text = showList.getText();
        if(text.equals("Show List")){
        supprimerClient.setVisible(true);
        List.setVisible(true);
        showList.setText("Hide List");
        }else{
        supprimerClient.setVisible(false);
        List.setVisible(false);
        showList.setText("Show List");
            }
        }
        
    }//GEN-LAST:event_showListActionPerformed

    private void supprimerClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supprimerClientActionPerformed
        String txt = supprimerClient.getText();
        if(txt.equals("Supprimer Clients")){
        supprimerClient.setText("Confirmer");
    }else{if(txt.equals("Confirmer")){
        try{
                String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
                String uName = "root";
                String uPass = "root";

                Connection con = DriverManager.getConnection(host, uName, uPass);





                java.awt.Component [] C = List.getComponents();
                for(int i=0;i<C.length;i++){
                if(C[i] instanceof javax.swing.JPanel){
                    javax.swing.JPanel pan = (javax.swing.JPanel)C[i];
                    java.awt.Component [] L = pan.getComponents();
                    String value = null;
                    Boolean verif = null;
                    if(L[0] instanceof javax.swing.JLabel){
                            javax.swing.JLabel lab = (javax.swing.JLabel)L[0];
                            value = lab.getText();
                    }
                    if(L[1] instanceof javax.swing.JCheckBox){
                            javax.swing.JCheckBox check = (javax.swing.JCheckBox)L[1];
                            verif = check.isSelected();
                    }
                    if(verif){
                String sql = "DELETE FROM CLIENTS WHERE email = ?";
                PreparedStatement stmt = con.prepareStatement(sql);

                stmt.setString(1, value);

                int rowsAffected = stmt.executeUpdate();

                System.out.println("Deleted rows: " + rowsAffected);
}
                    
}
}
    supprimerClient.setText("Supprimer Clients");
}            catch(SQLException err){
                System.out.println(err.getMessage());
            }
}
}


    }//GEN-LAST:event_supprimerClientActionPerformed
    private void updateList(){
try{
                String host = "jdbc:mysql://127.0.0.1:3306/locationappartement";
                String uName = "root";
                String uPass = "root";

                Connection con = DriverManager.getConnection(host, uName, uPass);
                Statement stmt = con.createStatement();
                String SQL = "SELECT * FROM CLIENTS";
                ResultSet rs = stmt.executeQuery(SQL);
                List.removeAll();
                List.setLayout(new javax.swing.BoxLayout(List, javax.swing.BoxLayout.Y_AXIS));
                while(rs.next()){
                    String email = rs.getString("EMAIL");
                    javax.swing.JPanel pan = new javax.swing.JPanel();
                    javax.swing.JLabel Label = new javax.swing.JLabel();
                    javax.swing.JCheckBox check = new javax.swing.JCheckBox();
                    System.out.println("Lable added");
                    Label.setLocation(0, 0);
                    java.awt.Dimension MINDIM = new java.awt.Dimension(200,30);
                    Label.setMinimumSize(MINDIM);
                    Label.setBackground(Color.red);
                    Label.setText(email);
                    Label.setVisible(true);
                    pan.add(Label);
                    pan.add(check);
                    List.add(pan);
                    
            }
            List.revalidate();
            List.repaint();
            }

            catch(SQLException err){
                System.out.println(err.getMessage());
            }
}



    private void supprimerClientPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_supprimerClientPropertyChange
        if(supprimerClient.getText().equals("Supprimer Clients")){
    updateList();
}
    }//GEN-LAST:event_supprimerClientPropertyChange

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
            java.util.logging.Logger.getLogger(GererClients.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GererClients.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GererClients.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GererClients.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            new GererClients().setVisible(true);
            System.out.println("in");
            List.setVisible(false);
            supprimerClient.setVisible(false);
            updateList();

            
        }});
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel List;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton showList;
    private javax.swing.JButton supprimerClient;
    // End of variables declaration//GEN-END:variables

