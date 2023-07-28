package org.example.dto;

import lombok.Data;

@Data
public class ClienteDTO extends PersonaDTO {

    private Long clienteId;
    private String contrasena;
    private String estado;
}
