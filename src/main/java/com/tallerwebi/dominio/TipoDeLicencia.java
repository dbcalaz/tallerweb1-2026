package com.tallerwebi.dominio;

import lombok.Getter;

@Getter
public enum TipoDeLicencia {

    D1("Licencia D1"),
    D2("Licencia D2");

    private final String descripcion;

    TipoDeLicencia(String descripcion) {
        this.descripcion = descripcion;
    }
}
