/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.capapersistencia;

import com.example.demo.capanegocio.ItemInventarioService;
import com.example.demo.capanegocio.modelo.Inventario;
import com.example.demo.capanegocio.modelo.ItemInventario;
import com.example.demo.capanegocio.modelo.Libro;
import com.example.demo.capanegocio.modelo.Sucursal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author vsfs2
 */
public interface ItemInventarioRepository extends JpaRepository<ItemInventario, Integer>{
       
    Optional<ItemInventario> findByInventarioAndLibro(Inventario inventario, Libro libro);
    ArrayList<ItemInventario> findByLibro(Libro libro);
    List<ItemInventario> findByInventario(Inventario inventario);
    //Optional<ItemInventario> findByInventarioSucursalAndLibro(Sucursal sucursal, Libro libro);
    //public ItemInventario findByIdLibroAndInventario();
    
}
