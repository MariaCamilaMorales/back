package org.example.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public class Persona {
    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personaId;*/
    private String nombre;
    private String genero;
    private Integer edad;
    private String identificacion;
    private String direccion;
    private String telefono;
}
