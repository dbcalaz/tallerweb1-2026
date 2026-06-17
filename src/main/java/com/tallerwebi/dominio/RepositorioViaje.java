package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioViaje {
    void guardarViaje(Viaje viaje);
    List<Viaje> buscarViajes(String origen, String destino, String fecha, Integer pasajeros);
    Viaje buscarPorId(Long id);
    void actualizar(Viaje viaje);
    List<Viaje> buscarUltimosViajesDelUsuario(Long idUsuario);

    void guardarReserva(Reserva reserva);
    List<Integer> obtenerAsientosOcupados(Long idViaje);
    void eliminarReserva(Long idReserva);
}