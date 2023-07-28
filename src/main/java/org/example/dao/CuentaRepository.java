package org.example.dao;

import org.example.entities.Cliente;
import org.example.entities.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CuentaRepository extends JpaRepository<Cuenta, Long>, JpaSpecificationExecutor<Cuenta> {
}
