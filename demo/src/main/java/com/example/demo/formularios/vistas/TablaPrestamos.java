package com.example.demo.formularios.vistas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import CapaPersistencia.ConexionDB;

/**
 *
 * @author 03_06_2023
 */


public class TablaPrestamos extends JFrame {
    private JTextField campoBusqueda;
    private JTable tablaPrestamos;
    private JButton btnBuscar;
    private DefaultTableModel modeloPrestamos;

    public TablaPrestamos() {
        setTitle("Tabla de Préstamos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        inicializarComponentes();
        setVisible(true); 
    }

    private void inicializarComponentes() {
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        campoBusqueda = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        panelBusqueda.add(new JLabel("ID prestamo: "));
        panelBusqueda.add(campoBusqueda);
        panelBusqueda.add(btnBuscar);

        String[] columnas = {"ID préstamo", "ID usuario", "ID libro", "Fecha préstamo", "Fecha límite"};
        modeloPrestamos = new DefaultTableModel(columnas, 0);
        tablaPrestamos = new JTable(modeloPrestamos);
        JScrollPane scrollPane = new JScrollPane(tablaPrestamos); // Para desplazamiento

        setLayout(new BorderLayout());
        add(panelBusqueda, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> buscarPrestamos(campoBusqueda.getText()));
    }

    private void buscarPrestamos(String consulta) {
        if (consulta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un ID de usuario.");
            return;
        }

        modeloPrestamos.setRowCount(0); 

        /*Connection conexion = new ConexionDB().obtenerConexion();
        if (conexion != null) {
            try {
                String sql = "SELECT id_prestamo, id_usuario, id_libro, fecha_prestamo, fecha_limite FROM Prestamo WHERE id_usuario = ?";
                PreparedStatement pst = conexion.prepareStatement(sql);
                pst.setString(1, consulta); 
                ResultSet rs = pst.executeQuery();

                if (!rs.isBeforeFirst()) { 
                    JOptionPane.showMessageDialog(this, "No se encontraron préstamos para este ID de usuario.");
                }

                while (rs.next()) {
                    modeloPrestamos.addRow(new Object[]{
                        rs.getInt("id_prestamo"),
                        rs.getInt("id_usuario"),
                        rs.getInt("id_libro"),
                        rs.getString("fecha_prestamo"),
                        rs.getString("fecha_limite")
                    });
                }

                rs.close();
                pst.close();
                conexion.close();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al obtener datos: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.");
        }*/
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TablaPrestamos::new);
    }
}
