package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DatosBusqueda;
import java.util.List;

public interface ServicioViaje {
    void confirmarViaje(Viaje viaje);

    List<Viaje> buscarViajes(DatosBusqueda datosBusqueda);

    void reservarAsiento(Long idViaje, Usuario usuarioLogueado);
    Viaje buscarPorId(Long id);

    // void crearReserva(Long idViaje, Usuario usuarioLogueado, String asientosSeleccionados);
}