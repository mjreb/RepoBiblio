/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.demo.capapersistencia;

import com.example.demo.capanegocio.modelo.Autor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author vsfs2
 */
@Repository
public interface AutorRepository extends CrudRepository <Autor, Long> {
    public Autor findByNombre(String nombre);  
}
