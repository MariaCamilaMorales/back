package org.example.services;

import org.example.dao.MovimientoRepository;
import org.example.dto.CuentaDTO;
import org.example.dto.MovimientoDTO;
import org.example.entities.Cuenta;
import org.example.entities.Movimiento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MoviemientoServiceImpl implements MovimientoService{

    @Autowired
    private MovimientoRepository movimientoRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override

    public List<MovimientoDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movimiento> pageResult = movimientoRepository.findAll(pageable);
        List<Movimiento> movimientos = pageResult.getContent();
        return movimientos.stream().map(movimiento -> modelMapper.map(movimiento, MovimientoDTO.class)).collect(Collectors.toList());
    }

    @Override
    public MovimientoDTO createMovimiento(MovimientoDTO movimientoDTO) {
        Movimiento movimiento = modelMapper.map(movimientoDTO, Movimiento.class);
        Movimiento savedMovimiento = movimientoRepository.save(movimiento);
        return modelMapper.map(savedMovimiento, MovimientoDTO.class);
    }

    @Override
    public MovimientoDTO updateMovimiento(Long movimientoId, MovimientoDTO movimientoDTO) {
        return null;
    }
}
