package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioViaje {

    void guardarViaje(Viaje viaje);

    List<Viaje> buscarViajes(Long idOrigen, Long idDestino, String fecha, Integer pasajeros);

    List<Parada> obtenerTodasLasParadas();

    Viaje buscarPorId(Long id);

    void actualizar(Viaje viaje);

    Long contarViajesPorUsuario(long idUsuario);

    Long contarViajesCanceladosPorUsuario(long idUsuario);

    List<Viaje> buscarUltimosViajesDelUsuario(Long idUsuario);

    void guardarReserva(Reserva reserva);

    List<Integer> obtenerAsientosOcupados(Long idViaje);

    void eliminarReserva(Long idReserva);

    List<Reserva> buscarReservasPorUsuario(Long idUsuario);
}