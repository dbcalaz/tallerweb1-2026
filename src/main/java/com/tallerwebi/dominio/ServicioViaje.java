package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DatosBusqueda;
import java.util.List;

public interface ServicioViaje {
    void confirmarViaje(Viaje viaje);

    List<Viaje> buscarViajes(DatosBusqueda datosBusqueda);
    List<Parada> obtenerTodasLasParadas();

    // Metodo para poder verificar tanto el cupo como el chofer
    void verificarViajes24Horas(Long idViaje);

    void reservarAsiento(Long idViaje, Usuario usuarioLogueado);
    void liberarAsiento(Long idViaje);

    Viaje buscarPorId(Long id);
    void guardarReserva(Reserva reserva);
    List<Integer> obtenerAsientosOcupados(Long idViaje);
    void eliminarReserva(Long idReserva);
}