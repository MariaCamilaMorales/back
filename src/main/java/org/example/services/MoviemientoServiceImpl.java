package org.example.services;

import org.example.dao.CuentaRepository;
import org.example.dao.MovimientoRepository;
import org.example.dto.CuentaDTO;
import org.example.dto.MovimientoDTO;
import org.example.entities.Cliente;
import org.example.entities.Cuenta;
import org.example.entities.Movimiento;
import org.example.exception.CustomException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MoviemientoServiceImpl implements MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movimiento> pageResult = movimientoRepository.findAll(pageable);
        List<Movimiento> movimientos = pageResult.getContent();
        return movimientos.stream().map(movimiento -> modelMapper.map(movimiento, MovimientoDTO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MovimientoDTO createMovimiento(MovimientoDTO movimientoDTO) {
        Movimiento movimiento = modelMapper.map(movimientoDTO, Movimiento.class);
        Optional<Cuenta> cuentaOptional = cuentaRepository.findById(movimientoDTO.getCuenta().getCuentaId());
        if (cuentaOptional.isEmpty()) {
            throw new NoSuchElementException("No se encontró la cuenta con Id:  " + movimientoDTO.getCuenta().getCuentaId());
        }
        Cuenta cuenta = cuentaOptional.get();
        if (cuenta.getSaldoIncial() + movimiento.getValor() < 0) {
            throw new CustomException("No se encuentra disponible " + movimiento.getValor() + " en la cuenta, su saldo es de: " + cuenta.getSaldoIncial());
        }
        cuenta.setSaldoIncial(cuenta.getSaldoIncial() + movimiento.getValor());
        movimiento.setSaldo(cuenta.getSaldoIncial());
        cuentaRepository.save(cuenta);
        Movimiento savedMovimiento = movimientoRepository.save(movimiento);
        return modelMapper.map(savedMovimiento, MovimientoDTO.class);
    }
    @Override
    @Transactional
    public MovimientoDTO updateMovimiento(Long movimientoId, MovimientoDTO movimientoDTO) {
        Movimiento movimiento = movimientoRepository.findById(movimientoId).orElseThrow(() -> new EntityNotFoundException("El movimiento con ID " + movimientoId + " no existe"));
        if (movimientoDTO.getTipoMovimiento() != null) {
            movimiento.setTipoMovimiento(movimientoDTO.getTipoMovimiento());
        }
        if (movimientoDTO.getFecha() != null) {
            movimiento.setFecha(movimientoDTO.getFecha());
        }
        if (movimientoDTO.getValor() != null) {
            movimiento.setValor(movimientoDTO.getValor());
        }
        if (movimientoDTO.getSaldo() != null) {
            movimiento.setSaldo(movimientoDTO.getSaldo());
        }
        Movimiento updatedMovimiento = movimientoRepository.save(movimiento);
        return modelMapper.map(updatedMovimiento, MovimientoDTO.class);
    }

    @Override
    @Transactional
    public void delete(Long movimientoId) {
        Movimiento movimiento = movimientoRepository.findById(movimientoId).orElseThrow(() -> new EntityNotFoundException("El movimiento con ID " + movimientoId + " no existe"));
        movimientoRepository.delete(movimiento);
    }
}
