package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@Entity
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String origen;
    private String destino;
    private String fecha;
    private String horario;
    private Double precio;
    private Integer asientosDisponibles;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Conductor conductor;
    private String duracion;
    private String tipoServicio;
}