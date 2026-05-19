package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PasswordInvalidaException;
import com.tallerwebi.dominio.ServicioRegistro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorRegistro {

  //para generar cierta logia llamo al servicio y lo uso en el constructor tmb y le agrego
  //el autowired para que lo inyecte
  ServicioRegistro servicioRegistro;

  @Autowired
  public ControladorRegistro(ServicioRegistro servicioRegistro) {
    this.servicioRegistro = servicioRegistro;
  }

  public ModelAndView registrar(DatosLogin datosLoginDTO) {
    ModelMap model = new ModelMap();
    if (datosLoginDTO.getEmail().isEmpty()) {
      model.put("error", "el email es obligatorio");
      return new ModelAndView("registro", model);
    }
    if (datosLoginDTO.getPassword().isEmpty()) {
      model.put("error", "el password es obligatorio");
      return new ModelAndView("registro", model);
    }
    //agrego excepcion para que no se rompa

    try{
      servicioRegistro.registrar(datosLoginDTO.getEmail(), datosLoginDTO.getPassword());
    }catch(PasswordInvalidaException e){
      model.put("error", "La Contraseña debe tener al menos de 6 caracteres");
      return new ModelAndView("registro", model);
    }

    model.put("mensaje", "el registro fue exitoso");
    return new ModelAndView("login", model);
  }


}
