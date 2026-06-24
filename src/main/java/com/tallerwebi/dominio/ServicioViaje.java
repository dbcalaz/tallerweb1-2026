package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioViaje {
    void confirmarViaje(Viaje viaje);
    List<Viaje> buscarViajes(String origen, String destino, String fecha);
    void reservarAsiento(Long idViaje, Usuario usuarioLogueado, String asientosSeleccionados);
    Viaje buscarPorId(Long id);

    void crearReserva(Long idViaje, Usuario usuario, String asiento);


    List<Parada> obtenerTodasLasParadas();
}