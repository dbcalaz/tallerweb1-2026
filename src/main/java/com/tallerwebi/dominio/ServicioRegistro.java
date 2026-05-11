package com.tallerwebi.dominio;

public interface ServicioRegistro {
    void registrar();

    Usuario registrar(String mail, String password);
}
