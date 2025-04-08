/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.capanegocio;

import com.example.demo.capanegocio.modelo.Libro;
import com.example.demo.capanegocio.modelo.Prestamo;
import com.example.demo.capanegocio.modelo.Sucursal;
import com.example.demo.capanegocio.modelo.Usuario;
import com.example.demo.capapersistencia.LibroRepository;
import com.example.demo.capapersistencia.PrestamoRepository;
import com.example.demo.capapersistencia.SucursalRepository;
import com.example.demo.capapersistencia.UsuarioRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ItemInventarioService itemInventarioService;
    
    @Autowired
    private SucursalSevice sucursalService;
    
    @Autowired
    private SucursalRepository sucursalRepository;
    
    @Autowired
    private UserService usuarioService;
     @Autowired
    private DevolucionService devolucionService;

    
    
    

    // Tarifa de multa por día de retraso
    private static final double TARIFA_MULTA_POR_DIA = 5.0;
    private static final int DIAS_PRESTAMO = 14; // Días de duración del préstamo

    /**
     * Recupera todos los préstamos de la base de datos.
     */
    public ArrayList<Prestamo> recuperaPrestamos() {
        return (ArrayList<Prestamo>) prestamoRepository.findAll();
    }

    /**
     * Crea un nuevo préstamo.
     * @param idLibro
     * @param idUsuario
     * @param nombreSucursal
     * @return 
     */
    //@Transactional
    public Prestamo creaPrestamo(int idLibro, long idUsuario, String nombreSucursal) {
        Libro libro = libroRepository.findById(idLibro)
                .orElseThrow(() -> new IllegalArgumentException("El libro no existe"));

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("El usuario no existe"));
        
        Sucursal sucursal = sucursalRepository.findByNombre(nombreSucursal);

        Prestamo prestamo = new Prestamo();
        prestamo.setLibro(libro);
        prestamo.setUsuario(usuario);
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaLimite(LocalDate.now().plusDays(DIAS_PRESTAMO));
        prestamo.setMultaAcumulada(0.0);
        prestamo.setMultaPagada(false);
        prestamo.setFechaDevolucion(null);
        prestamo.setNombreSucursal(nombreSucursal);

        itemInventarioService.actualizarDisponiblidad(idLibro,sucursal.getIdSucursal(),1);
        libroRepository.save(libro);
        return prestamoRepository.save(prestamo);
    }
    /*
    public ArrayList<Prestamo> recuperaPrestamosPorUsuario(long id) {

        return (ArrayList<Prestamo>) prestamoRepository.findByUsuarioIdUsuario(id);
    }
    
*/
    /**
     * Calcula la multa acumulada para un préstamo.
     */
    public double calcularMulta(int idPrestamo) {
        Prestamo prestamo = prestamoRepository.findById(idPrestamo)
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));

        if (prestamo.getFechaDevolucion() != null || prestamo.isMultaPagada()) {
            return 0.0;
        }

        if (LocalDate.now().isBefore(prestamo.getFechaLimite())) {
            return 0.0;
        }

        long diasRetraso = ChronoUnit.DAYS.between(prestamo.getFechaLimite(), LocalDate.now());
        return diasRetraso * TARIFA_MULTA_POR_DIA;
    }

    /**
     * Registra la devolución de un libro.
     * @param idPrestamo
     * @param idUsuario
     * @return 
     */
    //@Transactional
    public Prestamo registrarDevolucion(int idPrestamo, long idUsuario) {
        Prestamo prestamo = prestamoRepository.findById(idPrestamo)
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));
        
        if (prestamo.getUsuario().getidUsuario() != idUsuario) {
            throw new IllegalArgumentException("Este préstamo no es de este usuario");
        }

        if (prestamo.getFechaDevolucion() != null) {
            throw new IllegalArgumentException("Este préstamo ya fue devuelto");
        }

        prestamo.setFechaDevolucion(LocalDate.now());
        
        // Calcular multa final si hay retraso
        if (LocalDate.now().isAfter(prestamo.getFechaLimite())) {
            long diasRetraso = ChronoUnit.DAYS.between(prestamo.getFechaLimite(), LocalDate.now());
            prestamo.setMultaAcumulada(diasRetraso * TARIFA_MULTA_POR_DIA);
        }

        // Actualizar catálogo
        Sucursal sucursal = sucursalService.recuperaSucursalPorNombre(prestamo.getNombreSucursal());
        Libro libro = prestamo.getLibro();
        itemInventarioService.actualizarDisponiblidad(libro.getIdLibro(),sucursal.getIdSucursal(),2);
        libroRepository.save(libro);
        Prestamo prestamo2 = prestamoRepository.save(prestamo);
        devolucionService.registrarDevolucion(idPrestamo);
        return prestamo2; 
    }
    
    
public void revisaCondiciones(long idUsuario) {
    //boolean flag = false; 
    
    Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);
    
    //Reviso que el usuario exista
    if (usuario == null){
        throw new IllegalStateException("Usuario no exste");   
    }
    
    //  Reviso que tenga permisos para hacer prestamo
    if(usuarioService.obtenerPermisoUsuario(usuario) == false){
        throw new IllegalStateException("Usuario no tiene permisos de prestamo");   
    }
    
    // Creo el arreglo para almacenar los usuarios
    ArrayList<Prestamo> prestamos = recuperaPrestamosPorUsuario(idUsuario); 

     
    long prestamosActivos = prestamos.stream()
                                     .filter(p -> p.getFechaDevolucion() == null)
                                     .count();
    
    if (prestamosActivos>=2){
        throw new IllegalStateException("Número máximo de préstamos alcanzado");
    }
        
    ArrayList<Integer> idPrestamos = new ArrayList<>();
    for (Prestamo p : prestamos) {
        if (p.getMultaAcumulada() != 0.0) {
            idPrestamos.add(p.getIdPrestamo());
        }
    }
        
    if (!idPrestamos.isEmpty()) {
        throw new IllegalStateException("Existen prestamos con multas pendientes");
    }
    
}


    public ArrayList<Prestamo> recuperaPrestamosPorUsuario(long id) {
         ArrayList<Prestamo> prestamosFiltrados = new ArrayList(); 
         ArrayList<Prestamo> prestamos = (ArrayList<Prestamo>)prestamoRepository.findByUsuarioIdUsuario(id);
    
            for(Prestamo p:prestamos){
                if (p.getFechaDevolucion() ==  null || p.getMultaAcumulada() != 0.0)
                    prestamosFiltrados.add(p);
                }
    
  
        return prestamosFiltrados; 
    }
    
    public List<Prestamo> recuperaPrestamosAcumuladoresDeMulta(){
        
        return prestamoRepository.findByFechaLimiteLessThanEqualAndFechaDevolucionIsNull(LocalDate.now());
        
    }
    
 
    
}