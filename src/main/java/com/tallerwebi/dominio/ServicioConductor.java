package com.tallerwebi.dominio;

public interface ServicioConductor {


    Conductor consultarConductor(String email, String password);

    void registrarConductor(Conductor conductor);
}
