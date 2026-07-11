package com.tallerwebi.dominio;

public interface ServicioPuntuacion {

    boolean yaPuntuo(Long idUsuario, Long idReserva);

    void calificarConductor(Reserva reserva, Usuario usuario, Integer puntos);

    void actualizarPromedioConductor(Conductor conductor);
}
