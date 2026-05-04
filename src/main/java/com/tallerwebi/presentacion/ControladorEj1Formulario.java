package com.tallerwebi.presentacion;

import static java.lang.System.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorEj1Formulario {

  ModelMap modelo = new ModelMap();

  @RequestMapping(path = "/ejercicio1")
  public ModelAndView pedirNombre() {
    modelo.put("datosSaludo", new DatosSaludo());
    return new ModelAndView("ejercicio1", modelo);
  }

  @RequestMapping(path = "/ej1Saludo", method = RequestMethod.POST)
  public ModelAndView saludar(DatosSaludo datosSaludo) {

    modelo.put("nombre", datosSaludo.getNombre());
    return new ModelAndView("ej1Saludo", modelo);
  }
}
