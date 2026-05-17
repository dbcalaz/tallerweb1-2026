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
    if (origen.isEmpty()){
      modelo.put("error", "El punto de origen es obligatorio");
      return new ModelAndView("solicitarViaje", modelo);
    }
    if (destino.isEmpty()){
      modelo.put("error", "El punto de destino es obligatorio");
      return new ModelAndView("solicitarViaje", modelo);
    }
    return new ModelAndView("viajeEnCurso", modelo);
  }

  @RequestMapping("/viajeEnCurso")
  public ModelAndView viajeEnCurso() {
    return new ModelAndView("viajeEnCurso");
  }

}
