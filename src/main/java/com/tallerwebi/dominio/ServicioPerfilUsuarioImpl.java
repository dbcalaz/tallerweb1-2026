package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public ServicioPerfilUsuarioImpl(RepositorioUsuario repositorioUsuario, RepositorioReserva repositorioReserva) {
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return repositorioUsuario.burscarPorId(id);
    }

    @Override
    public List<Reserva> obtenerReservasPorUsuario(Long idUsuario) {
        return repositorioReserva.buscarUltimasReservasPorUsuario(idUsuario);
    }

    @Override
    public Conductor obtenerConductorFavorito(Long idUsuario) {

        List<Reserva> reservas = repositorioReserva.buscarUltimasReservasPorUsuario(idUsuario);
        return repositorioReserva.obtenerConductorFavorito(idUsuario);
    }

    @Override
    public Long obtenerCantidaddeViajes(Long idUsuario){
        return repositorioViaje.contarViajesPorUsuario(idUsuario);
    }

    @Override
    public Long obtenerCantidadViajesCancelados(Long idUsuario){
        return repositorioViaje.contarViajesCanceladosPorUsuario(idUsuario);
    }

}
