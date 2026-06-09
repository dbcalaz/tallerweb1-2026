package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Entity
public class Reserva {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name="id_usuario")
        private Usuario usuario;

        @ManyToOne
        @JoinColumn(name="id_viaje")
        private Viaje viaje;

        @Enumerated(EnumType.STRING)
        private EstadoReserva estadoReserva;

}
