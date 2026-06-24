package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioViaje {
    void guardarViaje(Viaje viaje);

    // Ahora busca por ID relacional, no por texto
    List<Viaje> buscarViajes(Long idOrigen, Long idDestino, String fecha, Integer pasajeros);

    // Para cargar los lugares en el desplegable
    List<Parada> obtenerTodasLasParadas();

    Viaje buscarPorId(Long id);
    void actualizar(Viaje viaje);

    Long contarViajesPorUsuario(long idUsuario);
    Long contarViajesCanceladosPorUsuario(long idUsuario);
    List<Viaje> buscarUltimosViajesDelUsuario(Long idUsuario);

    void guardarReserva(Reserva reserva);
    List<Integer> obtenerAsientosOcupados(Long idViaje);
    void eliminarReserva(Long idReserva);
}