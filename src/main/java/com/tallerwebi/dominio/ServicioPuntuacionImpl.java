package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioPuntuacionImpl implements ServicioPuntuacion {

    private RepositorioPuntuacion repositorioPuntuacion;
    private RepositorioConductor repositorioConductor;

    @Autowired
    public ServicioPuntuacionImpl(RepositorioPuntuacion repositorioPuntuacion,
                                  RepositorioConductor repositorioConductor) {
        this.repositorioPuntuacion = repositorioPuntuacion;
        this.repositorioConductor = repositorioConductor;
    }

    @Override
    public boolean yaPuntuo(Long idUsuario, Long idReserva) {
        return repositorioPuntuacion.existePuntuacioPorUsuarioYReserva(idUsuario, idReserva);
    }

    public void calificarConductor(Reserva reserva, Usuario usuario, Integer puntos) {
        if (puntos == null || puntos < 0 || puntos > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 0 y 5");
        }

        Puntuacion puntuacion = new Puntuacion();
        puntuacion.setUsuario(usuario);
        puntuacion.setReserva(reserva);
        puntuacion.setConductor(reserva.getViaje().getConductor());
        puntuacion.setPuntos(puntos);

        repositorioPuntuacion.guardar(puntuacion);

        actualizarPromedioConductor(reserva.getViaje().getConductor());
    }

    private void actualizarPromedioConductor(Conductor conductor) {
        Double promedio = repositorioPuntuacion.obtenerPromedioPorConductor(conductor.getId());
        conductor.setCalificacion(promedio.floatValue());
        repositorioConductor.actualizarConductor(conductor);
    }
}
