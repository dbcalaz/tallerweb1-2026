package com.tallerwebi.dominio;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ServicioRegistroTest {

    //el registro es exitoso si se ingresa mail y contraseña
    //el registro falla si la contraseña tiene menos de 6 caracteres
    private final String mail = "mmmm@mmmm.com";
    private final String password = "1234567";
    private final String passworMala = "1234";

    ServicioRegistro servicioRegistro = new ServicioRegistroImpl();

    @Test
    public void siIngresoMailYContraseñaRegistroExitoso(){
        givenUsuarioNoExiste();
        Usuario usuarioCreado = whenRegistroUsuario(mail, password);
        thenElRegistroEsExitoso(usuarioCreado);

    }

    @Test
    public void registroFallaSiLaPasswordTieneMenosDe6Caracteres(){
        givenUsuarioNoExiste();
        Usuario usuarioCreado = whenRegistroUsuario(mail, passworMala);
        thenElRegistroFalla(usuarioCreado);
    }

    private void thenElRegistroFalla(Usuario usuarioCreado) {
        assertThat(usuarioCreado, is(nullValue()));
    }

    private void thenElRegistroEsExitoso(Usuario usuarioCreado) {
        assertThat(usuarioCreado, is(notNullValue()));
    }

    private Usuario whenRegistroUsuario(String mail, String password) {

        Usuario usuario = servicioRegistro.registrar(mail,  password);
        return usuario;
    }

    private void givenUsuarioNoExiste() {
    }
}
