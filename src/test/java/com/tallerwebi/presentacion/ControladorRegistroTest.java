package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

public class ControladorRegistroTest {

  private final String mail = "juan@mmm.com";
  private final String password = "1234";
  private final String repitePassword = "1234";
  DatosRegistroDTO datosRegistroDTO = new DatosRegistroDTO(mail, password, repitePassword);

  ControladorRegistro controladorRegistro = new ControladorRegistro();

  @Test
  public void siSeIngresaEmailYPasswordElRegistroEsExitoso() {
    //Presentacion
    givenNoExisteUsuario();
    //ejecucion
    ModelAndView modelAndView = whenRegistroUsuario(mail, password);
    //comprobacion
    thenElRegistroEsExitoso(modelAndView);
  }

  private void thenElRegistroEsExitoso(ModelAndView modelAndView) {
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
    assertThat(
      modelAndView.getModel().get("mensaje").toString(),
      equalToIgnoringCase("el registro fue exitoso")
    );
  }

  private ModelAndView whenRegistroUsuario(String mail, String password) {
    ModelAndView mav = controladorRegistro.registrar(datosRegistroDTO);
    return mav;
  }

  private void givenNoExisteUsuario() {}
}
