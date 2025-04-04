/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.capanegocio;

import com.example.demo.capanegocio.modelo.Devolucion;
import com.example.demo.capanegocio.modelo.Prestamo;
import com.example.demo.capapersistencia.DevolucionRepository;
import com.example.demo.capapersistencia.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class DevolucionService {

    @Autowired
    private DevolucionRepository devolucionRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;
    

    /**
     * Registra una devolución y crea el registro correspondiente.
     * Utiliza la información de multa ya calculada en el préstamo.
     *
     * @param idPrestamo ID del préstamo que se está devolviendo.
     * @return La devolución registrada.
     * @throws IllegalStateException Si el préstamo no existe, ya fue devuelto o ya tiene una devolución registrada.
     */
    
    public Devolucion registrarDevolucion(int idPrestamo) {
        Prestamo prestamo = prestamoRepository.findById(idPrestamo)
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));

        // Verificar si ya existe una devolución para este préstamo
        if (devolucionRepository.existsByPrestamo(prestamo)) {
            throw new IllegalStateException("Este préstamo ya tiene una devolución registrada");
        }

        // Crear la devolución
        Devolucion devolucion = new Devolucion();
        devolucion.setPrestamo(prestamo);
        devolucion.setUsuario(prestamo.getUsuario());
        devolucion.setFechaDevolucion(prestamo.getFechaDevolucion());
        devolucion.setMulta(prestamo.getMultaAcumulada());

        // Guardar la devolución
        return devolucionRepository.save(devolucion);
    }

    /**
     * Registra el pago de una multa y actualiza el estado en el préstamo asociado.
     *
     * @param idDevolucion ID de la devolución.
     * @return La devolución actualizada.
     * @throws IllegalStateException Si la devolución no existe o ya está pagada.
     */
    

}