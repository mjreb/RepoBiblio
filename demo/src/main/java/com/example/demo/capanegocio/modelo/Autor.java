package com.example.demo.capanegocio.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
    
    @Entity  // Marcamos que esta clase se va a convertir en una tabla en la BD 
public class Autor {  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // estrategía es cómo quiero que se genere ese id
    @Column(name = "id_autor") 
    private long idAutor;
    private String nombre;
    private String nacionalidad;

    public Autor() {
    }


    public long getidAutor() {
        return idAutor;
    }

    public void setidAutor(long idAutor) {
        this.idAutor = idAutor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }


}
