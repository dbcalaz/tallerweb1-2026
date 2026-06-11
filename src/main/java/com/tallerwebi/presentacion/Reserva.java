package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Viaje;

public class Reserva {
    private Viaje viaje;
    private String asientos;
    private Double precioTotal;

    public Reserva(Viaje viaje, String asientos, Double precioTotal) {
        this.viaje = viaje;
        this.asientos = asientos;
        this.precioTotal = precioTotal;
    }

    public Viaje getViaje() { return viaje; }
    public void setViaje(Viaje viaje) { this.viaje = viaje; }

    public String getAsientos() { return asientos; }
    public void setAsientos(String asientos) { this.asientos = asientos; }

    public Double getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(Double precioTotal) { this.precioTotal = precioTotal; }
}