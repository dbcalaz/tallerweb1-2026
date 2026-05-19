package com.tallerwebi.presentacion;

public class DatosViaje {

    private String origen;
    private String destino;

    public DatosViaje() {
    }

    public DatosViaje(String origen, String destino) {
        this.origen = origen;
        this.destino = destino;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
}
