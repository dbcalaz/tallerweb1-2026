package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorRegistro {

  @RequestMapping(path = "/nuevo-usuario", method = RequestMethod.GET)
  public ModelAndView nuevoUsuario() {
    ModelMap model = new ModelMap();
    model.put("datosRegistro", new DatosRegistroDTO());
    return new ModelAndView("nuevo-usuario", model);
  }

  @RequestMapping(path = "/registro", method = RequestMethod.POST)
  public ModelAndView registrar(@ModelAttribute("datosRegistro") DatosRegistroDTO datosRegistro) {
    ModelMap model = new ModelMap();
    if (datosRegistro.getMail().isEmpty()) {
      model.put("error", "El email es obligatorio");
      return new ModelAndView("nuevo-usuario", model);
    }
    if (datosRegistro.getPassword().isEmpty()) {
      model.put("error", "El password es obligatorio");
      return new ModelAndView("nuevo-usuario", model);
    }
    if (!datosRegistro.getRepitePassword().equals(datosRegistro.getPassword())) {
      model.put("error", "El password debe coincidir");
      return new ModelAndView("nuevo-usuario", model);
    }
    model.put("mensaje", "el registro fue exitoso");
    return new ModelAndView("login", model);
  }
}
