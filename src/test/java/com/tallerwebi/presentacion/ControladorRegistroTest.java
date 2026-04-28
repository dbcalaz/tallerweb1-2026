package com.tallerwebi.presentacion;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class ControladorRegistroTest {

   // private final String mail = "juan@mmm.com";
   // private final String password ="1234";

    ControladorRegistro controladorRegistro = new ControladorRegistro();

    @Test
    public void siSeIngresaEmailYPasswordElRegistroEsExitoso(){
        DatosRegistroDTO datos = new DatosRegistroDTO("juan@mmm.com","1234", "1234");

        //Presentacion
        givenNoExisteUsuario();
        //ejecucion
        ModelAndView modelAndView = whenRegistroUsuario(datos);
        //comprobacion
        thenElRegistroEsExitoso(modelAndView);

    }

    private void thenElRegistroEsExitoso(ModelAndView modelAndView) {
        assertThat( modelAndView.getViewName(), equalToIgnoringCase( "login"));
        assertThat(modelAndView.getModel().get("mensaje").toString(), equalToIgnoringCase("el registro fue exitoso"));
    }

    private ModelAndView whenRegistroUsuario(DatosRegistroDTO datos) {
        ModelAndView mav = controladorRegistro.registrar(datos);
        return mav;
    }

    private void givenNoExisteUsuario() {
    }

    @Test
    public void elRegistroFallaSiNoIngresoMail(){
        DatosRegistroDTO datos = new DatosRegistroDTO("","1234","1234");
        givenNoExisteUsuario();
        ModelAndView modelAndView = whenRegistroUsuario(datos);
        thenElRegistroFalla(modelAndView, "El email es obligatorio");
    }

    private void thenElRegistroFalla(ModelAndView modelAndView, String mensaje){
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("registro"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase(mensaje));
    }

    @Test
    public void elRegistroFallaSiNoIngresoPassword(){
        DatosRegistroDTO datos = new DatosRegistroDTO("juan@mmm.com","","");
        givenNoExisteUsuario();
        ModelAndView modelAndView = whenRegistroUsuario(datos);
        thenElRegistroFalla(modelAndView, "El password es obligatorio");
    }

    @Test
    public void elRegistroFallaSiNoSeRepiteIngresoPassword(){
        DatosRegistroDTO datos = new DatosRegistroDTO("juan@mmm.com","1234","");
        givenNoExisteUsuario();
        ModelAndView modelAndView = whenRegistroUsuario(datos);
        thenElRegistroFalla(modelAndView, "El password debe coincidir");
    }


}
