package com.example.demo.capapersistencia;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.capanegocio.modelo.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    //public Reporte findByNombre(String nombre);
    public Reporte findBytipo(String tipo);

}
