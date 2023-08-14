package org.example.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.MovimientoDTO;
import org.example.dto.ReporteDTO;
import org.example.services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/reporte")
public class ReporteController {
    @Autowired
    private ReporteService reporteService;

    @GetMapping("")
    public ReporteDTO getReporte(@RequestParam("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
                                   @RequestParam("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,
                                   @RequestParam("clienteId") Long clienteId) {
        ReporteDTO reporte = reporteService.getReporte(fechaInicio, fechaFin, clienteId);
        return reporte;
    }
}
