package org.example.dto;

import lombok.Data;

import java.util.Date;
@Data
public class MovimientoDTO {

    private Long movimientoId;
    private Date fecha;
    private String tipoMovimiento;
    private double valor;
    private double saldo;
}
