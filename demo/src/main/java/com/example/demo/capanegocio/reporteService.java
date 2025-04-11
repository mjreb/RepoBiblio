package com.example.demo.capanegocio;

import com.example.demo.capanegocio.modelo.Devolucion;
import com.example.demo.capanegocio.modelo.Prestamo;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import com.example.demo.capanegocio.modelo.Reporte;
import com.example.demo.capanegocio.modelo.Usuario;
import com.example.demo.capapersistencia.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class reporteService {
    @Autowired
    private ReporteRepository reporteRepository;

    public ArrayList<Reporte> recuperaReportes() {
        System.out.println("reporteRepository = " + reporteRepository);
        ArrayList<Reporte> reportes = (ArrayList<Reporte>) reporteRepository.findAll();
        return reportes;
    }

    public Reporte agregaReporte(String tipoReporte, String descripcion, Usuario usuario, Prestamo prestamo, Devolucion devolucion) {
        // Verifica si ya existe un reporte con el mismo tipo
        Reporte reporteExistente = reporteRepository.findBytipo(tipoReporte);

        if (reporteExistente != null) {
            throw new IllegalArgumentException("Un reporte con este tipo ya existe");
        }

        // Crea el reporte
        Reporte reporte = new Reporte();
        reporte.setTipoReporte(tipoReporte);
        reporte.setDescripcion(descripcion);
        reporte.setUsuario(usuario);
        reporte.setPrestamo(prestamo);
        reporte.setDevolucion(devolucion);

        // Guarda el reporte en la base de datos
        return reporteRepository.save(reporte);
    }
}
