package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ConductorExistente;

import java.util.List;

public interface ServicioConductor {

    Conductor consultarConductor(String email, String password);

    void registrarConductor(Conductor conductor) throws ConductorExistente;

    List<Viaje> obtenerViajesDelConductor(Long idConductor);

    //Todos los viajes con estado = PENDIENTE
    List<Viaje> obtenerViajesPendientesDelConductor(Long idConductor);

    //Todos los viajes con estado = FINALIZADO
    List<Viaje> obtenerViajesFinalizadosDelConductor(Long idConductor);

    Combi buscarCombiActivePorIdConductor(Long id);
}
