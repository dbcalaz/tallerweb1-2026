package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioViaje {
    void confirmarViaje(Viaje viaje);
    List<Viaje> buscarViajes(String origen, String destino, String fecha);
    void reservarAsiento(Long idViaje, Usuario usuarioLogueado);
    Viaje buscarPorId(Long id);
}