package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioReserva {

    void guardar(Reserva reserva);

    List<Reserva> buscarUltimasReservasPorUsuario(long idUsuario);

    Conductor obtenerConductorFavorito(Long id);
}
