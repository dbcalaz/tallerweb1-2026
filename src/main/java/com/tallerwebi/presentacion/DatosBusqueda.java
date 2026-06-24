package com.tallerwebi.presentacion;

public class DatosBusqueda {
    private Long idOrigen;
    private Long idDestino;
    private String fecha;
    private Integer pasajeros;

    public DatosBusqueda() {}
    public DatosBusqueda(Long idOrigen, Long idDestino, String fecha, Integer pasajeros) {
        this.idOrigen = idOrigen;
        this.idDestino = idDestino;
        this.fecha = fecha;
        this.pasajeros = pasajeros;
    }
    // Getters y Setters
    public Long getIdOrigen() { return idOrigen; }
    public void setIdOrigen(Long idOrigen) { this.idOrigen = idOrigen; }
    public Long getIdDestino() { return idDestino; }
    public void setIdDestino(Long idDestino) { this.idDestino = idDestino; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public Integer getPasajeros() { return pasajeros; }
    public void setPasajeros(Integer pasajeros) { this.pasajeros = pasajeros; }
}