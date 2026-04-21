package com.tallerwebi.presentacion;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class ControladorRegistroTest {

    private final String mail = "juan@mmm.com";
    private final String password ="1234";

    ControladorRegistro controladorRegistro = new ControladorRegistro();

    @Test
    public void siSeIngresaEmailYPasswordElRegistroEsExitoso(){

        //Presentacion
        givenNoExisteUsuario();
        //ejecucion
        ModelAndView modelAndView = whenRegistroUsuario(mail,password);
        //comprobacion
        thenElRegistroEsExitoso(modelAndView);

    }

    private void thenElRegistroEsExitoso(ModelAndView modelAndView) {
        assertThat( modelAndView.getViewName(), equalToIgnoringCase( "login"));
        assertThat(modelAndView.getModel().get("mensaje").toString(), equalToIgnoringCase("el registro fue exitoso"));
    }

    private ModelAndView whenRegistroUsuario(String mail, String password) {
        ModelAndView mav = controladorRegistro.registrar(mail, password);
        return mav;
    }

    private void givenNoExisteUsuario() {
    }
}
