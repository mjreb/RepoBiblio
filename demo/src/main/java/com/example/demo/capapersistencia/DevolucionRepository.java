/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.capapersistencia;

import com.example.demo.capanegocio.modelo.Devolucion;
import com.example.demo.capanegocio.modelo.Prestamo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

@Repository
public interface DevolucionRepository extends JpaRepository<Devolucion, Long> {

    public boolean existsByPrestamo(Prestamo prestamo);

    public Object findByIdDevolucion(int idDevolucion);
    
    
}
