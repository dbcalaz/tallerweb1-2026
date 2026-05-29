package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

@Service
public class ServicioConductorImpl implements ServicioConductor {

    private RepositorioConductor repositorioConductor;

    public ServicioConductorImpl(RepositorioConductor repositorioConductor){
        this.repositorioConductor = repositorioConductor;
    }

    @Override
    public Conductor consultarConductor(String email, String password) {
        return repositorioConductor.buscarConductor(email,password);
    }

    @Override
    public void registrarConductor(Conductor conductor) {
        repositorioConductor.guardarConductor(conductor);
    }
}
