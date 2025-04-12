/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.formularios.controlador;

import com.example.demo.capanegocio.CorreoService;
import com.example.demo.capanegocio.LibroService;
import com.example.demo.capanegocio.PrestamoService;
import com.example.demo.capanegocio.SucursalSevice;
import com.example.demo.capanegocio.modelo.Libro;
import com.example.demo.capanegocio.modelo.Prestamo;
import com.example.demo.capanegocio.modelo.Sucursal;
import com.example.demo.formularios.vistas.FormlarioLogin;
import java.time.LocalDate;
import java.util.List;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author vsfs2
 */
@Component
public class ControladorInicio {
    
    @Autowired
    private PrestamoService prestamoService;
    @Autowired
    private LibroService libroService;
    @Autowired
    private SucursalSevice sucursalService;
    @Autowired
    private CorreoService correoService; 
    @Autowired
    private ApplicationContext context;
     
/**
 * Inicia la aplicación enviando primero las notificaciones de multa a los usuarios
 * y luego mostrando el formulario de inicio de sesión.
 */     

    public void inicia(){
        mandaNotificacionesMulta();
        enviarRecordatoriosDevolucion();
        FormlarioLogin loginForm =context.getBean(FormlarioLogin.class);
        loginForm.setVisible(true);   
    }
    
 /**
 * Recupera los préstamos con acumulación de multa y calcula el monto correspondiente con prestamoService,
 * para después enviar una notificación con toda la información del atraso por correo electrónico al usuario 
 * relacionado con cada préstamo.
 * 
 * Si ocurre algún error al enviar un correo, se notifica al administrador del correo electrónico de la biblioteca.
 */
    
     public void mandaNotificacionesMulta(){
         System.out.println("Se estan enviando los correos");
         List<Prestamo> prestamos = prestamoService.recuperaPrestamosAcumuladoresDeMulta();
         System.out.println(prestamos);
         for (Prestamo p : prestamos) {
        try {
            Libro libro = libroService.recuperaLibroPorId(p.getLibro().getIdLibro());
            Sucursal sucursal = sucursalService.recuperaSucursalPorNombre(p.getNombreSucursal());
            String subject = "MULTA ACUMULADA";
            String content = "Estimado usuario, usted presenta una multa de \n\n" +
                    "                        " + prestamoService.calcularMulta(p.getIdPrestamo()) + "\n\n"
                    + "por el atraso en la devolución de\n\n" 
                    + libro.getTitulo() + " de " + libro.getAutor().getNombre() + "\n\n"
                    + "prestado el\n\n"
                    + "               " + p.getFechaPrestamo() + "\n\n"
                    + "con fecha límite de devolución el\n\n"
                    + "               " + p.getFechaLimite() + "\n\n"
                    + "en la sucursal\n\n"
                    + "               " + sucursal.getNombre() + "\n\n"
                    + "ubicada en " + sucursal.getDireccion() + "\n\n"
                    + "DEVOLVER INMEDIATAMENTE PARA EVITAR EL INCREMENTO DE LA MULTA, QUE ES DE 5 PESOS POR DÍA";

            correoService.sendCorreo(p.getUsuario().getCorreo(), subject, content, null);
            

            } catch (Exception e) {
                System.out.println( "Error al enviar correo a " + p.getUsuario().getCorreo());
            }
        }
         System.out.println("Se terminaron de enviar los correos");
    }
     
     
    /*
     Recupera los prestamos con una fecha limite cercana, y manda un correo a los usuarios respectivos,
     envia el mensaje incluyendo el prestamo, el titulo del libro y la fecha de devolucion.
     */ 
     public void enviarRecordatoriosDevolucion() {
        LocalDate fechaLimite = LocalDate.now().plusDays(5); // Fecha límite en 5 días
        List<Prestamo> prestamos = prestamoService.obtenerPrestamosConFechaLimiteCercana(fechaLimite);

        for (Prestamo prestamo : prestamos) {
            String destinatario = prestamo.getUsuario().getCorreo(); // Asumiendo que Usuario tiene un campo email
            String asunto = "Recordatorio de Devolución de Libro";
            String mensaje = "Estimado/a " + prestamo.getUsuario().getNombre() + ",\n\n"
                    + "Este es un recordatorio de que la fecha de devolución del libro \""
                    + prestamo.getLibro().getTitulo() + "\" es el " + prestamo.getFechaDevolucion() + ".\n\n"
                    + "Por favor, realiza la devolución a tiempo.\n\n"
                    + "Gracias.";

            try {
                correoService.sendCorreo(prestamo.getUsuario().getCorreo(), asunto, mensaje, null);
                
                System.out.println("Recordatorio enviado a: " + destinatario);
            } catch (MessagingException e) {
                System.err.println("Error al enviar recordatorio a: " + destinatario + " - " + e.getMessage());
            }
        }
        System.out.println("Se terminaron de enviar los correos de devoluciones cercanas");
    }
   
}
