package com.tallerwebi.dominio;


import java.util.List;

public interface ServicioPerfilUsuario {

    Usuario buscarPorId(Long id);
    List<Reserva> obtenerReservasPorUsuario(Long idUsuario);

    Conductor obtenerConductorFavorito(Long id);

    Long obtenerCantidaddeViajes(Long idUsuario);

    Long obtenerCantidadViajesCancelados(Long idUsuario);
}
