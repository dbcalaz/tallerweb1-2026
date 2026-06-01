package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ConductorExistente;

import java.util.List;

public interface ServicioConductor {

    Conductor consultarConductor(String email, String password);

    void registrarConductor(Conductor conductor) throws ConductorExistente;

    List<Viaje> obtenerViajesDelConductor(Long idConductor);
}
