/*
 * Pruebas unitarias para el método recuperaPrestamosPorUsuario() de PrestamoService.
 * Verifica el filtrado de préstamos por estado (devueltos/no devueltos) y multas pendientes.
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas de Recuperación de Préstamos por Usuario")
public class RecuperaPrestamosPorUsuarioTest {
    
    @Mock
    private PrestamoRepository prestamoRepository;
    
    @InjectMocks
    private PrestamoService prestamoService;
    
    private List<Prestamo> prestamosMuestra;
    
    /**
     * Configura datos de prueba antes de cada test.
     * Crea 4 préstamos con diferentes combinaciones de:
     * - Estado de devolución (null = no devuelto)
     * - Multas pendientes (>0 = tiene multa)
     */
    @BeforeEach
    void configurarDatosPrueba() {
        // Préstamo 1: No devuelto, sin multa
        Prestamo prestamo1 = new Prestamo();
        prestamo1.setIdPrestamo(1);
        prestamo1.setFechaDevolucion(null);
        prestamo1.setMultaAcumulada(0.0);

        // Préstamo 2: Devuelto, con multa
        Prestamo prestamo2 = new Prestamo();
        prestamo2.setIdPrestamo(2);
        prestamo2.setFechaDevolucion(LocalDate.now());
        prestamo2.setMultaAcumulada(10.0);

        // Préstamo 3: No devuelto, con multa
        Prestamo prestamo3 = new Prestamo();
        prestamo3.setIdPrestamo(3);
        prestamo3.setFechaDevolucion(null);
        prestamo3.setMultaAcumulada(15.0);

        // Préstamo 4: Devuelto, sin multa
        Prestamo prestamo4 = new Prestamo();
        prestamo4.setIdPrestamo(4);
        prestamo4.setFechaDevolucion(LocalDate.now());
        prestamo4.setMultaAcumulada(0.0);

        prestamosMuestra = Arrays.asList(prestamo1, prestamo2, prestamo3, prestamo4);
    }
    
    /**
     * Prueba el filtrado cuando hay préstamos no devueltos y con multas.
     * Resultado esperado: 3 préstamos (IDs 1, 2, 3).
     */
    @Test
    @DisplayName("Debería filtrar préstamos no devueltos y con multas")
    void deberiaFiltrarPrestamosNoDevueltosYConMultas() {
        when(prestamoRepository.findByUsuarioIdUsuario(anyLong()))
            .thenReturn(new ArrayList<>(prestamosMuestra));
        
        ArrayList<Prestamo> resultado = prestamoService.recuperaPrestamosPorUsuario(1L);
        
        assertEquals(3, resultado.size(), "Debería retornar 3 préstamos (no devueltos o con multa)");
        assertTrue(contienePrestamoConId(resultado, 1), "Debería incluir préstamo no devuelto sin multa");
        assertTrue(contienePrestamoConId(resultado, 2), "Debería incluir préstamo devuelto con multa");
        assertTrue(contienePrestamoConId(resultado, 3), "Debería incluir préstamo no devuelto con multa");
        assertFalse(contienePrestamoConId(resultado, 4), "No debería incluir préstamo devuelto sin multa");
    }
    
    /**
     * Prueba cuando todos los préstamos están devueltos y sin multas.
     * Resultado esperado: Lista vacía.
     */
    @Test
    @DisplayName("Debería retornar lista vacía cuando todos los préstamos están devueltos sin multas")
    void deberiaRetornarListaVaciaParaPrestamosDevueltosSinMultas() {
        List<Prestamo> prestamosDevueltos = Arrays.asList(
            crearPrestamoPrueba(5, LocalDate.now(), 0.0),
            crearPrestamoPrueba(6, LocalDate.now(), 0.0)
        );
        
        when(prestamoRepository.findByUsuarioIdUsuario(anyLong()))
            .thenReturn(new ArrayList<>(prestamosDevueltos));
        
        ArrayList<Prestamo> resultado = prestamoService.recuperaPrestamosPorUsuario(2L);
        
        assertTrue(resultado.isEmpty(), "Debería retornar lista vacía para préstamos devueltos sin multas");
    }
    
    /**
     * Prueba cuando el usuario no tiene préstamos.
     * Resultado esperado: Lista vacía.
     */
    @Test
    @DisplayName("Debería retornar lista vacía cuando no hay préstamos")
    void deberiaRetornarListaVaciaParaUsuarioSinPrestamos() {
        when(prestamoRepository.findByUsuarioIdUsuario(anyLong()))
            .thenReturn(new ArrayList<>());
        
        ArrayList<Prestamo> resultado = prestamoService.recuperaPrestamosPorUsuario(3L);
        
        assertTrue(resultado.isEmpty(), "Debería retornar lista vacía cuando no hay préstamos");
    }
    
    /**
     * Prueba cuando todos los préstamos están no devueltos pero sin multas.
     * Resultado esperado: 2 préstamos (ambos no devueltos).
     */
    @Test
    @DisplayName("Debería retornar préstamos no devueltos sin multas")
    void deberiaRetornarPrestamosNoDevueltosSinMultas() {
        List<Prestamo> prestamosNoDevueltos = Arrays.asList(
            crearPrestamoPrueba(7, null, 0.0),
            crearPrestamoPrueba(8, null, 0.0)
        );
        
        when(prestamoRepository.findByUsuarioIdUsuario(anyLong()))
            .thenReturn(new ArrayList<>(prestamosNoDevueltos));
        
        ArrayList<Prestamo> resultado = prestamoService.recuperaPrestamosPorUsuario(4L);
        
        assertEquals(2, resultado.size(), "Debería retornar todos los préstamos no devueltos");
        assertTrue(resultado.stream().allMatch(p -> p.getFechaDevolucion() == null), 
                 "Todos los préstamos devueltos deberían estar no devueltos");
    }
    
    /**
     * Prueba cuando hay préstamos con multas pendientes.
     * Resultado esperado: 2 préstamos (ambos con multas > 0).
     */
    @Test
    @DisplayName("Debería retornar préstamos con multas pendientes")
    void deberiaRetornarPrestamosConMultasPendientes() {
        List<Prestamo> prestamosConMulta = Arrays.asList(
            crearPrestamoPrueba(9, LocalDate.now(), 10.0),
            crearPrestamoPrueba(10, null, 15.0)
        );
        
        when(prestamoRepository.findByUsuarioIdUsuario(anyLong()))
            .thenReturn(new ArrayList<>(prestamosConMulta));
        
        ArrayList<Prestamo> resultado = prestamoService.recuperaPrestamosPorUsuario(5L);
        
        assertEquals(2, resultado.size(), "Debería retornar todos los préstamos con multas");
        assertTrue(resultado.stream().allMatch(p -> p.getMultaAcumulada() > 0), 
                 "Todos los préstamos devueltos deberían tener multas pendientes");
    }
    
    // --------------------------
    // Métodos auxiliares
    // --------------------------
    
    /**
     * Crea un préstamo de prueba con parámetros específicos.
     * @param id Identificador del préstamo
     * @param fechaDevolucion Fecha de devolución (null si no devuelto)
     * @param multa Monto de multa pendiente (0 si no tiene)
     * @return Objeto Prestamo configurado
     */
    private static Prestamo crearPrestamoPrueba(int id, LocalDate fechaDevolucion, double multa) {
        Prestamo p = new Prestamo();
        p.setIdPrestamo(id);
        p.setFechaDevolucion(fechaDevolucion);
        p.setMultaAcumulada(multa);
        return p;
    }
    
    /**
     * Verifica si un préstamo con cierto ID existe en una lista.
     * @param prestamos Lista de préstamos a verificar
     * @param id ID del préstamo a buscar
     * @return true si encuentra el préstamo, false si no
     */
    private static boolean contienePrestamoConId(List<Prestamo> prestamos, int id) {
        return prestamos.stream().anyMatch(p -> p.getIdPrestamo() == id);
    }
}
