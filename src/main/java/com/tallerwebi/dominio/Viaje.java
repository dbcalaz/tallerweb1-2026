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
    private String destino;
    private String fecha;
    private String horario;
    private Double precio;
    private Integer numeroViaje;
    private Integer asientosDisponibles;
    private String duracion;
    private String tipoServicio;

    private String duracion;
    private String tipoDeViaje;

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

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getTipoDeViaje() {
        return tipoDeViaje;
    }

    public void setTipoDeViaje(String tipoDeViaje) {
        this.tipoDeViaje = tipoDeViaje;
    }
}