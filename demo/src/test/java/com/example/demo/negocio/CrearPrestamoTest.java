/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.negocio;

import com.example.demo.capanegocio.ItemInventarioService;
import com.example.demo.capanegocio.PrestamoService;
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
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;



/**
 *
 * @author 03_06_2023
 */
public class CrearPrestamoTest {
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
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    //Prueba unitaria cuando usuario, libro y sucursal existen
    //Este caso no deberia arrojar error, ya que todos los datos son validos
    @Test
    void creaPrestamoValido(){
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
        
        doNothing().when(itemInventarioService)
               .actualizarDisponiblidad(anyInt(), anyInt(), anyInt());
        
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
}
