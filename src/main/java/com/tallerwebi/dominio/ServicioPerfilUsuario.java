package com.tallerwebi.dominio;


import java.util.List;

public interface ServicioPerfilUsuario {

    Usuario buscarPorId(Long id);
    List<Reserva> obtenerReservasPorUsuario(Long idUsuario);

    Long obtenerCantidaddeViajes(Long idUsuario);

    Long obtenerCantidadViajesCancelados(Long idUsuario);

    Reserva buscarReservaPorId(Long id);

    Long obtenerCantidadDeviajesPorEstadoPorUsuario(Long id, EstadoReserva estadoReserva);

    void cancelarReserva(Reserva reserva);
}
