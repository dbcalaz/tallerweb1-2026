package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DatosBusqueda;
import java.time.LocalTime;
import java.util.List;

public interface ServicioViaje {
    void confirmarViaje(Viaje viaje);
    List<Viaje> buscarViajes(DatosBusqueda datosBusqueda);
    List<Parada> obtenerTodasLasParadas();
    void verificarViajes24Horas(Long idViaje);
    void reservarAsiento(Long idViaje, Usuario usuarioLogueado);
    void liberarAsiento(Long idViaje);
    Viaje buscarPorId(Long id);
    void guardarReserva(Reserva reserva);
    List<Integer> obtenerAsientosOcupados(Long idViaje);
    void eliminarReserva(Long idReserva);

    // Método actualizado
    List<Reserva> buscarReservasPorEstado(Long idUsuario, EstadoReserva estado);

    double calcularPrecioPorTramo(Viaje viaje, Long idOrigen, Long idDestino);
    LocalTime calcularHorarioParada(Viaje viaje, ViajeParada viajeParada);
}