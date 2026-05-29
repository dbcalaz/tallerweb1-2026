package com.tallerwebi.dominio;

public interface RepositorioConductor {

    Conductor buscarConductor(String email, String password);

    void guardarConductor(Conductor conductor);
}
