/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.negocio;

import com.example.demo.capanegocio.DevolucionService;
import com.example.demo.capanegocio.ItemInventarioService;
import com.example.demo.capanegocio.PrestamoService;
import com.example.demo.capanegocio.SucursalSevice;
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
import java.util.ArrayList;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.doNothing;



public class RevisarCondicionesTest {
    
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
    
    @Mock
    private ItemInventarioService itemInventarioService;
    
    @InjectMocks
    private PrestamoService prestamoService;
    
    @Mock
    private SucursalSevice sucursalService;
    
    @Mock
    private DevolucionService devolucionService;
    
    
    /**
     * Configuración inicial para cada prueba.
     * Inicializa los mocks necesarios. Para cada @Test, se ejecuta el @BeforeEach
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    
    /**
     * Prueba que verifica que se lance la excepción correcta cuando el usuario no existe.
     * 
     * Se espera que cuando se solicite un préstamo para un usuario que no existe,
     * el sistema lance una excepción IllegalStateException con el mensaje "Usuario no existe".
     */
 
    @Test
    void revisaCondicionesUsuarioNoExisteLanzaExcepcion() {
        when(usuarioService.obtenerUsuarioPorId(10L)).thenReturn(null);

        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            () -> prestamoService.revisaCondiciones(10L));

