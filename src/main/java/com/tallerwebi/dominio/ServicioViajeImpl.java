package com.tallerwebi.dominio;

public class ServicioViajeImpl implements ServicioViaje {

    private RepositorioViaje repositorioViaje;

    public ServicioViajeImpl(RepositorioViaje repositorio) {
        this.repositorioViaje = repositorio;
    }

    @Override
    public void confirmarViaje(Viaje viaje) {
        repositorioViaje.guardarViaje(viaje);
    }
}
