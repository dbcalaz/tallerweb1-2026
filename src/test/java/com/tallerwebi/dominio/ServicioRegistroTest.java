package com.tallerwebi.dominio;

import org.junit.jupiter.api.Test;

public class ServicioRegistroTest {

    //el registro es exitoso si se ingresa mail y contraseña
    //el registro falla si la contraseña tiene menos de 6 caracteres
    private final String mail = "mmmm@mmmm.com";
    private final String password = "1234";

    ServicioRegistro servicioRegistro = new ServicioRegistroImpl();

    @Test
    public void siIngresoMailYContraseñaRegistroExitoso(){
        givenUsuarioNoExiste();
        whenRegistroUsuario();
        thenElRegistroEsExitoso();

    }

    private void thenElRegistroEsExitoso() {
    }

    private void whenRegistroUsuario() {
        servicioRegistro.registrar(mail, password);
    }

    private void givenUsuarioNoExiste() {
    }
}
