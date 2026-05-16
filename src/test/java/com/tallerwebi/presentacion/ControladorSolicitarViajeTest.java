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
    ModelAndView modelAndView = whenIniciaViaje(origen, destino);
    thenElPedidoEsExitoso(modelAndView);
  }

  private void thenElPedidoEsExitoso(ModelAndView modelAndView) {
    assertThat(modelAndView.getViewName(), equalToIgnoringCase("solicitarViaje"));

  }

  private ModelAndView whenIniciaViaje(String origen, String destino) {
    ModelAndView mav = controladorSolicitarViaje.solicitarViaje(origen, destino);
    return mav;
  }

  private void givenSeCreaViaje() {
  }

}
