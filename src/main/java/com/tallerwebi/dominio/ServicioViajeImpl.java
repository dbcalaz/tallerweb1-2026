package com.tallerwebi.dominio;

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
    public ServicioViajeImpl(RepositorioViaje repositorio,  RepositorioReserva repositorioReserva) {
        this.repositorioViaje = repositorio;
        this.repositorioReserva = repositorioReserva;
    }

    @Override
    public void confirmarViaje(Viaje viaje) {
        repositorioViaje.guardarViaje(viaje);
    }

    @Override
    public List<Viaje> buscarViajes(String origen, String destino, String fecha) {
        return repositorioViaje.buscarViajes(origen, destino, fecha);
    }

    @Override
    public void reservarAsiento(Long idViaje, Usuario usuarioLogueado, String asientosSeleccionados) {
        Viaje viaje = repositorioViaje.buscarPorId(idViaje);
        if (viaje != null && viaje.getAsientosDisponibles() > 0) {
            viaje.setAsientosDisponibles(viaje.getAsientosDisponibles() - 1);
            repositorioViaje.actualizar(viaje);
        } else {
            throw new RuntimeException("El viaje no existe o no tiene asientos disponibles.");
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

        //descuenta asiento
        viaje.setAsientosDisponibles(viaje.getAsientosDisponibles() - 1);

        repositorioViaje.guardarViaje(reserva.getViaje());
        repositorioReserva.guardar(reserva);

    }

}