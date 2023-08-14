package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class PersonaDTO {
    private String nombre;
    private String genero;
    private Integer edad;
    @JsonIgnore
    private String identificacion;
    @JsonIgnore
    private String direccion;
    @JsonIgnore
    private String telefono;
}
