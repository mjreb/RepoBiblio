package com.example.demo;

import com.example.demo.capanegocio.AutorService;
import com.example.demo.capanegocio.ItemInventarioService;
import com.example.demo.capanegocio.LibroService;
import com.example.demo.capanegocio.PrestamoService;
import com.example.demo.capanegocio.SucursalSevice;
import com.example.demo.capanegocio.UserService;
import com.example.demo.capanegocio.modelo.Autor;
import com.example.demo.capanegocio.modelo.Libro;
import com.example.demo.capanegocio.modelo.Prestamo;
import com.example.demo.capanegocio.modelo.Usuario;
import com.example.demo.capapersistencia.UsuarioRepository;
import com.example.demo.formularios.controlador.ControladorInicio;
import com.example.demo.formularios.vistas.FormlarioLogin;
import com.example.demo.formularios.vistas.FormularioRegistro;
import jakarta.annotation.PostConstruct;
import java.awt.GraphicsEnvironment;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import static org.springframework.web.server.adapter.WebHttpHandlerBuilder.applicationContext;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo"})
public class DemoApplication{

    /*
    @Autowired
    private UserService userService;
    
    @Autowired
    private AutorService autorService;

    @Autowired
    private LibroService libroService;
    
     
    */
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AutorService autorService;

    @Autowired
    private LibroService libroService;
    
     @Autowired
     private ItemInventarioService item; 
     
     @Autowired
    private SucursalSevice sucursalService;
     

	public static void main(String[] args) {
            
            SpringApplication app = new SpringApplication(DemoApplication.class);
            app.setHeadless(false);

            // Iniciar la app y obtener el contexto
            ApplicationContext context = app.run(args);

            // Obtener el bean y usarlo
            ControladorInicio controlador = context.getBean(ControladorInicio.class);
            controlador.inicia();
            /*
            
            // Crear la aplicación Spring Boot y configurarla para no ser headless
            SpringApplication app = new SpringApplication(DemoApplication.class);
            app.setHeadless(false);  // Desactiva el modo headless

            // Iniciar la aplicación
            app.run(args);
            inicia();
            
            */
        /*
            
        
        //Obtener el bean y hacerlo visible
        
        
        FormlarioLogin loginForm = context.getBean(FormlarioLogin.class);
        loginForm.setVisible(true);
       
     
      */
         
         
 
     }
        
        
   
        
       @PostConstruct
      public void testDB(){
          

        /*
        Usuario usuario1 = userService.agregaUsuario("Juan", "Pérez", "Gómez", "juan@example.com", 1234567890L, "password1", LocalDate.now());
        Usuario usuario2 = userService.agregaUsuario("María", "López", "Martínez", "maria@example.com", 9876543210L, "password2", LocalDate.now());
        Usuario usuario3 = userService.agregaUsuario("Carlos", "García", "Sánchez", "carlos@example.com", 2281463024L, "password3", LocalDate.now());
         

        /* 
        Usuario usuario1 = userService.agregaUsuario("Juan", "Pérez", "Gómez", "juan@example.com", 1234567890L, "password1", LocalDate.now());
        Usuario usuario2 = userService.agregaUsuario("María", "López", "Martínez", "maria@example.com", 9876543210L, "password2", LocalDate.now());
        Usuario usuario3 = userService.agregaUsuario("Carlos", "García", "Sánchez", "carlos@example.com", 2281463024L, "password3", LocalDate.now());
        

         // Agregar autores
         
        Autor autor1 = autorService.agregaAutor("Gabriel García Márquez", "Colombiano");
        Autor autor2 = autorService.agregaAutor("J.K. Rowling", "Británica");
        Autor autor3 = autorService.agregaAutor("George Orwell", "Británico");
        
        
        // Agregar libros (2 por autor)
        
        Libro libro1 = libroService.agregaLibro("Cien años de soledad", "Sudamericana", 1967, 10, autor1);
        Libro libro2 = libroService.agregaLibro("El amor en los tiempos del cólera", "Sudamericana", 1985, 5, autor1);
        Libro libro3 = libroService.agregaLibro("Harry Potter y la piedra filosofal", "Salamandra", 1997, 15, autor2);
        Libro libro4 = libroService.agregaLibro("Harry Potter y la cámara secreta", "Salamandra", 1998, 12, autor2);
        Libro libro5 = libroService.agregaLibro("1984", "Debolsillo", 1949, 8, autor3);
        Libro libro6 = libroService.agregaLibro("Rebelión en la granja", "Debolsillo", 1945, 7, autor3);
        
        
          
        System.out.println(sucursalService.crearSucursal("Zocalo", "San Salvador #12 Colonia Centro", 1L, "L-V: 8:00 a 19:00", "googlemaps.com"));
        System.out.println(sucursalService.crearSucursal("Santa Fe", "Tlalpan 14",2L , "L-V: 8:00 a 19:00", "googlemaps.com"));
   
        
        // Agregar Inventariio
        
        
        item.agregarLibro(1, 1, 2); // Libro 1, Surcusal 1 , cantidad
        item.agregarLibro(2, 1, 0);  //  Libro 2, Surcusal 1, cantidad
        item.agregarLibro(3, 1, 3); // Libro 3, Surcusal 1 , cantidad
        item.agregarLibro(4, 1, 2);  //  Libro 4, Surcusal 2, cantidad
        
        item.agregarLibro(4, 2, 2); //   Libro 4, Surcusal 2, cantidad
        item.agregarLibro(5, 2, 1); //   Libro 5, Surcusal 2, cantidad
        item.agregarLibro(6, 2, 2); //   Libro 6, Surcusal 2, cantidad 
        item.agregarLibro(2, 2, 1);  //  Libro 2, Surcusal 2, cantidad
        
        

   
   
    prestamoService.creaPrestamo(4 , 2L,"Zocalo"); // Libro ID: 4, Usuario ID: 2
    prestamoService.creaPrestamo(4, 1L,"Santa Fe"); // Libro ID: 1, Usuario ID: 1
   prestamoService.creaPrestamo(5, 2L,"Santa Fe"); // Libro ID: 5, Usuario ID: 2

    System.out.println(prestamoService.creaPrestamo(1 , 3L,"Zocalo")); // Libro ID: 4, Usuario ID: 2

    System.out.println(prestamoService.recuperaPrestamosPorUsuario(3L)); 
    */
 //
 
 //System.out.println(prestamoService.registrarDevolucion(14)); 
 
//System.out.println(libroService.agregaLibro("Prueba", "prueba2", 2014, 4L));
  
//System.out.println(prestamoService.recuperaPrestamosAcumuladoresDeMulta());

    
      }
}
