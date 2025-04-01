package com.example.demo.capanegocio;

import com.example.demo.capanegocio.modelo.Inventario;
import com.example.demo.capanegocio.modelo.ItemInventario;
import com.example.demo.capanegocio.modelo.Libro;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.capanegocio.modelo.Sucursal;
import com.example.demo.capanegocio.modelo.Usuario;
import com.example.demo.capapersistencia.InventarioRepository;
import com.example.demo.capapersistencia.ItemInventarioRepository;
import com.example.demo.capapersistencia.LibroRepository;
import com.example.demo.capapersistencia.SucursalRepository;
import com.example.demo.capapersistencia.UsuarioRepository;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SucursalSevice {
    @Autowired
    private SucursalRepository sucursalRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
 @Autowired
    private LibroRepository libroRepository;
    
    @Autowired
    private ItemInventarioRepository inventarioRepository;
    
    @Autowired
    private ItemInventarioService itemInventarioService;
    
    public ArrayList<Sucursal> recuperaSucursales() {
        System.out.println("sucursalRepository = " + sucursalRepository);
        ArrayList<Sucursal> sucursales = (ArrayList<Sucursal>) sucursalRepository.findAll();
        return sucursales;
        
    }

    //Método para recuperar una sucursal con su nombre una nueva sucursal
    public Sucursal recuperaSucursalPorNombre(String nombre) {
        Sucursal sucursal = sucursalRepository.findByNombre(nombre);
        //Chacamos que la sucursal exista
        if (sucursal == null) {
            throw new IllegalArgumentException("La sucursal no existe");
        }
        return sucursal;
    }
    
    

    public Sucursal crearSucursal(String nombre, String direccion, Long idAdministrador, String horario, String link) {
        Usuario administrador = usuarioRepository.findById(idAdministrador)
            .orElseThrow(() -> new IllegalArgumentException("Administrador no encontrado"));
        
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(nombre);
        sucursal.setDireccion(direccion);
        sucursal.setAdministrador(administrador);
        sucursal.setHorario(horario);
        sucursal.setLinkUbicacion(link);
        // Crear automáticamente el inventario asociado
        Inventario inventario = new Inventario(); // Creamos inventario
        inventario.setSucursal(sucursal); // Asociamos la sucursal con el inventario. El inventario se asocia a la sucursal automáticamente
        sucursal.setInventario(inventario); // Asociamos el inventario con la sucursal
       
        return sucursalRepository.save(sucursal);
        
    }
    
    
    public Sucursal recuperarSucursal(int id) {
        return sucursalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sucursal no encontrada con ID: " + id));
 
    }
    
    
     public ArrayList<Sucursal> recuperarSucursales(ArrayList<ItemInventario> items) {
       
       
        Set<Integer> idsSucursales = items.stream()
                                     .map(item -> item.getId())
                                     .collect(Collectors.toSet());
        
        
        // Llenar el arreglo
        ArrayList<Sucursal> sucursales = new ArrayList<>();
        for (Integer s : idsSucursales) {
                Sucursal sucursal = sucursalRepository.findById(s)
                         .orElseThrow(() -> new RuntimeException("Sucursal no encontrada con ID: " + s));
                sucursales.add(sucursal);
        }
            return sucursales;
            
 
    }
     
     
    public List<Sucursal> obtenerSucursalesDisponiblesParaLibro(int idLibro) {
    // 1. Buscar libro (con manejo de error)
    Libro libro = libroRepository.findById(idLibro)
            .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + idLibro));

    // 2. Buscar items de inventario
    List<ItemInventario> itemsInventario = inventarioRepository.findByLibro(libro);
    if (itemsInventario.isEmpty()) {
        throw new RuntimeException("No hay existencias para el libro ID: " + idLibro);
    }

    // 3. Filtrar items con disponibilidad
    List<ItemInventario> itemsDisponibles = filtrarItemsDisponibles(itemsInventario);
    if (itemsDisponibles.isEmpty()) {
        throw new RuntimeException("No hay sucursales con disponibilidad para el libro ID: " + idLibro);
    }

    // 4. Extraer sucursales
    return extraerSucursales(itemsDisponibles);
}

public List<ItemInventario> filtrarItemsDisponibles(List<ItemInventario> items) {
    if (items == null) {
        return Collections.emptyList();
    }
    return items.stream()
            .filter(item -> item.getCantidad() > 0)
            .collect(Collectors.toList());
}

public List<Sucursal> extraerSucursales(List<ItemInventario> items) {
    return items.stream()
            .map(item -> item.getInventario().getSucursal())  // Accede a sucursal a través de inventario
            .filter(Objects::nonNull)
            .distinct()
            .collect(Collectors.toList());
} 
    
    
    

    
}
