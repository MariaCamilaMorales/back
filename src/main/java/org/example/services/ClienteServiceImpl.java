package org.example.services;

import org.example.dao.ClienteRepository;
import org.example.dto.ClienteDTO;
import org.example.entities.Cliente;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    @Lazy
    private ClienteRepository clienteRepository;
    @Autowired
    @Lazy
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cliente> pageResult = clienteRepository.findAll(pageable);
        List<Cliente> clientes = pageResult.getContent();
        return clientes.stream().map(cliente -> modelMapper.map(cliente, ClienteDTO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ClienteDTO createCliente(ClienteDTO clienteDTO) {
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        Cliente savedCliente = clienteRepository.save(cliente);
        return modelMapper.map(savedCliente, ClienteDTO.class);
    }

    @Override
    @Transactional
    public ClienteDTO updateCliente(Long clienteId, ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new EntityNotFoundException("El cliente con ID " + clienteId + " no existe"));
        if(clienteDTO.getNombre() != null) {
            cliente.setNombre(clienteDTO.getNombre());
        }
        if(clienteDTO.getGenero() != null) {
            cliente.setGenero(clienteDTO.getGenero());
        }
        if(clienteDTO.getEdad() != null) {
            cliente.setEdad(clienteDTO.getEdad());
        }
        if(clienteDTO.getIdentificacion() != null) {
            cliente.setIdentificacion(clienteDTO.getIdentificacion());
        }
        if(clienteDTO.getDireccion() != null) {
            cliente.setDireccion(clienteDTO.getDireccion());
        }
        if(clienteDTO.getTelefono() != null) {
            cliente.setTelefono(clienteDTO.getTelefono());
        }
        if(clienteDTO.getContrasena() != null) {
            cliente.setContrasena(clienteDTO.getContrasena());
        }
        if(clienteDTO.getEstado() != null) {
            cliente.setEstado(Boolean.valueOf(clienteDTO.getEstado()));
        }
        Cliente updatedCliente = clienteRepository.save(cliente);
        return modelMapper.map(updatedCliente, ClienteDTO.class);
    }

    @Override
    @Transactional
    public void delete(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new EntityNotFoundException("El cliente con ID " + clienteId + " no existe"));
        clienteRepository.delete(cliente);
    }
}
