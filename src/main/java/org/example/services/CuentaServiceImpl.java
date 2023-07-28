package org.example.services;


import org.example.dao.CuentaRepository;
import org.example.dto.CuentaDTO;
import org.example.entities.Cliente;
import org.example.entities.Cuenta;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaServiceImpl implements CuentaService{

    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CuentaDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cuenta> pageResult = cuentaRepository.findAll(pageable);
        List<Cuenta> cuentas = pageResult.getContent();
        return cuentas.stream().map(cuenta -> modelMapper.map(cuenta, CuentaDTO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CuentaDTO createCuenta(CuentaDTO cuentaDTO) {
        Cuenta cuenta = modelMapper.map(cuentaDTO, Cuenta.class);
        Cuenta savedCuenta = cuentaRepository.save(cuenta);
        return modelMapper.map(savedCuenta, CuentaDTO.class);
    }

    @Override
    @Transactional
    public CuentaDTO updateCuenta(Long cuentaId, CuentaDTO cuentaDTO) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId).orElseThrow(() -> new EntityNotFoundException("La cuenta con ID " + cuentaId + " no existe"));
        if(cuentaDTO.getNumeroCuenta() != null) {
            cuenta.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
        }
        if(cuentaDTO.getTipoCuenta() != null) {
            cuenta.setTipoCuenta(cuentaDTO.getTipoCuenta());
        }
        if(cuentaDTO.getSaldoIncial() != null) {
            cuenta.setSaldoIncial(Double.valueOf(cuentaDTO.getSaldoIncial()));
        }
        if(cuentaDTO.getEstado() != null) {
            cuenta.setEstado(Boolean.valueOf(cuentaDTO.getEstado()));
        }
        if(cuentaDTO.getCliente().getClienteId() != null){
            Cliente cliente = new Cliente();
            cliente.setClienteId(cuentaDTO.getCliente().getClienteId());
            cuenta.setCliente(cliente);
        }
        Cuenta updatedCuenta = cuentaRepository.save(cuenta);
        return modelMapper.map(updatedCuenta, CuentaDTO.class);
    }

    @Override
    @Transactional
    public void delete(Long cuentaId) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId).orElseThrow(() -> new EntityNotFoundException("La cuenta con ID " + cuentaId + " no existe"));
        cuentaRepository.delete(cuenta);
    }
}
