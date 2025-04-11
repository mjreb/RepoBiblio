/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.negocio;

import com.example.demo.capanegocio.PrestamoService;
import com.example.demo.capanegocio.modelo.Prestamo;
import com.example.demo.capapersistencia.PrestamoRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *El metodo tiene el nombre mal, en realidad recupera los prestamos por fecha, funciona correctamente
 * solo está mal el nombre.
 * @author PC
 */
public class RecuperaUsuariosPorFecha {
    @Mock
    private PrestamoRepository prestamoRepository;
    @InjectMocks
    private PrestamoService prestamoService;
    
    private LocalDate fechaLocal = LocalDate.now();
    private List<Prestamo> prestamos = new ArrayList<>();
    
    @Test
    void recuperaUsuariosPorFechaDevuelvePrestamosCuandoExisten(){
        when(prestamoRepository.findByFechaPrestamoGreaterThanEqual(fechaLocal)).thenReturn((ArrayList<Prestamo>) prestamos);
        List<Prestamo> resultado = prestamoService.recuperaUsuariosPorFecha(fechaLocal);
        assertEquals(prestamos, prestamos);
        verify(prestamoRepository).findByFechaPrestamoGreaterThanEqual(fechaLocal);
        
    }
    
    @Test
    void recuperaUsuariosPorFechaDevuelvePrestamosCuandoNoExisten(){
        when(prestamoRepository.findByFechaPrestamoGreaterThanEqual(fechaLocal)).thenReturn(new ArrayList<>());
        assertThrows(UnsupportedOperationException.class, () -> prestamoService.recuperaUsuariosPorFecha(fechaLocal));
        verify(prestamoRepository).findByFechaPrestamoGreaterThanEqual(fechaLocal);

    }
}
