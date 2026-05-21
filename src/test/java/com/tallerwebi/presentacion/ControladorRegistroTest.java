package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.mock;

public class ControladorRegistroTest {

  private final String mail = "juan@mmm.com";
  private final String password = "1234";
  private final String repitePassword = "1234";
  private DatosRegistroDTO datosRegistroDTO;

  private ServicioLogin servicioLoginMock;
  private ControladorRegistro controladorRegistro;

  @BeforeEach
  public void init() {
    datosRegistroDTO = new DatosRegistroDTO(mail, password, repitePassword);

    servicioLoginMock = mock(ServicioLogin.class);

    controladorRegistro = new ControladorRegistro(servicioLoginMock);
  }

  @Test
  public void siSeIngresaEmailYPasswordElRegistroEsExitoso() {
    givenNoExisteUsuario();

    ModelAndView modelAndView = whenRegistroUsuario(mail, password);

    thenElRegistroEsExitoso(modelAndView);
  }

  private void thenElRegistroEsExitoso(ModelAndView modelAndView) {
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
    assertThat(
            modelAndView.getModel().get("mensaje").toString(),
            equalToIgnoringCase("El registro fue exitoso")
    );
  }

  private ModelAndView whenRegistroUsuario(String mail, String password) {
    return controladorRegistro.registrar(datosRegistroDTO);
  }

  private void givenNoExisteUsuario() {
  }
}