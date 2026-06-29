package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Pasajero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private Integer numeroAsiento;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = true)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_reserva")
    private Reserva reserva;
}