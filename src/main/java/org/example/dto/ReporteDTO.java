package org.example.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReporteDTO {
    private List<CuentaDTO> cuentas;
    private ClienteDTO clienteDTO;
    private List<MovimientoDTO> movimientos;
}
