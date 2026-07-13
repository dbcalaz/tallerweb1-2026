package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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

    @Override
    public void calificarConductor(Reserva reserva, Usuario usuario, Integer puntos) {
        if (puntos == null || puntos < 1 || puntos > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }

        Puntuacion puntuacion = new Puntuacion();
        puntuacion.setUsuario(usuario);
        puntuacion.setReserva(reserva);
        puntuacion.setConductor(reserva.getViaje().getConductor());
        puntuacion.setPuntos(puntos);

        repositorioPuntuacion.guardar(puntuacion);

        actualizarPromedioConductor(reserva.getViaje().getConductor());
    }

    @Override
    public void actualizarPromedioConductor(Conductor conductor) {

        Double promedio =repositorioPuntuacion.obtenerPromedioPorConductor(conductor.getId());

        if(promedio != null){
            double promedioRedondeado = Math.round(promedio * 10.0) / 10.0;

            conductor.setCalificacion((float) promedioRedondeado);
            repositorioConductor.actualizarConductor(conductor);
        }
    }
}
