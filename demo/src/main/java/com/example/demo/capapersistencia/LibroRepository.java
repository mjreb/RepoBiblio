/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.demo.capapersistencia;

import com.example.demo.capanegocio.modelo.Libro;
import com.example.demo.capanegocio.modelo.Sucursal;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 *
 * @author vsfs2
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer>{
    public Libro findByTitulo(String titulo); 
    //public List<Libro> findByAutor(String autor);
    //public List<Libro> findBySucursal_IdSucursal(int sucursalId);
    //public Libro findById(int id); 
}
