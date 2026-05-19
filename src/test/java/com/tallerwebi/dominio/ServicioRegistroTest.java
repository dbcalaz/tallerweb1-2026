package com.tallerwebi.dominio;

import org.junit.jupiter.api.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServicioRegistroTest {



    ServicioRegistro servicioRegistro = new ServicioRegistroimpl();
    private final String email ="test@test.com";
    private final String passwordInvalida ="1234";
    private final String password="123456";

    @Test
    public void siIngresoEmailYPasswordElRegistroEsExitoso(){

     givenUsuarioNoExiste();
     Usuario usuarioCreado = whenRegistroUsuario(email,password);
     thenElRegistroEsExitoso(usuarioCreado);


    }
    @Test
    public void registroFallaSiLaPasswprdTieneMenosDe6Caracteres(){

        givenUsuarioNoExiste();
        //primero va lo que espero y el segundo parametro es el ejecutable osea
        // la funcion que voy a llamar y espero que falle

        assertThrows(PasswordInvalidaException.class, ()-> whenRegistroUsuario(email,passwordInvalida));
      //  Usuario usuarioCreado = whenRegistroUsuario(email,passwordInvalida);
       // thenElRegistroFalla(usuarioCreado);
    }

    private void thenElRegistroFalla(Usuario usuarioCreado) {
        assertThat(usuarioCreado,is(nullValue()));
    }

    private void thenElRegistroEsExitoso(Usuario usuarioCreado) {
        assertThat(usuarioCreado,is(notNullValue()));
    }

    private Usuario whenRegistroUsuario(String email, String password) {
       Usuario usuario= servicioRegistro.registrar(email,password);
       return usuario;
    }

    private void givenUsuarioNoExiste() {
    }


}
