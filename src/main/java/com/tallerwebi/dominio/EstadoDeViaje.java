package com.tallerwebi.dominio;

public enum EstadoDeViaje {

    PENDIENTE, CANCELADO, FINALIZADO, EN_CURSO;

    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
