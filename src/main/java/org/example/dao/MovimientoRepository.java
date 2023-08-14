package org.example.dao;

import org.example.entities.Cuenta;
import org.example.entities.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long>, JpaSpecificationExecutor<Movimiento> {
    List<Movimiento> findByFechaBetweenAndCuenta_Cliente_ClienteId(Date fechaInicio, Date fechaFin, Long clienteId);
}
