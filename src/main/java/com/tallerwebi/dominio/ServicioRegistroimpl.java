package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioRegistroimpl implements ServicioRegistro {


    @Override
    public Usuario registrar(String email, String password) {

        if(password.length() < 6) {
            throw new PasswordInvalidaException();
        }
        return new Usuario();
    }
}
