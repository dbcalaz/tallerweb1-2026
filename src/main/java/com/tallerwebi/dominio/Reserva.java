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
        @JoinColumn(name="id_usuario")
        private Usuario usuario; // Seria el dueño de la cuenta que va a comprar el/los pasaje/s

        @ManyToOne
        @JoinColumn(name="id_viaje")
        private Viaje viaje;

        @Enumerated(EnumType.STRING)
        private EstadoReserva estadoReserva;

        private Double precioTotal;

        // Se agrega una relacion de: 1 reserva tiene muchos pasajeros
        @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL)
        private List<Pasajero> pasajeros = new ArrayList<>();

        public Reserva() {}
}