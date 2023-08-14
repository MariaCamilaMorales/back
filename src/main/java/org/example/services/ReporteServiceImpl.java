package org.example.services;

import org.example.dao.MovimientoRepository;
import org.example.dto.ClienteDTO;
import org.example.dto.CuentaDTO;
import org.example.dto.MovimientoDTO;
import org.example.dto.ReporteDTO;
import org.example.entities.Cuenta;
import org.example.entities.Movimiento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    private MovimientoRepository movimientoRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public ReporteDTO getReporte(Date fechaInicio, Date fechaFin, Long clienteId) {
        List<Movimiento> movimientos = movimientoRepository.findByFechaBetweenAndCuenta_Cliente_ClienteId(fechaInicio, fechaFin, clienteId);
        List<MovimientoDTO> movimientosDTO = movimientos.stream().map(movimiento -> modelMapper.map(movimiento, MovimientoDTO.class)).collect(Collectors.toList());
        ReporteDTO reporteDTO = new ReporteDTO();
        if(movimientos.isEmpty()){
            return reporteDTO;
        }
        reporteDTO.setClienteDTO(modelMapper.map(movimientos.get(0).getCuenta().getCliente(), ClienteDTO.class));
        reporteDTO.setMovimientos(movimientosDTO);
        List<CuentaDTO> cuentasDTO =new ArrayList<>();
        for(MovimientoDTO movimientoDTO: movimientosDTO){
            if(cuentasDTO.contains(movimientoDTO.getCuenta())){
                continue;
            }
            cuentasDTO.add(movimientoDTO.getCuenta());
        }
        reporteDTO.setCuentas(cuentasDTO);
        return reporteDTO;
    }
}
