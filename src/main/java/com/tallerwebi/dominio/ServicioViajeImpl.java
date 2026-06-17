package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DatosBusqueda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional // Spring abre una transacción de BD automática para cada método
public class ServicioViajeImpl implements ServicioViaje {

    private RepositorioViaje repositorioViaje;

    @Autowired
    public ServicioViajeImpl(RepositorioViaje repositorioViaje) {
        this.repositorioViaje = repositorioViaje;
    }

    @Override
    public void confirmarViaje(Viaje viaje) {
        repositorioViaje.guardarViaje(viaje);
    }

    // Envía directo los campos desglosados del DTO hacia el repositorio
    @Override
    public List<Viaje> buscarViajes(DatosBusqueda datosBusqueda) {
        return repositorioViaje.buscarViajes(
                datosBusqueda.getOrigen(),
                datosBusqueda.getDestino(),
                datosBusqueda.getFecha(),
                datosBusqueda.getPasajeros()
        );
    }

    // REGLA DE NEGOCIO: Resta un asiento disponible de la combi al comprar
    @Override
    public void reservarAsiento(Long idViaje, Usuario usuarioLogueado) {
        Viaje viaje = repositorioViaje.buscarPorId(idViaje);
        if (viaje != null && viaje.getAsientosDisponibles() > 0) {
            viaje.setAsientosDisponibles(viaje.getAsientosDisponibles() - 1);
            repositorioViaje.actualizar(viaje); // Guarda el nuevo stock de asientos
        } else {
            throw new RuntimeException("El viaje seleccionado no posee asientos disponibles.");
        }
    }

    // REGLA DE NEGOCIO: Devuelve +1 asiento a la combi si el usuario cancela la reserva
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
}