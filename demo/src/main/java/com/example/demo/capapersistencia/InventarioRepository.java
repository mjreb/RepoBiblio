/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.demo.capapersistencia;

import com.example.demo.capanegocio.modelo.Inventario;
import com.example.demo.capanegocio.modelo.Libro;
import com.example.demo.capanegocio.modelo.Sucursal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author vsfs2
 */
public interface InventarioRepository extends JpaRepository<Inventario, Integer>{

    public Inventario findBySucursal(Sucursal sucursal);
}
