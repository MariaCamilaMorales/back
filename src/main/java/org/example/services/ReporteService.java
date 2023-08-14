package org.example.services;

import org.example.dto.ReporteDTO;

import java.util.Date;

public interface ReporteService {
    ReporteDTO getReporte(Date fechaInicio, Date fechaFin, Long clienteId);
}
