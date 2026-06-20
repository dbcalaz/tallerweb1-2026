package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EstadoDeCombi;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatosFiltro {
    private EstadoDeCombi estadoDeCombi;
    private String marca;
    private String modelo;
    private String patente;

    public DatosFiltro(EstadoDeCombi estadoDeCombi, String marca, String modelo, String patente) {
        this.estadoDeCombi = estadoDeCombi;
        this.marca = marca;
        this.modelo = modelo;
        this.patente=patente;
    }

    public DatosFiltro() {

    }
}