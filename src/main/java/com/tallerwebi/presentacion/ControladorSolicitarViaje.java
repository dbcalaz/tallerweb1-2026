package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorSolicitarViaje {

  @RequestMapping(path = "/solicitarViaje")
  public ModelAndView solicitarViaje() {
    ModelMap modelo = new ModelMap();
    modelo.put("solicitud", modelo);
    return new ModelAndView("solicitarViaje", modelo);
  }
}
