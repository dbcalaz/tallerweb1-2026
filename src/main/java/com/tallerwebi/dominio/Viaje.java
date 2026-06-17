package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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
    private Integer numeroViaje;
    private Integer asientosDisponibles;
    private String duracion;
    private String tipoServicio;

    @Enumerated(EnumType.STRING)
    private EstadoDeViaje estadoDeViaje;

    @ManyToOne
    @JoinColumn(name = "id_combi")
    private Combi combi;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_conductor")
    private Conductor conductor;

    @OneToMany
    private List<Reserva> reservas;

}