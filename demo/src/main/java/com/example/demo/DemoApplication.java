package com.example.demo;

import com.example.demo.formularios.controlador.ControladorInicio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo"})
public class DemoApplication{

	public static void main(String[] args) {
            
            SpringApplication app = new SpringApplication(DemoApplication.class);
            app.setHeadless(false);

            // Iniciar la app y obtener el contexto
            ApplicationContext context = app.run(args);

            // Obtener el bean del controlador y usarlo
            ControladorInicio controlador = context.getBean(ControladorInicio.class);
            controlador.inicia();
     }
}
