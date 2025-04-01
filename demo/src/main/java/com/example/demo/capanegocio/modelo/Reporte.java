/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.capanegocio.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 *
 * @author 100077515
 */
@Entity
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdReporte;
    
    @Column (nullable = false, length = 250)
    private String tipo;
    
    @Column (nullable = false, length = 250)
    private String descripcion;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "idPrestamo", nullable = false)
    private Prestamo prestamo;
    
    @ManyToOne
    @JoinColumn(name = "idDevolucion", nullable = false)
    private Devolucion devolucion;

    public int getIdReporte() {
        return IdReporte;
    }

    public void setIdReporte(int IdReporte) {
        this.IdReporte = IdReporte;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipo = tipoReporte;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public Devolucion getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(Devolucion devolucion) {
        this.devolucion = devolucion;
    }

    @Override
    public String toString() {
        return "Reporte{" + "IdReporte=" + IdReporte + ", tipoReporte=" + tipo + ", descripcion=" + descripcion + ", usuario=" + usuario + ", prestamo=" + prestamo + ", devolucion=" + devolucion + '}';
    }
    
    
    
    
}
