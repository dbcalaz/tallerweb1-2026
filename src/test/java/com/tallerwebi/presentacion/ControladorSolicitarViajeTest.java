package com.tallerwebi.presentacion;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class ControladorSolicitarViajeTest {

  private final String origen = "oliden 1543";
  private final String destino = "estacion merlo";
  ControladorSolicitarViaje controladorSolicitarViaje = new ControladorSolicitarViaje();

  @Test
  public void siSeIngresaOrigenydestinoElPedidoEsExitoso(){
    givenSeCreaViaje();
    DatosViaje datosViaje = new DatosViaje(origen, destino);
    ModelAndView modelAndView = whenIniciaViaje(datosViaje);
    thenElPedidoEsExitoso(modelAndView);
  }

  private void thenElPedidoEsExitoso(ModelAndView modelAndView) {
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("viajeEnCurso"));

  }

  private ModelAndView whenIniciaViaje(DatosViaje datosViaje) {
    ModelAndView mav = controladorSolicitarViaje.solicitarViaje(datosViaje);
    return mav;
  }

  private void givenSeCreaViaje() {
  }

  @Test
  public void siNoSeIngresaOrigenyDestinoLaSolicitudNoEsExitosa(){
    givenSeCreaViaje();
    DatosViaje datosViaje = new DatosViaje("", "");
    ModelAndView modelAndView = whenIniciaViaje(datosViaje);
    thenLaSolicitudNoEsExitosa(modelAndView);
  }

  private void thenLaSolicitudNoEsExitosa(ModelAndView modelAndView) {
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("solicitarViaje"));
  }

  @Test
  public void queSePuedaConfirmarUnaSolicitudDeViaje(){
    givenSeCreaViaje();
    DatosViaje datosViaje = new DatosViaje(origen, destino);
    ModelAndView modelAndView = whenSeConfirmaViaje(datosViaje);
    thenLaConfirmacionEsExitosa(modelAndView);
  }

    private ModelAndView whenSeConfirmaViaje(DatosViaje datosViaje) {
        ModelAndView mav = controladorSolicitarViaje.confirmarViaje(datosViaje);
        return mav;
    }

  private void thenLaConfirmacionEsExitosa(ModelAndView modelAndView) {
      assertThat( modelAndView.getViewName(), equalToIgnoringCase("home"));
    }

}
