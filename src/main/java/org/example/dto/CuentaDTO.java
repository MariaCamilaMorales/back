package org.example.dto;

import lombok.Data;
import org.example.entities.Cliente;

@Data
public class CuentaDTO {

    private Long cuentaId;
    private String numeroCuenta;
    private String tipoCuenta;
    private String saldoIncial;
    private String estado;
    private Cliente cliente;
}
