package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioPuntuacionImpl implements ServicioPuntuacion {

    private RepositorioPuntuacion repositorioPuntuacion;

    @Autowired
    public void getRepositorioPuntuacionImpl(RepositorioPuntuacion  repositorioPuntuacion) {
        this.repositorioPuntuacion = repositorioPuntuacion;
    }

    @Override
    public boolean yaPuntuo(Long idUsuario, Long idReserva) {
        return repositorioPuntuacion.existePuntuacioPorUsuarioYReserva(idUsuario, idReserva);
    }
}
