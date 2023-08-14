package org.example.dto;

import lombok.Data;


@Data
public class CuentaDTO {

    private Long cuentaId;
    private String numeroCuenta;
    private String tipoCuenta;
    private String saldoIncial;
    private String estado;
    private ClienteDTO cliente;
}
