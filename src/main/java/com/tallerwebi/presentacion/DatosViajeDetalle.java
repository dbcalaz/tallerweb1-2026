package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Pasajero;
import com.tallerwebi.dominio.Reserva;
import com.tallerwebi.dominio.Viaje;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DatosViajeDetalle {

    private final Viaje viaje;
    private final List<Pasajero> pasajerosTotales;
    private final Integer capacidadMaxima;
    private final Integer cantidadPasajeros;
    private final Double precioAcumulado;
    private final int porcentajeOcupacion;

    public DatosViajeDetalle(Viaje viaje) {
        this.viaje = viaje;
        this.pasajerosTotales = new ArrayList<>();

        if (viaje.getReservas() != null) {
            for (Reserva r : viaje.getReservas()) {
                if (r.getEstadoReserva() != null &&
                        r.getEstadoReserva().name().equals("CONFIRMADA") ||
                        r.getEstadoReserva().name().equals("EN_CURSO") ||
                        r.getEstadoReserva().name().equals("FINALIZADA")) {

                    if (r.getPasajeros() != null) {
                        this.pasajerosTotales.addAll(r.getPasajeros());
                    }
                }
            }
        }

        this.cantidadPasajeros = this.pasajerosTotales.size();

        if (viaje.getCombi() != null && viaje.getCombi().getCantidadDeAsientos() != null) {
            this.capacidadMaxima = viaje.getCombi().getCantidadDeAsientos();
        } else {
            this.capacidadMaxima = 19;
        }

        double acumulado = 0.0;
        if (viaje.getReservas() != null) {
            for (Reserva r : viaje.getReservas()) {
                if (r.getEstadoReserva() != null && !r.getEstadoReserva().name().equals("CANCELADA")) {
                    acumulado += (r.getPrecioTotal() != null) ? r.getPrecioTotal() : 0.0;
                }
            }
        }
        this.precioAcumulado = acumulado;

        if (this.capacidadMaxima > 0) {
            int calc = (this.cantidadPasajeros * 100) / this.capacidadMaxima;
            this.porcentajeOcupacion = Math.min(calc, 100);
        } else {
            this.porcentajeOcupacion = 0;
        }
    }
}
