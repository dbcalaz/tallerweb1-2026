package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioRegistroImpl implements ServicioRegistro {
    @Override
    public void registrar() {

    }

    @Override
    public void registrar(String mail, String password) {

    }
}
