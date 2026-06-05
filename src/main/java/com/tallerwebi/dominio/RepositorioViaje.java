package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioViaje {
    void guardarViaje(Viaje viaje);
    List<Viaje> buscarViajes(String origen, String destino, String fecha);
    Viaje buscarPorId(Long id);
    void actualizar(Viaje viaje);


}