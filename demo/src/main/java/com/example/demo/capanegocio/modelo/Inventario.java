/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.capanegocio.modelo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vsfs2
 */

@Entity
public class Inventario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idInventario;
    
    @OneToOne // Una sucursal tiene un inventario
    @JoinColumn(name = "id_sucursal", nullable = false, unique = true)
    private Sucursal sucursal;
    
    // Un inventario puede tener muchos inventarios de sucursales
    @OneToMany(mappedBy = "inventario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemInventario> libros = new ArrayList<>();

    public Inventario() {
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public List<ItemInventario> getLibros() {
        return libros;
    }

    public void setLibros(List<ItemInventario> libros) {
        this.libros = libros;
    }
    
    
    
}
