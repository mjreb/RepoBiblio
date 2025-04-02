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
     * @param idSucursal
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

        itemInventarioService.actualizarDisponiblidad(idLibro,sucursal.getIdSucursal());
        libroRepository.save(libro);
        return prestamoRepository.save(prestamo);
    }
    
    public ArrayList<Prestamo> recuperaPrestamosPorUsuario(long id) {

        return (ArrayList<Prestamo>) prestamoRepository.findByUsuarioIdUsuario(id);
    }
    

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
     */
    @Transactional
    public Prestamo registrarDevolucion(int idPrestamo) {
        Prestamo prestamo = prestamoRepository.findById(idPrestamo)
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));

        if (prestamo.getFechaDevolucion() != null) {
            throw new IllegalArgumentException("Este préstamo ya fue devuelto");
        }

        prestamo.setFechaDevolucion(LocalDate.now());
        
        // Calcular multa final si hay retraso
        if (LocalDate.now().isAfter(prestamo.getFechaLimite()) && !prestamo.isMultaPagada()) {
            long diasRetraso = ChronoUnit.DAYS.between(prestamo.getFechaLimite(), LocalDate.now());
            prestamo.setMultaAcumulada(diasRetraso * TARIFA_MULTA_POR_DIA);
        }

        // Liberar el libro
        Libro libro = prestamo.getLibro();
        //libro.setCantidad(1+ 1);

        libroRepository.save(libro);
        return prestamoRepository.save(prestamo);
    }

    /**
     * Actualiza diariamente las multas para préstamos vencidos no devueltos.
     */
    @Scheduled(cron = "0 0 0 * * ?") // Se ejecuta a medianoche cada día
    @Transactional
    public void actualizarMultasDiarias() {
        List<Prestamo> prestamosVencidos = prestamoRepository
                .findByFechaDevolucionIsNullAndFechaLimiteBefore(LocalDate.now());

        for (Prestamo prestamo : prestamosVencidos) {
            if (!prestamo.isMultaPagada()) {
                long diasRetraso = ChronoUnit.DAYS.between(prestamo.getFechaLimite(), LocalDate.now());
                double multa = diasRetraso * TARIFA_MULTA_POR_DIA;
                prestamo.setMultaAcumulada(multa);
                prestamoRepository.save(prestamo);
            }
        }
    }

    /**
     * Registra el pago de una multa.
     */
    @Transactional
    public Prestamo pagarMulta(int idPrestamo) {
        Prestamo prestamo = prestamoRepository.findById(idPrestamo)
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));

        if (prestamo.isMultaPagada()) {
            throw new IllegalStateException("La multa ya fue pagada");
        }

        prestamo.setMultaPagada(true);
        return prestamoRepository.save(prestamo);
    }

    /**
     * Obtiene préstamos con multas pendientes de pago.
     */
    public List<Prestamo> obtenerPrestamosConMultaPendiente() {
        return prestamoRepository.findByMultaAcumuladaGreaterThanAndMultaPagadaFalse(0.0);
    }
    
    
    public int numeroPrestamos(long id){
        
        List<Prestamo> prestamos = ( ArrayList<Prestamo>)prestamoRepository.findByUsuarioIdUsuario(id);
        
        return prestamos.size();
    }
    
            
    

    
    
}