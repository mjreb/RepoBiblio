/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.example.demo.formularios.vistas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author 03_06_2023
 */
@Component
@Scope("prototype")
public class MenuUsuario extends javax.swing.JFrame {
    
        
    /*@Autowired
    private MenuPrestamo prestamo;*/
    
    @Autowired
    private ApplicationContext context;
    
    private long idUsuario; 
    
 
    
    //@Autowired
    //private MenuPrestamo menuPrestamo; 
    
       // @Autowired
    //private FormularioDevolucion formularioDevolucion; 
    
    
    /**
     * Creates new form MenuUsuario
     */
    @Autowired
    public MenuUsuario() {
        initComponents();
    }
    
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
        jButtonIrAPrestamos = new javax.swing.JButton();
        jButtonIrADevoluciones = new javax.swing.JButton();
        jButtonIrAAservo = new javax.swing.JButton();
        Cerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(402, 136));

        jLabel1.setText("Menu de usuario");

        jButtonIrAPrestamos.setText("Prestamos");
        jButtonIrAPrestamos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIrAPrestamosActionPerformed(evt);
            }
        });

        jButtonIrADevoluciones.setText("Devoluciones");
        jButtonIrADevoluciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIrADevolucionesActionPerformed(evt);
            }
        });

        jButtonIrAAservo.setText("Aservo de otras sucursales");
        jButtonIrAAservo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIrAAservoActionPerformed(evt);
            }
        });

        Cerrar.setText("Cerrar Sesión");
        Cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jButtonIrAPrestamos, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonIrADevoluciones)
                .addGap(40, 40, 40))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                        .addComponent(Cerrar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(jButtonIrAAservo)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(Cerrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonIrADevoluciones)
                    .addComponent(jButtonIrAPrestamos))
                .addGap(30, 30, 30)
                .addComponent(jButtonIrAAservo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonIrAPrestamosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIrAPrestamosActionPerformed
        MenuPrestamo menuPrestamo = context.getBean(MenuPrestamo.class);
        menuPrestamo.pasarId(idUsuario);
        System.out.println("Id en MenuUsuario antes de pasar a MenuPrestamo" + idUsuario);
        menuPrestamo.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButtonIrAPrestamosActionPerformed

    private void jButtonIrADevolucionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIrADevolucionesActionPerformed
        FormularioDevolucion formularioDevolucion= context.getBean(FormularioDevolucion.class);
        formularioDevolucion.pasarId(idUsuario);
         System.out.println("Id en MenuUsuario antes de pasar a devolucion" + idUsuario);
        formularioDevolucion.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButtonIrADevolucionesActionPerformed

    private void jButtonIrAAservoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIrAAservoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonIrAAservoActionPerformed

    private void CerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CerrarActionPerformed
        FormlarioLogin login=context.getBean(FormlarioLogin.class);
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_CerrarActionPerformed

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
            java.util.logging.Logger.getLogger(MenuUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuUsuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cerrar;
    private javax.swing.JButton jButtonIrAAservo;
    private javax.swing.JButton jButtonIrADevoluciones;
    private javax.swing.JButton jButtonIrAPrestamos;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
