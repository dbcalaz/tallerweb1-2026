package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorSolicitarViaje {

  @RequestMapping("/solicitarViaje")
  public ModelAndView solicitarViaje() {
    DatosViaje datosViaje = new DatosViaje();
    ModelMap model = new ModelMap();
    model.put("datosViaje", datosViaje);
    return new ModelAndView("solicitarViaje", model);
  }

  @RequestMapping(path = "/viajeEnCurso", method = RequestMethod.POST)
  public ModelAndView solicitarViaje(@ModelAttribute("datosViaje")  DatosViaje datosViaje) {
    ModelMap modelo = new ModelMap();
    if (datosViaje.getOrigen().isEmpty()){
      modelo.put("error", "El punto de origen es obligatorio");
      modelo.put("datosViaje", datosViaje);
      return new ModelAndView("solicitarViaje", modelo);
    }
    if (datosViaje.getDestino().isEmpty()){
      modelo.put("error", "El punto de destino es obligatorio");
      modelo.put("datosViaje", datosViaje);
      return new ModelAndView("solicitarViaje", modelo);
    }
    modelo.put("datosViaje", datosViaje);
    return new ModelAndView("viajeEnCurso", modelo);
  }

  @RequestMapping("/viajeEnCurso")
  public ModelAndView viajeEnCurso() {
    ModelMap modelo = new ModelMap();
    modelo.put("datosViaje", new DatosViaje());
    return new ModelAndView("viajeEnCurso", modelo);
  }

}
