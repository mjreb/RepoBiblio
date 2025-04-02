/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.example.demo.formularios.admin;

import com.example.demo.capanegocio.UserService;
import com.example.demo.capanegocio.modelo.Usuario;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import jakarta.annotation.PostConstruct;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.io.File;

/**
 *
 * @author Diegogo
 */
@Component
@Scope("prototype")
public class GenerarMulta extends javax.swing.JFrame {
    
    private List<Usuario> usuarios;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ApplicationContext context;
    /**
     * Creates new form GenerarMulta
     */
    public GenerarMulta() {
        initComponents();
    }
    
    @PostConstruct
    private void cargarUsuarios(){
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Correo");
        modelo.addColumn("Apellido");
        
        usuarios = userService.recuperaUsuarios();
        
        for(Usuario usuario: usuarios){
            modelo.addRow(new Object[]{
                usuario.getidUsuario(),
                usuario.getNombre(),
                usuario.getApellidoPat(),
                usuario.getCorreo()
                
            });
        }
        jTable1.setModel(modelo);
        
        usuarios = userService.recuperaUsuarios();
        filtrarUsuarios("");
        
        Buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt){
                filtrarUsuarios(Buscar.getText());
            }   
        });
    }
    
    private void filtrarUsuarios(String texto){
        DefaultTableModel modelo= (DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0);
        
        texto = texto.toLowerCase();
        
        for(Usuario usuario: usuarios){
            String id = String.valueOf(usuario.getidUsuario());
            String nombre = usuario.getNombre().toLowerCase();
            String apellido = usuario.getApellidoPat().toLowerCase();
            String correo = usuario.getCorreo().toLowerCase();
            
            if(id.contains(texto) || nombre.contains(texto) || apellido.contains(texto) || correo.contains(texto)){
                modelo.addRow(new Object[]{
                    usuario.getidUsuario(),
                    usuario.getNombre(),
                    usuario.getApellidoPat(),
                    usuario.getCorreo()
                });
            }
            
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        Regresar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        Generar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        Buscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        Motivo = new javax.swing.JList<>();
        jLabel4 = new javax.swing.JLabel();
        Monto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Descripcion = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Generar Multas");

        Regresar.setText("Volver");
        Regresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegresarActionPerformed(evt);
            }
        });

        jLabel2.setText("Descripción:");

        Generar.setText("Generar");
        Generar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenerarActionPerformed(evt);
            }
        });

        jLabel3.setText("Buscar usuario:");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        Motivo.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Atraso", "Daño Libro", "Daño Mobiliario", "Conducta" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(Motivo);

        jLabel4.setText("Monto:");

        Monto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MontoActionPerformed(evt);
            }
        });

        jLabel5.setText("Tipo:");

        Descripcion.setColumns(20);
        Descripcion.setRows(5);
        jScrollPane3.setViewportView(Descripcion);

        jLabel6.setText("Doble clic en ID para seleccionar usuario");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Buscar))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(196, 196, 196)
                                .addComponent(Regresar))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(269, 269, 269)
                        .addComponent(Generar))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Monto, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(72, 72, 72))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jScrollPane2))))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(Regresar))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Monto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(Generar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerarActionPerformed
        int fila = jTable1.getEditingRow();
        if(fila<0){
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, selecciona un usuario para generar multa");
            return;
        }
        
        Long idUsuario = (Long) jTable1.getValueAt(fila, 0);
        
        Usuario usuarioSeleccionado = null;
        for(Usuario u :usuarios){
            if(u.getidUsuario() ==idUsuario){
                usuarioSeleccionado = u;
                break;
            }
        }
        if(usuarioSeleccionado == null){
            javax.swing.JOptionPane.showMessageDialog(this, "No se encontro el usuario");
            return;
        }
        
        String userId = String.valueOf(usuarioSeleccionado.getidUsuario());
        String userNombre = usuarioSeleccionado.getNombre();
        String userApellidoPat =usuarioSeleccionado.getApellidoPat();
        String userApellidoMat =usuarioSeleccionado.getApellidoMat();
        String userCorreo = usuarioSeleccionado.getCorreo();
        
        String motivo = Motivo.getSelectedValue();
        String descripcion = Descripcion.getText();
        String monto = Monto.getText();
        
        Document document = new Document();
        try {
            String baseName = "Multa-" + userId;
            String fileName = baseName + ".pdf";
            File file = new File(fileName);
            int contador = 1;
            while (file.exists()){
                fileName = baseName + "_"+ contador + ".pdf";
                file = new File(fileName);
                contador++; 
            }
            
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            
            document.add(new Paragraph("Multa generada para el usuario: "));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("ID: "+ userId));
            document.add(new Paragraph("Nombre: "+ userNombre));
            document.add(new Paragraph("Apellido Paterno: " + userApellidoPat));
            document.add(new Paragraph("Apellido Materno: "+ userApellidoMat));
            document.add(new Paragraph("Correo: "+ userCorreo));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Motivo: " + motivo+"\n"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Descripción: " + descripcion+"\n"));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Monto : $" + monto));
            
            javax.swing.JOptionPane.showMessageDialog(this, "PDF generado exitosamente: "+ fileName);
        } catch(Exception e){
            javax.swing.JOptionPane.showMessageDialog(this, "Error al generar PDF: " + e.getMessage());
            e.printStackTrace();
        } finally{
            document.close();
        }
        
    }//GEN-LAST:event_GenerarActionPerformed

    private void MontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MontoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MontoActionPerformed

    private void RegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegresarActionPerformed
        MenuGestion menG =context.getBean(MenuGestion.class);
        menG.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_RegresarActionPerformed

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
            java.util.logging.Logger.getLogger(GenerarMulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GenerarMulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GenerarMulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GenerarMulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GenerarMulta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Buscar;
    private javax.swing.JTextArea Descripcion;
    private javax.swing.JButton Generar;
    private javax.swing.JTextField Monto;
    private javax.swing.JList<String> Motivo;
    private javax.swing.JButton Regresar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
