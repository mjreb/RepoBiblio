/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.negocio;

import com.example.demo.capanegocio.PrestamoService;
import com.example.demo.capanegocio.modelo.Autor;
import com.example.demo.capanegocio.modelo.Prestamo;
import com.example.demo.capanegocio.modelo.Sucursal;
import com.example.demo.capanegocio.modelo.Usuario;
import com.example.demo.capapersistencia.PrestamoRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *El metodo tiene el nombre mal, en realidad recupera los prestamos por fecha, funciona correctamente
 * solo está mal el nombre.
 * @author PC
 */
@ExtendWith(MockitoExtension.class)
public class RecuperaUsuariosPorFecha {
    @Mock
    private PrestamoRepository prestamoRepository;
    @Mock
    private Prestamo prestamo;
    @Mock 
    private Usuario usuario;
    @Mock
    private Autor autor;
    @Mock
    private Sucursal sucursal;
    
    
    
    @InjectMocks
    private PrestamoService prestamoService;
    
    private LocalDate fechaLocal = LocalDate.now();
    private List<Prestamo> prestamos = new ArrayList<>();
   
    
    @Test
    void recuperaUsuariosPorFechaDevuelvePrestamosCuandoExisten(){
        // Crear un préstamo simulado
        Prestamo prestamo1 = new Prestamo();
        List<Prestamo> prestamos = new ArrayList<>();
        prestamos.add(prestamo1);

        // Configurar el mock del repositorio
        when(prestamoRepository.findByFechaPrestamoGreaterThanEqual(fechaLocal)).thenReturn((ArrayList<Prestamo>) prestamos);

        // Llamar al método del servicio
        List<Prestamo> resultado = prestamoService.recuperaUsuariosPorFecha(fechaLocal);

        // Verificar el resultado
        assertEquals(prestamos, resultado);

        // Verificar la interacción con el repositorio
        verify(prestamoRepository).findByFechaPrestamoGreaterThanEqual(fechaLocal);
    
    }
    
    @Test
    void recuperaUsuariosPorFechaDevuelvePrestamosCuandoNoExisten(){
        // Configurar el mock del repositorio para devolver una lista vacía
        when(prestamoRepository.findByFechaPrestamoGreaterThanEqual(fechaLocal)).thenReturn(new ArrayList<>());

        // Llamar al método del servicio
        List<Prestamo> resultado = prestamoService.recuperaUsuariosPorFecha(fechaLocal);

        // Verificar el resultado
        assertEquals(new ArrayList<>(), resultado);

        // Verificar la interacción con el repositorio
        verify(prestamoRepository).findByFechaPrestamoGreaterThanEqual(fechaLocal);

    }
}
