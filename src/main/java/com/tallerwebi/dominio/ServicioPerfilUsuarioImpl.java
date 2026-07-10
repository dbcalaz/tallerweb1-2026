package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioPerfilUsuarioImpl implements ServicioPerfilUsuario {

    private RepositorioUsuario repositorioUsuario;
    private RepositorioReserva repositorioReserva;
    private RepositorioViaje repositorioViaje;

    @Autowired
    public ServicioPerfilUsuarioImpl(RepositorioUsuario repositorioUsuario,  RepositorioReserva repositorioReserva,  RepositorioViaje repositorioViaje) {
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioReserva = repositorioReserva;
        this.repositorioViaje = repositorioViaje;
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return repositorioUsuario.burscarPorId(id);
    }

    @Override
    public List<Reserva> obtenerReservasPorUsuario(Long idUsuario) {
        List<Reserva> reservas = repositorioReserva.buscarUltimasReservasPorUsuario(idUsuario);

        return reservas != null ? reservas : new ArrayList<>();
    }

    @Override
    public Long obtenerCantidaddeViajes(Long idUsuario){
        return repositorioViaje.contarViajesPorUsuario(idUsuario);
    }

    @Override
    public Long obtenerCantidadViajesCancelados(Long idUsuario){
        return repositorioViaje.contarViajesCanceladosPorUsuario(idUsuario);
    }

    @Override
    public Reserva buscarReservaPorId(Long id){
        return repositorioReserva.buscarReservaPorId(id);
    }

    @Override
    public Long obtenerCantidadDeviajesPorEstadoPorUsuario(Long id, EstadoReserva estadoReserva) {
        Usuario usuario = buscarPorId(id);

        if ( usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        return repositorioUsuario.getCantidadDeViajesPorEstado(usuario, estadoReserva);
    }

    @Override
    public void cancelarReserva(Reserva reserva) {

        if (reserva == null) {
            throw new RuntimeException("Reserva inexistente");
        }

        if (!EstadoReserva.CONFIRMADA.equals(reserva.getEstadoReserva())) {
            throw new RuntimeException("La reserva no puede cancelarse");
        }

        Viaje viaje = reserva.getViaje();

        Integer cantidadPasajeros = reserva.getPasajeros().size();

        viaje.setAsientosDisponibles(viaje.getAsientosDisponibles() + cantidadPasajeros);

        reserva.setEstadoReserva(EstadoReserva.CANCELADA);

        repositorioViaje.actualizar(viaje);
        repositorioReserva.actualizarEstadoReserva(reserva);
    }
}
