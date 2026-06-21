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
        return repositorioViaje.buscarViajes(
                datosBusqueda.getOrigen(),
                datosBusqueda.getDestino(),
                datosBusqueda.getFecha(),
                datosBusqueda.getPasajeros()
        );
    }

    @Override
    public List<Viaje> buscarViajes(String origen, String destino, String fecha) {
        return repositorioViaje.buscarViajes(origen, destino, fecha, 1);
    }

    @Override
    public void reservarAsiento(Long idViaje, Usuario usuarioLogueado, String asientosSeleccionados) {
        Viaje viaje = repositorioViaje.buscarPorId(idViaje);
        if (viaje != null && viaje.getAsientosDisponibles() > 0) {
            viaje.setAsientosDisponibles(viaje.getAsientosDisponibles() - 1);
            repositorioViaje.actualizar(viaje);
        } else {
            throw new RuntimeException("El viaje seleccionado no posee asientos disponibles.");
        }
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
    public void crearReserva(Long idViaje, Usuario usuario, String asiento) {
        Viaje viaje = repositorioViaje.buscarPorId(idViaje);
        if(viaje == null) throw new RuntimeException("El viaje no existe");

        Reserva reserva = new Reserva();
        reserva.setViaje(viaje);
        reserva.setUsuario(usuario);
        reserva.setAsientos(asiento != null ? asiento : "No especificados");
        reserva.setPrecioTotal(viaje.getPrecio());
        reserva.setEstadoReserva(EstadoReserva.CONFIRMADA);

        viaje.setAsientosDisponibles(viaje.getAsientosDisponibles() - 1);

        repositorioViaje.guardarViaje(reserva.getViaje());
        repositorioReserva.guardar(reserva);
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