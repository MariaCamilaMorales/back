package org.example.services;

import org.example.dto.MovimientoDTO;

import java.util.List;

public interface MovimientoService {
    List<MovimientoDTO> getAll(int page, int size);

    MovimientoDTO createMovimiento(MovimientoDTO movimientoDTO);

    MovimientoDTO updateMovimiento(Long movimientoId, MovimientoDTO movimientoDTO);

    void delete(Long movimientoId);
}
