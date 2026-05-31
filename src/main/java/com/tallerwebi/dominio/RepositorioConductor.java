package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioConductor {

    Conductor buscarConductor(String email, String password);

    void guardarConductor(Conductor conductor);

    List<Viaje> obtenerViajesPorConductor(Long idConductor);
}
