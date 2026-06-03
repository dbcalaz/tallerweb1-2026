package com.tallerwebi.presentacion;

public class DatosBusqueda {
    private String origen;
    private String destino;
    private String fecha;
    private Integer pasajeros;

    public DatosBusqueda() {}

    public DatosBusqueda(String origen, String destino, String fecha, Integer pasajeros) {
        this.origen = origen;
        this.destino = destino;
        this.fecha = fecha;
        this.pasajeros = pasajeros;
    }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public Integer getPasajeros() { return pasajeros; }
    public void setPasajeros(Integer pasajeros) { this.pasajeros = pasajeros; }
}