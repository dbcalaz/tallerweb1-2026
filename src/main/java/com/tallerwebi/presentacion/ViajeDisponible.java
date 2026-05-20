package com.tallerwebi.presentacion;

public class ViajeDisponible {
    private String origen;
    private String destino;
    private String horario;
    private Double precio;
    private Integer asientosDisponibles;

    public ViajeDisponible(String origen, String destino, String horario, Double precio, Integer asientos) {
        this.origen = origen;
        this.destino = destino;
        this.horario = horario;
        this.precio = precio;
        this.asientosDisponibles = asientos;
    }

    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }
    public String getHorario() { return horario; }
    public Double getPrecio() { return precio; }
    public Integer getAsientosDisponibles() { return asientosDisponibles; }
}