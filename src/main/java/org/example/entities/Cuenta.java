package org.example.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name ="cuentas")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cuentaId;
    private String numeroCuenta;
    private String tipoCuenta;
    private double saldoIncial;
    private boolean estado;

    @OneToOne()
    @JoinColumn(name = "cliente", referencedColumnName = "clienteId", nullable = false)
    private Cliente cliente;

}
