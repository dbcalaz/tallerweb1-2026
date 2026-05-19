package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String origen;
    private String destino;
    @ManyToOne()
    private Usuario usuario;

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

}
