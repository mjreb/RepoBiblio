/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.capanegocio.modelo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

/**
 *
 * @author 100077515
 */
@Entity
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSucursal;

    @Column(unique = true, nullable = false, length = 250)
    private String nombre;

    @Column(nullable = false, length = 250)
    private String direccion;
    
    private String horario;
    private String linkUbicacion; 
    
     
    // Una sucursal solo puede tener un inventario
    @OneToOne(mappedBy = "sucursal", cascade = CascadeType.ALL, orphanRemoval = true)
    private Inventario inventario;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario administrador;

    public Sucursal() {
    }
    


    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getLinkUbicacion() {
        return linkUbicacion;
    }

    public void setLinkUbicacion(String linkUbicacion) {
        this.linkUbicacion = linkUbicacion;
    }

    public Inventario getInventario() {
        return inventario;
    }

    //Relacion bidireccional 
    public void setInventario(Inventario inventario) {
        this.inventario = inventario; // 1. Establece el inventario en la sucursal
        inventario.setSucursal(this); //  2. Establece la sucursal en el inventario

    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Usuario getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Usuario administrador) {
        this.administrador = administrador;
    }

    @Override

public String toString() {
    return "Sucursal: " +
           "id=" + idSucursal +
           ", nombre='" + nombre + '\'' +
           ", direccion='" + direccion + '\'' +
            "Link:  " + linkUbicacion
           ;
}

    
}
