package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorSolicitarViaje {

  @RequestMapping("/solicitarViaje")
  public ModelAndView solicitarViaje() {
    return new ModelAndView("solicitarViaje");
  }

  public ModelAndView solicitarViaje(String origen, String destino) {
    ModelMap modelo = new ModelMap();
    return new ModelAndView("solicitarViaje", modelo);
  }
}
