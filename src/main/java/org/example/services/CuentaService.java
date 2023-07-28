package org.example.services;

import org.example.dto.CuentaDTO;

import java.util.List;

public interface CuentaService {
    List<CuentaDTO> getAll(int page, int size);

    CuentaDTO createCuenta(CuentaDTO cuentaDTO);

    CuentaDTO updateCuenta(Long cuentaId, CuentaDTO cuentaDTO);

    void delete(Long cuentaId);
}
