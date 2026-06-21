package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Reserva {

        public Reserva() {}

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

        private String asientos;
        private Double precioTotal;

        @Column(name = "numero_asiento")
        private Integer numeroAsiento;

        public Reserva(Viaje viaje, String asientos, Double precioTotal) {
                this.viaje = viaje;
                this.asientos = asientos;
                this.precioTotal = precioTotal;
        }

        public Integer getNumeroAsiento() { return numeroAsiento; }
        public void setNumeroAsiento(Integer numeroAsiento) { this.numeroAsiento = numeroAsiento; }
}