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
    /*private float latitudOrigen;
    private float longitudOrigen;*/
    private String destino;
    /*private float latitudDestino;
    private float longitudDestino;*/
    private String fecha;
    private String horario;
    private Double precio;
    private Integer asientosDisponibles;
    //private Combi combi;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Usuario usuario;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Conductor conductor;


}