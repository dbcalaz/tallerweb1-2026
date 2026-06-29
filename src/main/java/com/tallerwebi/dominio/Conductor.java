package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Conductor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String documento;
    private String password;
    @Enumerated(EnumType.STRING)
    private TipoDeLicencia licencia;
    private float calificacion;
    private Double ganancia;
    private boolean cuentaHabilitada;
    @Enumerated(EnumType.STRING)
    private EstadoConductor estadoConductor;

}