        assertEquals("Usuario no exste", exception.getMessage());
    }
    
    /**
     * Prueba que verifica que se lance una excepción cuando el usuario no tiene permisos.
     * 
     * Se configura un usuario sin permisos de préstamo y se verifica que el sistema
     * lance una excepción IllegalStateException con el mensaje "Usuario no tiene permisos de prestamo".
     */
    
      @Test
    void usuarioSinPermisosLanzaExcepcion(){
        
        Usuario usuario = new Usuario(); 
        usuario.setApellidoMat("Garcia");
        usuario.setApellidoPat("Gomez");
        usuario.setContrasena("1234");
        usuario.setCorreo("juanito@gmail.com");
        usuario.setNombre("Juan");
        usuario.setPermisosPrestamo(false); // Usuario SIN permisos
        usuario.setidUsuario(10L);
    
        // Configurar los mocks
        when(usuarioService.obtenerUsuarioPorId(10L)).thenReturn(usuario);
        when(usuarioService.obtenerPermisoUsuario(usuario)).thenReturn(false);

        // 2. Ejecución y Verificación (When/Then)
        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            () -> prestamoService.revisaCondiciones(10L));
 
        assertEquals("Usuario no tiene permisos de prestamo", exception.getMessage());
    }
    
    /**
     * Prueba que verifica que se lance una excepción cuando el usuario alcanza el máximo de préstamos.
     * 
     * Se configura un usuario con el número máximo de préstamos activos (2) y se verifica
     * que el sistema lance la excepción correspondiente.
     */
    
        @Test
    void usuarioConPrestamosMaximos(){
        Usuario usuario = new Usuario(); 
        usuario.setidUsuario(10L);
        usuario.setPermisosPrestamo(true); 
        
        Prestamo prestamo1 = new Prestamo();
        prestamo1.setUsuario(usuario);
        prestamo1.setFechaDevolucion(null);
        Prestamo prestamo2 = new Prestamo();
        prestamo2.setUsuario(usuario);
        prestamo2.setFechaDevolucion(null);
        
        ArrayList<Prestamo> prestamos = new ArrayList();
        prestamos.add(prestamo1);
        prestamos.add(prestamo2);
               
    
        when(usuarioService.obtenerUsuarioPorId(10L)).thenReturn(usuario);
        when(usuarioService.obtenerPermisoUsuario(usuario)).thenReturn(true);
        
        when(prestamoRepository.findByUsuarioIdUsuario(10L)).thenReturn(prestamos);
    
        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            () -> prestamoService.revisaCondiciones(10L));
    
        assertEquals("Número máximo de préstamos alcanzado", exception.getMessage());
    }
    
    /**
     * Prueba que verifica que se lance una excepción cuando el usuario tiene multas pendientes.
     * 
     * Se configura un usuario con préstamos que tienen multas acumuladas y se verifica
     * que el sistema lance la excepción correspondiente.
     */
    
        @Test
    void revisaCondicionesPrestamosConMultas() {
        
        Usuario usuario = new Usuario(); 
        usuario.setidUsuario(10L);
        usuario.setPermisosPrestamo(true); 
        
        
        when(usuarioService.obtenerUsuarioPorId(10L)).thenReturn(usuario);
        when(usuarioService.obtenerPermisoUsuario(usuario)).thenReturn(true);

        ArrayList<Prestamo> prestamos = new ArrayList<>();
        Prestamo prestamoConMulta = new Prestamo();
        prestamoConMulta.setMultaAcumulada(15.0);
        prestamos.add(prestamoConMulta);

        when(prestamoRepository.findByUsuarioIdUsuario(10L)).thenReturn(prestamos);

        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            () -> prestamoService.revisaCondiciones(10L));

        assertEquals("Existen prestamos con multas pendientes", exception.getMessage());
    }
    
    /**
     * Prueba que verifica que no se lance ninguna excepción cuando se cumplen todas las condiciones de préstamo.
     * 
     * Se configura un usuario con todos los requisitos cumplidos y se verifica
     * que el sistema no lance ninguna excepción al revisar las condiciones.
     */
    
      @Test
    void cumpleTodasLasCondiciones(){
        Usuario usuario = new Usuario(); 
        usuario.setidUsuario(10L);
        usuario.setPermisosPrestamo(true); 
        
        when(usuarioService.obtenerUsuarioPorId(10L)).thenReturn(usuario);
        when(usuarioService.obtenerPermisoUsuario(usuario)).thenReturn(true);
       
        Prestamo prestamo1 = new Prestamo();
        prestamo1.setUsuario(usuario);
        prestamo1.setFechaDevolucion(LocalDate.now());
        Prestamo prestamo2 = new Prestamo();
        prestamo2.setUsuario(usuario);
        prestamo2.setFechaDevolucion(null);
        
        ArrayList<Prestamo> prestamos = new ArrayList<Prestamo>();
        prestamos.add(prestamo1);
        prestamos.add(prestamo2);
        
        
        when(prestamoRepository.findByUsuarioIdUsuario(10L)).thenReturn(prestamos);
        
        
        assertDoesNotThrow(() -> prestamoService.revisaCondiciones(10L));

    }
    
    //Prueba unitaria cuando usuario, libro y sucursal existen
    //Este caso no deberia arrojar error, ya que todos los datos son validos
    @Test
    void creaPrestamoValido(){
        Libro libro=new Libro();
        
        when(libroRepository.findById(10)).thenReturn(Optional.of(libro));
        
        Usuario usuario=new Usuario();
        usuario.setidUsuario(11L);
        
        when(usuarioRepository.findById(11L)).thenReturn(Optional.of(usuario));
        
        Sucursal sucursal=new Sucursal();
        sucursal.setIdSucursal(3);
        sucursal.setNombre("Bellas artes");
        
        when(sucursalRepository.findByNombre("Bellas artes")).thenReturn(sucursal);
        
        Prestamo prestamo=new Prestamo();
        prestamo.setLibro(libro);
        prestamo.setUsuario(usuario);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaLimite(LocalDate.now().plusDays(10));
        prestamo.setMultaAcumulada(0);
        prestamo.setMultaPagada(false);
        prestamo.setFechaDevolucion(null);
        prestamo.setNombreSucursal("Bellas artes");
        
        when(prestamoRepository.save(any(Prestamo.class))).thenReturn(prestamo);
        
        assertDoesNotThrow(() -> prestamoService.creaPrestamo(10, 11L, "Bellas artes"));
        
    }
    
    //En esta prueba unitaria se evalua si no existe el libro
    //En este caso se deberia arrojar una excepcion de tipo IllegalArgumentException
    //ya que el no exite un libro con un id correspondiente al ingresado
    @Test
    void creaPrestamoLibroNoExiste(){
        Libro libro=new Libro();
        libro.setIdLibro(10);
        
        when(libroRepository.findById(10)).thenReturn(Optional.of(libro));
        
        Usuario usuario=new Usuario();
        usuario.setidUsuario(11L);
        
        when(usuarioRepository.findById(11L)).thenReturn(Optional.of(usuario));
        
        Sucursal sucursal=new Sucursal();
        sucursal.setIdSucursal(3);
        sucursal.setNombre("Bellas artes");
        
        when(sucursalRepository.findByNombre("Bellas artes")).thenReturn(sucursal);
        
        Prestamo prestamo=new Prestamo();
        prestamo.setLibro(libro);
        prestamo.setUsuario(usuario);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaLimite(LocalDate.now().plusDays(10));
        prestamo.setMultaAcumulada(0);
        prestamo.setMultaPagada(false);
        prestamo.setFechaDevolucion(null);
        prestamo.setNombreSucursal("Bellas artes");
        
        IllegalArgumentException exception= assertThrows(IllegalArgumentException.class, 
            () -> prestamoService.creaPrestamo(20,11L,"Bellas artes"));
    }
    
    //En esta prueba se hace el caso de que no exista el usuario
    //En este caso se deberia arrojar una excepcion de tipo IllegalArgumentException
    //ya que el no exite un usuario con un id existente
    @Test
    void creaPrestamoUsuarioNoExiste(){
        Libro libro=new Libro();
        libro.setIdLibro(10);
        
        when(libroRepository.findById(10)).thenReturn(Optional.of(libro));
        
        Usuario usuario=new Usuario();
        usuario.setidUsuario(11L);
        
        when(usuarioRepository.findById(11L)).thenReturn(Optional.of(usuario));
        
        Sucursal sucursal=new Sucursal();
        sucursal.setIdSucursal(3);
        sucursal.setNombre("Bellas artes");
        
        when(sucursalRepository.findByNombre("Bellas artes")).thenReturn(sucursal);
        
        Prestamo prestamo=new Prestamo();
        prestamo.setLibro(libro);
        prestamo.setUsuario(usuario);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaLimite(LocalDate.now().plusDays(10));
        prestamo.setMultaAcumulada(0);
        prestamo.setMultaPagada(false);
        prestamo.setFechaDevolucion(null);
        prestamo.setNombreSucursal("Bellas artes");
        
        IllegalArgumentException exception= assertThrows(IllegalArgumentException.class, 
            () -> prestamoService.creaPrestamo(10,20L,"Bellas artes"));
    }
    

    
    //Prueba que verifica que se lance una excepcion cuando el usuario no existe al registrar una devolucion
    //Se configura una busqueda de usuario que retorna vacio (usuario no existente)
    //se espera una excepción IllegalArgumentException con el mensaje correspondiente
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
    
    //Prueba que verifica que se lance una excepcion cuando el préstamo no exsite al registrar una devolucion
    // Se configura un usuario valido pero el prestamo no se encuentra en la base de datos
    //Se espera que se lance una excepción IllegalArgumentException con el mensaje correspondiente
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
 
}
