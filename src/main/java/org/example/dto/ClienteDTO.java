package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ClienteDTO extends PersonaDTO {

    private Long clienteId;
    @JsonIgnore
    private String contrasena;
    private String estado;
}
