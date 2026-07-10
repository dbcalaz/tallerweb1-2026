package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario; // Seria el dueño de la cuenta que va a comprar el/los pasaje/s

    @ManyToOne
    @JoinColumn(name = "id_viaje")
    private Viaje viaje;

    @Enumerated(EnumType.STRING)
    private EstadoReserva estadoReserva;

    private Double precioTotal;

    // Se agrega una relacion de: 1 reserva tiene muchos pasajeros
    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
    private List<Pasajero> pasajeros = new ArrayList<>();

    //Para calcular la cantidad de tramos que hace un usuario y así poder calcular el valor del viaje.
    @ManyToOne
    @JoinColumn(name = "id_parada_origen")
    private ViajeParada paradaOrigen;

    @ManyToOne
    @JoinColumn(name = "id_parada_destino")
    private ViajeParada paradaDestino;

    public Reserva() {
    }

    public boolean puedeCancelarse() {
        return estadoReserva == EstadoReserva.CONFIRMADA;
    }
}