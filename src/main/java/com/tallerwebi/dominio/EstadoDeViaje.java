package com.tallerwebi.dominio;

import lombok.Getter;

@Getter
public enum EstadoDeViaje {

    PENDIENTE("Pendiente"),
    CANCELADO("Cancelado"),
    FINALIZADO("Finalizado"),
    EN_CURSO("En Curso"),
    DISPONIBLE("Disponible"),
    ASIGNADO("Asignado");

    private final String descripcion;

    EstadoDeViaje(String descripcion) {
        this.descripcion = descripcion;
    }
}
