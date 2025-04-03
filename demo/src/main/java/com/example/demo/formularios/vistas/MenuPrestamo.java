/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.example.demo.formularios.vistas;

import com.example.demo.capanegocio.PrestamoService;
import javax.swing.JOptionPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author 03_06_2023
 */

@Component
@Scope("prototype")
public class MenuPrestamo extends javax.swing.JFrame {
    /*
      @Autowired 
    private MenuUsuario menuUsuario;
*/
    /**
     * Creates new form MenuPrestamo
     */
    
    @Autowired 
    private ApplicationContext context;
    
    @Autowired
    private PrestamoService prestamoService;
    
    public MenuPrestamo() {
        initComponents();
    }
    
   
     
     private long idUsuario; 
    
    public void  pasarId(long id){
       this.idUsuario = id; 
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
        jButtonIrAConsulta = new javax.swing.JButton();
        jButtonCrearPrestamo = new javax.swing.JButton();
        jButtonIrAMenuUsuario = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Menu de préstamos");

        jButtonIrAConsulta.setText("Consultar");
        jButtonIrAConsulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIrAConsultaActionPerformed(evt);
            }
        });

        jButtonCrearPrestamo.setText("Crear préstamo");
        jButtonCrearPrestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCrearPrestamoActionPerformed(evt);
            }
        });

        jButtonIrAMenuUsuario.setText("Volver");
        jButtonIrAMenuUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIrAMenuUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonIrAMenuUsuario)
                        .addGap(21, 21, 21))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonIrAConsulta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                        .addComponent(jButtonCrearPrestamo)
                        .addGap(70, 70, 70))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButtonIrAMenuUsuario))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonIrAConsulta)
                    .addComponent(jButtonCrearPrestamo))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonIrAMenuUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIrAMenuUsuarioActionPerformed
        MenuUsuario menuUsuario = context.getBean(MenuUsuario.class);
        menuUsuario.pasarId(idUsuario);
        System.out.println("Id en MenuPrestamo  antes de regresar a MenuUsuario" + idUsuario);
        
        menuUsuario.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButtonIrAMenuUsuarioActionPerformed

    private void jButtonIrAConsultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIrAConsultaActionPerformed
        System.out.println(prestamoService.recuperaPrestamosPorUsuario(idUsuario));
        
        
        /*TablaPrestamos prestamos=new TablaPrestamos();
        prestamos.setVisible(true);
        this.dispose();
        */
    }//GEN-LAST:event_jButtonIrAConsultaActionPerformed

    private void jButtonCrearPrestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCrearPrestamoActionPerformed
        
       try{
           prestamoService.revisaCondiciones(idUsuario);
           
       }catch(IllegalStateException i){
           JOptionPane.showMessageDialog(this, i.getMessage());
           
           
       }
        
       
           
    }//GEN-LAST:event_jButtonCrearPrestamoActionPerformed

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
            java.util.logging.Logger.getLogger(MenuPrestamo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuPrestamo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuPrestamo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuPrestamo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrestamo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCrearPrestamo;
    private javax.swing.JButton jButtonIrAConsulta;
    private javax.swing.JButton jButtonIrAMenuUsuario;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
