/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.demo.capapersistencia;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.capanegocio.modelo.Sucursal;

/**
 *
 * @author 100077515
 */
@Repository
public interface SucursalRepository extends CrudRepository<Sucursal, Integer> {
    public Sucursal findByNombre(String nombre);
    public Sucursal findByDireccion(String direccion);
  
}
