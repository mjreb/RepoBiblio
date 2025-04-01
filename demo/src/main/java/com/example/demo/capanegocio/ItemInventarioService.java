/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.capanegocio;

import com.example.demo.capanegocio.modelo.Inventario;
import com.example.demo.capanegocio.modelo.ItemInventario;
import com.example.demo.capanegocio.modelo.Libro;
import com.example.demo.capanegocio.modelo.Sucursal;
import com.example.demo.capapersistencia.InventarioRepository;
import com.example.demo.capapersistencia.LibroRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.capapersistencia.ItemInventarioRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author vsfs2
 */

@Service
public class ItemInventarioService {
    
    @Autowired
    private ItemInventarioRepository itemInventarioRepository;
    
    @Autowired
    private LibroRepository libroRepository;
    
    @Autowired
    private InventarioRepository inventarioRepository;
    
     @Autowired
    private LibroService libroService;
     
     @Autowired
    private SucursalSevice sucursalService;
    
    
    
    
    public String agregarLibro(int idLibro, int idInventario, int cantidad){
        
        Inventario inventario = inventarioRepository.findById(idInventario)
            .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
        
        
        Libro libro = libroRepository.findById(idLibro)
            .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        
        
        // Verificar si ya existe el item
        Optional<ItemInventario> itemExistente = itemInventarioRepository.findByInventarioAndLibro(inventario, libro);
        
        if (itemExistente.isPresent()) {
            ItemInventario inv = itemExistente.get();
            inv.setCantidad(inv.getCantidad() + cantidad);
            itemInventarioRepository.save(inv);
            return "Libros agreados correctamente";
            
        } else {
            ItemInventario nuevoLibro= new ItemInventario();
            nuevoLibro.setInventario(inventario);
            nuevoLibro.setLibro(libro);
            nuevoLibro.setCantidad(cantidad);
            itemInventarioRepository.save(nuevoLibro);
            return "Libros nuevos agreados correctamente";
        }
    }
    
    
    public  List<ItemInventario> recuperarLibrosPorInventario(Inventario inventario){
        return itemInventarioRepository.findByInventario(inventario);
    }
    
    public ArrayList<ItemInventario> recuperaInventarioPorIdLibro(int idLibro){
        
        Libro libro = libroRepository.findById(idLibro).orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + idLibro));
        
        return itemInventarioRepository.findByLibro(libro);
  
    }
    
   public void actualizarDisponiblidad(int idLibro,int idSucursal){
       Libro libro = libroService.recuperaLibroPorId(idLibro);
       Sucursal sucursal = sucursalService.recuperarSucursal(idSucursal);
       Inventario inventario = inventarioRepository.findBySucursal(sucursal);

       ItemInventario item = itemInventarioRepository.findByInventarioAndLibro(inventario,libro)
            .orElseThrow(() -> new RuntimeException(
                    "No se encontró inventario para el libro ID: " + idLibro + 
                    " en la sucursal ID: " + idSucursal));
       
       item.setCantidad(item.getCantidad() - 1);
       itemInventarioRepository.save(item);
 
   }
    
    
}
