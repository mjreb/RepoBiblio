/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.capanegocio;
import com.example.demo.capanegocio.modelo.Sucursal;
import com.example.demo.capanegocio.modelo.Inventario;
import com.example.demo.capapersistencia.InventarioRepository;
import com.example.demo.capapersistencia.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vsfs2
 */
@Service
public class InventarioService {
    
    @Autowired
    private InventarioRepository inventarioRepository;
    
    @Autowired
    private SucursalRepository sucursalRepository;
    
    
    public Inventario recuperarInventarioPorSucursal(Sucursal sucursal){
        
        Inventario inventario = inventarioRepository.findBySucursal(sucursal);

        return inventario; 
        
    }
    
    
    
}
