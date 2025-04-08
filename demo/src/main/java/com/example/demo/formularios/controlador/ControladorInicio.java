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
import com.example.demo.formularios.vistas.MenuUsuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
     
    
    
    public void inicia(){
        mandaNotificacionesMulta();
        FormlarioLogin loginForm =context.getBean(FormlarioLogin.class);
       
        loginForm.setVisible(true);
         
        
    }
    
     public void mandaNotificacionesMulta(){
         System.out.println("Se estan enviando los correos");
         List<Prestamo> prestamos = prestamoService.recuperaPrestamosAcumuladoresDeMulta();
         System.out.println(prestamos);
         for (Prestamo p : prestamos) {
        try {
            Libro libro = libroService.recuperaLibroPorId(p.getIdPrestamo());
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
   
}
