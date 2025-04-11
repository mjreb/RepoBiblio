package com.example.demo.capanegocio.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPrestamo;

    @ManyToOne
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDate fechaPrestamo;

    @Column(nullable = false)
    private LocalDate fechaLimite;

    @Column
    private boolean multaPagada = false;

    @Column
    private LocalDate fechaDevolucion;

    @Column
    private double multaAcumulada = 0.0;
    
    @Column
    private String nombreSucursal;
    

    // Getters y setters
    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public boolean isMultaPagada() {
        return multaPagada;
    }

    public void setMultaPagada(boolean multaPagada) {
        this.multaPagada = multaPagada;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public double getMultaAcumulada() {
        return multaAcumulada;
    }

    public void setMultaAcumulada(Double multaAcumulada) {
        this.multaAcumulada = multaAcumulada;
    }

    // Método para calcular la multa actualizada
    public double calcularMultaActual() {
        if (multaPagada || LocalDate.now().isBefore(fechaLimite) || fechaDevolucion != null) {
            return 0.0;
        }
        
        long diasRetraso = ChronoUnit.DAYS.between(fechaLimite, LocalDate.now());
        this.multaAcumulada = diasRetraso * 5.0; // $5 por día de retraso
        return this.multaAcumulada;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }
    

    @Override
    public String toString() {
        return "Prestamo:" + "\n"+
               "  ID Prestamo: " + idPrestamo + "\n" +
               "  Libro: " + (libro != null ? libro.getTitulo() : "N/A") + "\n" +
               "  Usuario: " + (usuario != null ? usuario.getNombre() : "N/A") + "\n" +
               "  Fecha de prestamo: " + fechaPrestamo + "\n" +
               "  Fecha limite: " + fechaLimite + "\n" +
               "  Fecha devolución: " + (fechaDevolucion != null ? fechaDevolucion : "Pendiente") + "\n" +
               "  Multa acumulada: $" + multaAcumulada + "\n" +
               "  Multa pagada: " + (multaPagada ? "Sí" : "No")+"\n" +
                "En sucursal: " + nombreSucursal; 
    }
}