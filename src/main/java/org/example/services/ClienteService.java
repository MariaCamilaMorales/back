package org.example.services;

import org.example.dto.ClienteDTO;

import java.util.List;

public interface ClienteService {
    List<ClienteDTO> getAll(int page, int size);

    ClienteDTO createCliente(ClienteDTO clienteDTO);

    ClienteDTO updateCliente(Long clienteId, ClienteDTO clienteDTO);

    void delete(Long clienteId);
}
