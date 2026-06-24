package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DatosCrearViaje;
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
    private Double tarifaBase= 1000d;
    private Double distancia;
    private Double valorPorKm;
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

    @ManyToMany
    private List<Parada> paradasIntermedias;


    public Viaje (DatosCrearViaje  datosCrearViaje, Conductor conductor, Combi combi, List<Parada> paradasIntermedias) {
        this.origen = datosCrearViaje.getOrigen();
        this.destino = datosCrearViaje.getDestino();
        this.fecha = datosCrearViaje.getFecha();
        this.horario= datosCrearViaje.getHorario();
        this.estadoDeViaje= EstadoDeViaje.EN_CURSO;
        this.distancia = datosCrearViaje.getDistancia();
        this.valorPorKm = datosCrearViaje.getValorPorKm();
        this.precio= tarifaBase + (distancia*valorPorKm);

        this.conductor= conductor;
        this.combi= combi;
        this.paradasIntermedias = paradasIntermedias;
    }
    public Viaje() {
    }

}