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
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 *
 * @author Jose Carlos
 */
@ExtendWith(MockitoExtension.class)
public class RecuperaPrestamosPorUsuarioTest {
    
    @Mock
    private PrestamoRepository prestamoRepository;
    
    @InjectMocks
    private PrestamoService prestamoService;
    
    private List<Prestamo> prestamosDePrueba;
    
    @BeforeEach
    void setUp() {
        // Configurar datos de prueba comunes para varios tests
        Prestamo prestamo1 = new Prestamo();
        prestamo1.setIdPrestamo(1);
        prestamo1.setFechaDevolucion(null); // No devuelto
        prestamo1.setMultaAcumulada(0.0);

        Prestamo prestamo2 = new Prestamo();
        prestamo2.setIdPrestamo(2);
        prestamo2.setFechaDevolucion(LocalDate.now()); // Devuelto
        prestamo2.setMultaAcumulada(10.0); // Con multa

        Prestamo prestamo3 = new Prestamo();
        prestamo3.setIdPrestamo(3);
        prestamo3.setFechaDevolucion(null); // No devuelto
        prestamo3.setMultaAcumulada(15.0); // Con multa

        Prestamo prestamo4 = new Prestamo();
        prestamo4.setIdPrestamo(4);
        prestamo4.setFechaDevolucion(LocalDate.now()); // Devuelto
        prestamo4.setMultaAcumulada(0.0); // Sin multa

        prestamosDePrueba = Arrays.asList(prestamo1, prestamo2, prestamo3, prestamo4);
    }
    
    @Test
    public void testRecuperaPrestamosPorUsuario_ConPrestamosNoDevueltosYConMulta() {
        // Configurar el mock
        when(prestamoRepository.findByUsuarioIdUsuario(anyLong()))
            .thenReturn(new ArrayList<>(prestamosDePrueba));
        
        // Ejecutar el método
        ArrayList<Prestamo> resultado = prestamoService.recuperaPrestamosPorUsuario(1L);
        
        // Verificar resultados
        assertEquals(3, resultado.size(), "Debería retornar 3 préstamos");
        assertTrue(contienePrestamoConId(resultado, 1), "Debería incluir préstamo no devuelto");
        assertTrue(contienePrestamoConId(resultado, 2), "Debería incluir préstamo con multa");
        assertTrue(contienePrestamoConId(resultado, 3), "Debería incluir préstamo no devuelto con multa");
        assertFalse(contienePrestamoConId(resultado, 4), "No debería incluir préstamo devuelto sin multa");
    }
    
    @Test
    public void testRecuperaPrestamosPorUsuario_ConTodosPrestamosDevueltosSinMulta() {
        // Configurar datos de prueba
        List<Prestamo> prestamosDevueltos = Arrays.asList(
            crearPrestamo(5, LocalDate.now(), 0.0),
            crearPrestamo(6, LocalDate.now(), 0.0)
        );
        
        // Configurar el mock
        when(prestamoRepository.findByUsuarioIdUsuario(anyLong()))
            .thenReturn(new ArrayList<>(prestamosDevueltos));
        
        // Ejecutar el método
        ArrayList<Prestamo> resultado = prestamoService.recuperaPrestamosPorUsuario(2L);
        
        // Verificar resultados
        assertTrue(resultado.isEmpty(), "Debería retornar lista vacía");
    }
    
    @Test
    public void testRecuperaPrestamosPorUsuario_ConListaVacia() {
        // Configurar el mock
        when(prestamoRepository.findByUsuarioIdUsuario(anyLong()))
            .thenReturn(new ArrayList<>());
        
        // Ejecutar el método
        ArrayList<Prestamo> resultado = prestamoService.recuperaPrestamosPorUsuario(3L);
        
        // Verificar resultados
        assertTrue(resultado.isEmpty(), "Debería retornar lista vacía");
    }
    
    @Test
    public void testRecuperaPrestamosPorUsuario_ConPrestamosNoDevueltos() {
        // Configurar datos de prueba
        List<Prestamo> prestamosNoDevueltos = Arrays.asList(
            crearPrestamo(7, null, 0.0),
            crearPrestamo(8, null, 0.0)
        );
        
        // Configurar el mock
        when(prestamoRepository.findByUsuarioIdUsuario(anyLong()))
            .thenReturn(new ArrayList<>(prestamosNoDevueltos));
        
        // Ejecutar el método
        ArrayList<Prestamo> resultado = prestamoService.recuperaPrestamosPorUsuario(4L);
        
        // Verificar resultados
        assertEquals(2, resultado.size(), "Debería retornar 2 préstamos");
        assertTrue(resultado.stream().allMatch(p -> p.getFechaDevolucion() == null), 
                 "Todos deberían ser préstamos no devueltos");
    }
    
    @Test
    public void testRecuperaPrestamosPorUsuario_ConPrestamosConMulta() {
        // Configurar datos de prueba
        List<Prestamo> prestamosConMulta = Arrays.asList(
            crearPrestamo(9, LocalDate.now(), 10.0),
            crearPrestamo(10, null, 15.0)
        );
        
        // Configurar el mock
        when(prestamoRepository.findByUsuarioIdUsuario(anyLong()))
            .thenReturn(new ArrayList<>(prestamosConMulta));
        
        // Ejecutar el método
        ArrayList<Prestamo> resultado = prestamoService.recuperaPrestamosPorUsuario(5L);
        
        // Verificar resultados
        assertEquals(2, resultado.size(), "Debería retornar 2 préstamos");
        assertTrue(resultado.stream().allMatch(p -> p.getMultaAcumulada() > 0 || p.getFechaDevolucion() == null), 
                 "Todos deberían tener multa o no estar devueltos");
    }
    
    // Método auxiliar para crear préstamos de prueba
    private static Prestamo crearPrestamo(int id, LocalDate fechaDevolucion, double multa) {
        Prestamo p = new Prestamo();
        p.setIdPrestamo(id);
        p.setFechaDevolucion(fechaDevolucion);
        p.setMultaAcumulada(multa);
        return p;
    }
    
    // Método auxiliar para verificar si un préstamo está en la lista
    private static boolean contienePrestamoConId(List<Prestamo> prestamos, int id) {
        return prestamos.stream().anyMatch(p -> p.getIdPrestamo() == id);
    }
}
