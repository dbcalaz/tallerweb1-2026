package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorFormularios {

  @RequestMapping("/formularioProducto")
  public ModelAndView formId() {
    return new ModelAndView("formularioId");
  }

  @RequestMapping(path = "/reenviarAPath", method = RequestMethod.GET)
  public ModelAndView retornoAVariablesPath(@RequestParam long id) {
    return new ModelAndView("redirect:/producto/" + id);
  }

  @RequestMapping(path = "/producto/{id}", method = RequestMethod.GET)
  public ModelAndView retornoId(@PathVariable long id) {
    ModelMap model = new ModelMap();
    model.put("id", id);
    return new ModelAndView("producto", model);
  }

  @RequestMapping("/formulario")
  public ModelAndView irAFormulario() {
    return new ModelAndView("formulario");
  }

  @RequestMapping(path = "/saludo", method = RequestMethod.GET)
  public ModelAndView saludo(@RequestParam String nombre) {
    ModelMap model = new ModelMap();
    //el key del model tiene que coincidir con el name del input
    model.put("nombre", nombre);

    return new ModelAndView("saludo", model);
  }
}
