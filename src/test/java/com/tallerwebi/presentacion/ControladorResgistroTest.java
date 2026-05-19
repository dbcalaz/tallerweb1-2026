package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import com.tallerwebi.dominio.PasswordInvalidaException;
import com.tallerwebi.dominio.ServicioRegistro;
import com.tallerwebi.dominio.ServicioRegistroimpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

public class ControladorResgistroTest {

  /*
   * 1-el rsgitro es exitoso si se ingresa email y pasword
   * 2- el resgitro falla si no ingreso mail
   * 3- el registro falla si no ingreso pasword
   * 4- el registro falla si la password y la repeticion de password no coincides
   * 5- el registro falla si el mail no tiene formato valido
   *
   *
   * 6- el registro falla si la contrasenia tiene menos de 6 caracteres
   * 7- el resgistro falla si ya existe un usuario con el mismo mail
   * */

  ServicioRegistro servicioRegistro = mock(ServicioRegistroimpl.class);
  ControladorRegistro controladorRegistro = new ControladorRegistro(servicioRegistro);

  @Test
  public void siSeIngresaEmailYPasswordElRegistroEsExitoso() {
    DatosLogin usuario = new DatosLogin("javi@live.com", "1111", "1111");
    //preparacion uso del GIVEN
    givenNoExisteUsuario();

    //Ejecucion uso del WHEN
    ModelAndView modeloRecibido = whenRegistroUsuario(usuario);

    // Comprobacion uso del THEN
    thenElRegistroEsExitoso(modeloRecibido);
  }

  private void thenElRegistroEsExitoso(ModelAndView modelAndView) {
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
    assertThat(
      modelAndView.getModel().get("mensaje").toString(),
      equalToIgnoringCase("el registro fue exitoso")
    );
  }

  private ModelAndView whenRegistroUsuario(DatosLogin datosLoginDTO) {
    ModelAndView mav = controladorRegistro.registrar(datosLoginDTO);
    return mav;
  }

  private void givenNoExisteUsuario() {}

  @Test
  public void siNoIngresoMailElRegistroFalla() {
    DatosLogin usuario = new DatosLogin("", "1234", "1234");
    //preparacion given
    givenNoExisteUsuario();

    //ejecucion   when
    ModelAndView modelAndView = whenRegistroUsuario(usuario);

    //comprobacion then
    thenElRegistroFalla(modelAndView, "el email es obligatorio");
  }

  /*private void thenElRegistroFalla(ModelAndView modelAndView) {
     assertThat( modelAndView.getViewName(),equalToIgnoringCase("registro"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("el email es obligatorio "));
    }*/

  private void thenElRegistroFalla(ModelAndView modelAndView, String mensaje) {
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("registro"));
    assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase(mensaje));
  }

  @Test
  public void sinoIngresoPasswordElRegistroFalla() {
    DatosLogin usuario = new DatosLogin("javi@live.com.ar", "", "");
    givenNoExisteUsuario();
    ModelAndView modelAndView = whenRegistroUsuario(usuario);
    thenElRegistroFalla(modelAndView, "el password es obligatorio");
  }

  @Test
  public void siSeIngresaLaPassWordYSuRepeticionNoCoincidenElRegistroFalla() {}
  /*@Test
  public void siSeIngresaUnEmailNoValidoElRegistroFalla() {
    DatosLogin usuario = new DatosLogin("javilive.com", "1111", "1234");
    givenNoExisteUsuario();
    ModelAndView modelAndView = whenRegistroUsuario(usuario);
    thenElRegistroFalla(modelAndView, "el email debe contender @");


  }*/

  @Test
  public void elRegistroFallaSiLaPasswordTieneMenosDe6Caracteres(){
    givenNoExisteUsuario();
    //seteo el comportamiento de  mi mock servicio registro le indico lo que quiero que haga
    //con su metodo de mockito
    //le digo que tire esa exception cuando mi mockito use el metodo registrar
    doThrow(PasswordInvalidaException.class).when(servicioRegistro).registrar("javi@gmail","1234");

    DatosLogin datosLogin = new DatosLogin("javi@gmail","1234");
    ModelAndView modelAndView= whenRegistroUsuario(datosLogin);
    thenElRegistroFalla( modelAndView, "La contraseña debe tener al menos de 6 caracteres");
  }
}
