package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DatosBusqueda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioViajeImpl implements ServicioViaje {

    private RepositorioViaje repositorioViaje;
    private RepositorioReserva repositorioReserva;

    @Autowired
    public ServicioViajeImpl(RepositorioViaje repositorioViaje, RepositorioReserva repositorioReserva) {
        this.repositorioViaje = repositorioViaje;
        this.repositorioReserva = repositorioReserva;
    }

    @Override
    public void confirmarViaje(Viaje viaje) {
        repositorioViaje.guardarViaje(viaje);
    }

    @Override
    public List<Viaje> buscarViajes(DatosBusqueda datosBusqueda) {
        return repositorioViaje.buscarViajes(datosBusqueda.getIdOrigen(), datosBusqueda.getIdDestino(), datosBusqueda.getFecha(), datosBusqueda.getPasajeros());
    }

    @Override
    public List<Parada> obtenerTodasLasParadas() {
        return repositorioViaje.obtenerTodasLasParadas();
    }

    @Override
    public void reservarAsiento(Long idViaje, Usuario usuarioLogueado) {
        Viaje viaje = repositorioViaje.buscarPorId(idViaje);
        if (viaje != null && viaje.getAsientosDisponibles() > 0) {
            viaje.setAsientosDisponibles(viaje.getAsientosDisponibles() - 1);
            repositorioViaje.actualizar(viaje);
        } else {
            throw new RuntimeException("El viaje seleccionado no posee asientos disponibles.");
        }
    }

    @Override
    public void liberarAsiento(Long idViaje) {
        Viaje viaje = repositorioViaje.buscarPorId(idViaje);
        if (viaje != null) {
            viaje.setAsientosDisponibles(viaje.getAsientosDisponibles() + 1);
            repositorioViaje.actualizar(viaje);
        }
    }

    @Override
    public Viaje buscarPorId(Long id) {
        return repositorioViaje.buscarPorId(id);
    }

    @Override
    public void guardarReserva(Reserva reserva) {
        calcularPrecio(reserva);
        repositorioViaje.guardarReserva(reserva);
    }

    @Override
    public List<Integer> obtenerAsientosOcupados(Long idViaje) {
        return repositorioViaje.obtenerAsientosOcupados(idViaje);
    }

    @Override
    public void eliminarReserva(Long idReserva) {
        repositorioViaje.eliminarReserva(idReserva);
    }

    // Se aplica una logica de sobre una regla para verificar un viaje dentro de las 24 horas"
    @Override
    public void verificarViajes24Horas(Long idViaje) {
        Viaje viaje = repositorioViaje.buscarPorId(idViaje);

        if (viaje != null) {
            // Se calcula cual es el cupo minimo para que asi la combi pueda salir (puse de ejemplo 30%)
            int capacidadTotal = viaje.getCombi().getCantidadDeAsientos();
            int cupoMinimo = (int) (capacidadTotal * 0.30);

            // Calculamos a cuantos asientos se ocuparon realmente
            int asientosOcupados = capacidadTotal - viaje.getAsientosDisponibles();

            // Con esto se verifica en caso de la siguiente situacion: a este viaje le falta el conductor o no llegó al cupo mínimo de gente?
            if (viaje.getConductor() == null || asientosOcupados < cupoMinimo) {
                // Si llega a pasar, el viaje se cancela de forma automatica.
                viaje.setEstadoDeViaje(EstadoDeViaje.CANCELADO);
                repositorioViaje.actualizar(viaje);
            }
        }
    }

    //este metodo tiene que ser llamado por el metodo que crea la reserva
    public double calcularPrecio(Reserva reserva) {
        Viaje viaje = reserva.getViaje();
        int totalTramos = viaje.getParadas().size() - 1;
        int tramosDelPasajero = reserva.getParadaDestino().getOrden() - reserva.getParadaOrigen().getOrden();
        return (viaje.getPrecio() / totalTramos) * tramosDelPasajero;
    }

}