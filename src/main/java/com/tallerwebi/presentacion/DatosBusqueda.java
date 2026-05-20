package com.tallerwebi.presentacion;

public class DatosBusqueda {
    private String origen;
    private String destino;
    private String fecha;

    public DatosBusqueda() {}

    public DatosBusqueda(String origen, String destino, String fecha) {
        this.origen = origen;
        this.destino = destino;
        this.fecha = fecha;
    }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}