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

    @Autowired
    public ServicioPerfilUsuarioImpl(RepositorioUsuario repositorioUsuario,  RepositorioReserva repositorioReserva) {
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioReserva = repositorioReserva;
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

}
