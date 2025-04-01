/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.capanegocio;

import com.example.demo.capanegocio.modelo.Autor;
import com.example.demo.capapersistencia.AutorRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vsfs2
 */
@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    /**
     * Recupera todos los autores de la base de datos.
     * @return Lista de autores
     */
    public ArrayList<Autor> recuperaAutores() {
        System.out.println("autorRepository = " + autorRepository);
        
     
        ArrayList<Autor> autores = (ArrayList<Autor>) autorRepository.findAll();
    
        
        return autores;
    }

    /**
     * Agrega un nuevo autor a la base de datos
     * @param nombre Nombre del autor
     * @param nacionalidad Nacionalidad del autor
     * @return El autor creado
     * @throws IllegalArgumentException Si ya existe un autor con el mismo nombre
     */
    public Autor agregaAutor(String nombre, String nacionalidad) {
        // Verificamos si ya existe el autor
        Autor autorExistente = autorRepository.findByNombre(nombre);
        
        if(autorExistente != null) {
            throw new IllegalArgumentException("Un autor con este nombre ya existe");
        }
        
        // Creamos el nuevo autor
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setNacionalidad(nacionalidad);
        
        // Guardamos en la base de datos
        autorRepository.save(autor);
        
        return autor;
    }
}