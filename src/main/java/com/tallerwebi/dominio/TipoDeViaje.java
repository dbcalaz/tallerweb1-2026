package com.tallerwebi.dominio;

import lombok.Getter;

@Getter
public enum TipoDeViaje {

    COMUN("Común"),
    EJECUTIVO("Ejecutivo"),
    EXPRESSO("Expresso");

    private final String descripcion;

    TipoDeViaje(String descripcion) {
        this.descripcion = descripcion;
    }
}