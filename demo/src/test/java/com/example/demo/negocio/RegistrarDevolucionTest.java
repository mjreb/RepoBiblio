
package com.example.demo.negocio;

import com.example.demo.capanegocio.PrestamoService;
import com.example.demo.capanegocio.UserService;
import com.example.demo.capanegocio.modelo.Libro;
import com.example.demo.capanegocio.modelo.Prestamo;
import com.example.demo.capanegocio.modelo.Sucursal;
import com.example.demo.capanegocio.modelo.Usuario;
import com.example.demo.capapersistencia.LibroRepository;
import com.example.demo.capapersistencia.PrestamoRepository;
import com.example.demo.capapersistencia.SucursalRepository;
import com.example.demo.capapersistencia.UsuarioRepository;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Diegogo
 */

public class RegistrarDevolucionTest {
    
    @Mock
    private UserService usuarioService;
    
    @Mock
    private PrestamoRepository prestamoRepository;
    
    @Mock
    private LibroRepository libroRepository;
    
    @Mock
    private UsuarioRepository usuarioRepository;
    
    @Mock
    private SucursalRepository sucursalRepository;
    
    @InjectMocks
    private PrestamoService prestamoService;
    
    
    /**
     * Configuración inicial para cada prueba.
     * Inicializa los mocks necesarios. Para cada @Test, se ejecuta el @BeforeEach
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    /*
     *Prueba que verifica que se lance una excepcion cuando el usuario no existe al registrar una devolucion
     *
     *Se configura una busqueda de usuario que retorna vacio (usuario no existente)
     *se espera una excepción IllegalArgumentException con el mensaje correspondiente
     */
    @Test
    void registrarDevolucionUsuarioInvalido() {
        
        Prestamo prestamo = new Prestamo();
        prestamo.setIdPrestamo(19);
        when(prestamoRepository.findById(19)).thenReturn(Optional.of(prestamo));
        
        Usuario usuario = new Usuario();
        usuario.setidUsuario(20L);
        
        prestamo.setUsuario(usuario);
        
        when(usuarioRepository.findById(20L)).thenReturn(Optional.of(usuario));
        
        when(prestamoRepository.save(any(Prestamo.class))).thenReturn(prestamo);
        
        prestamo.setFechaLimite(LocalDate.now().plusDays(10));
        
        IllegalArgumentException exception= assertThrows(IllegalArgumentException.class, 
            () -> prestamoService.registrarDevolucion(19,21L));
        
    }        
    
    /**
     * Prueba que verifica que se lance una excepcion cuando el préstamo no exsite al registrar una devolucion
     * 
     *Se configura un usuario valido pero el prestamo no se encuentra en la base de datos
     *Se espera que se lance una excepción IllegalArgumentException con el mensaje correspondiente
    */
    @Test
    void registrarDevolucionPrestamoInvalido() {
        
        Prestamo prestamo = new Prestamo();
        prestamo.setIdPrestamo(19);
        when(prestamoRepository.findById(19)).thenReturn(Optional.of(prestamo));
        
        Usuario usuario = new Usuario();
        usuario.setidUsuario(20L);
        
        prestamo.setUsuario(usuario);
        
        when(usuarioRepository.findById(20L)).thenReturn(Optional.of(usuario));
        
        when(prestamoRepository.save(any(Prestamo.class))).thenReturn(prestamo);
        
        prestamo.setFechaLimite(LocalDate.now().plusDays(10));
        
        IllegalArgumentException exception= assertThrows(IllegalArgumentException.class, 
            () -> prestamoService.registrarDevolucion(20,20L));
        
    }
    
    /**
     * Prueba que verifica que se registre correctamente la devolución de un préstamo válido.
     * 
     * Se configura un usuario existente y un préstamo válido sin fecha de devolución.
     * Se espera que el método no arroje ninguna excepción.
     */
    @Test
    void devolucionValida(){
        Libro libro=new Libro();
        
        Usuario usuario=new Usuario();
        usuario.setidUsuario(10L);
        
        Sucursal sucursal=new Sucursal();
        sucursal.setIdSucursal(3);
        sucursal.setNombre("Sucursal_1");
        
        Prestamo prestamo=new Prestamo();
        prestamo.setLibro(libro);
        prestamo.setUsuario(usuario);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaLimite(LocalDate.now().plusDays(5));
        prestamo.setMultaAcumulada(0);
        prestamo.setMultaPagada(false);
        prestamo.setFechaDevolucion(null);
        prestamo.setNombreSucursal("Sucursal_1");
        
        when(sucursalRepository.findByNombre("Sucursal_1")).thenReturn(sucursal);
        
        when(usuarioRepository.findById(10L)).thenReturn(Optional.of(usuario));
        
        when(libroRepository.findById(5)).thenReturn(Optional.of(libro));
        
        when(prestamoRepository.save(any(Prestamo.class))).thenReturn(prestamo);
        
        assertDoesNotThrow(() -> prestamoService.creaPrestamo(5, 10L, "Sucursal_1"));
        
    }
    
}
