package com.tallerwebi.dominio;

public interface RepositorioPuntuacion {

    void guardarPuntuacion(Puntuacion puntuacion);


    boolean existePuntuacioPorUsuarioYReserva(Long idUsuario, Long idReserva);

    void guardar(Puntuacion puntuacion);

    Double obtenerPromedioPorConductor(Long id);
}
