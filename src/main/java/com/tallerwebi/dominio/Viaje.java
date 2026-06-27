package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("orden ASC")
    private List<ViajeParada> paradas;
    private LocalDate fecha;
    private LocalTime horario;
    private Double precio;
    private Integer numeroViaje;
    private Integer asientosDisponibles;
    private String duracion;
    //private String tipoServicio;

    @Enumerated(EnumType.STRING)
    private TipoDeViaje tipoDeViaje;

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

    public String getOrigen() {
        if (paradas == null || paradas.isEmpty()) return null;
        return paradas.get(0).getParada().getNombre();
    }

    public String getDestino() {
        if (paradas == null || paradas.isEmpty()) return null;
        return paradas.get(paradas.size() - 1).getParada().getNombre();
    }
}