package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.ServicioAdministrador;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioAdministradorImpl implements ServicioAdministrador {

    RepositorioAdministrador repositorioAdministrador;

    public ServicioAdministradorImpl(RepositorioAdministrador repositorioAdministrador) {
        this.repositorioAdministrador = repositorioAdministrador;
    }

    @Override
    public List<ReporteFalla> obtenerFallasDeCombis() {
        return repositorioAdministrador.getFallas();
    }
}
