package org.example.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity(name = "movimientos")
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movimientoId;
    private Date fecha;
    private String tipoMovimiento;
    private Double valor;
    private Double saldo;

    @ManyToOne
    @JoinColumn(name = "cuenta", referencedColumnName = "cuentaId", nullable = false)
    private Cuenta cuenta;
}
