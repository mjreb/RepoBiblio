/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.demo.capapersistencia;

import com.example.demo.capanegocio.modelo.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {
    
    List<Prestamo> findByFechaDevolucionIsNullAndFechaLimiteBefore(LocalDate fecha);
    
    List<Prestamo> findByMultaPagadaFalseAndFechaDevolucionIsNotNullAndMultaAcumuladaGreaterThan(double cantidad);
    
    List<Prestamo> findByUsuarioIdUsuario(long idUsuario);
    
    ArrayList<Prestamo> findByFechaPrestamoGreaterThanEqual(LocalDate fecha);

    public Object findByIdPrestamo(int idPrestamo);

    public List<Prestamo> findByMultaAcumuladaGreaterThanAndMultaPagadaFalse(double d);
}