/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zechat;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author vi
 */
public class FormPrincipal extends javax.swing.JFrame {
          private Chat c;
          private DefaultListModel list; 

          /**
           * Creates new form FormPrincipal
           */
          public FormPrincipal() {
                    initComponents();
          }
          
          public FormPrincipal(Chat c) {
                    initComponents();
                    this.c = c;
                    offline();
                    list= new DefaultListModel();
                    jList1.setModel(list);
          }
          
          public void updateList(){
                    clearList();
                    for(Contato co : c.getCtts()){
                        list.addElement(co.getNick());
                    }
                    jList1.setModel(list);
                    jLabel1.setText(c.getCtts().size()+" Contatos onlines");
          }
          
          public void clearList(){
                    list.clear();
          }
          
          protected void offline(){
                    jLabel1.setVisible(false);
                    jList1.setVisible(false);
                    jButton1.setVisible(true);
                    jButton2.setVisible(false);
                    jLabel2.setText(null);
                    jScrollPane1.setVisible(false);
          }
          
          protected void online(){
                    jLabel1.setVisible(true);
                    jList1.setVisible(true);
                    jButton1.setVisible(false);
                    jButton2.setVisible(true);
                    jLabel2.setText(String.format(":%s - ONLINE",c.getNick()));
                    jScrollPane1.setVisible(true);
          }

          /**
           * This method is called from within the constructor to initialize the
           * form. WARNING: Do NOT modify this code. The content of this method
           * is always regenerated by the Form Editor.
           */
          @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setText("Fazer Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(151, 134, -1, -1));

        jScrollPane1.setViewportView(jList1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, 140, 210));

        jLabel1.setText("Usuários Online");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, -1, -1));

        jButton2.setText("Iniciar Chat");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 260, -1, -1));

        jLabel2.setText("jLabel2");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

          private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
           try {
               // TODO add your handling code here:
               c.fazerLogin(JOptionPane.showInputDialog("Escolha seu nick (max 10)"));
           } catch (IOException ex) {
               Logger.getLogger(FormPrincipal.class.getName()).log(Level.SEVERE, null, ex);
           }
           online();
          }//GEN-LAST:event_jButton1ActionPerformed

          private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
            try {
               // TODO add your handling code here:
               c.logout();
            } catch (IOException ex) {
               Logger.getLogger(FormPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }    
          }//GEN-LAST:event_formWindowClosing

          private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
                    // TODO add your handling code here:
                    if(!jList1.isSelectionEmpty()){
                              c.iniciarChat(jList1.getSelectedIndex());
                    }else{
                              JOptionPane.showMessageDialog(null, "Selecione um contato para iniciar uma conversa");
                    }
          }//GEN-LAST:event_jButton2ActionPerformed

          /**
           * @param args the command line arguments
           */
          public static void main(String args[]) {
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
                              java.util.logging.Logger.getLogger(FormPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    } catch (InstantiationException ex) {
                              java.util.logging.Logger.getLogger(FormPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                              java.util.logging.Logger.getLogger(FormPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                              java.util.logging.Logger.getLogger(FormPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                    //</editor-fold>

                    /* Create and display the form */
                    java.awt.EventQueue.invokeLater(new Runnable() {
                              public void run() {
                                        new FormPrincipal().setVisible(true);
                              }
                    });
          }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}