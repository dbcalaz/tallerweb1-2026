package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorRegistro {

  private ServicioLogin servicioLogin;

  @Autowired
  public ControladorRegistro(ServicioLogin servicioLogin) {
    this.servicioLogin = servicioLogin;
  }

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

    try {
      Usuario nuevoUsuario = new Usuario();
      nuevoUsuario.setEmail(datosRegistro.getMail());
      nuevoUsuario.setPassword(datosRegistro.getPassword());
      // nuevoUsuario.setRol("USUARIO");

      servicioLogin.registrar(nuevoUsuario);

    } catch (UsuarioExistente e) {
      model.put("error", "El usuario ya existe");
      return new ModelAndView("nuevo-usuario", model);
    } catch (Exception e) {
      model.put("error", "Error interno al registrar el usuario");
      return new ModelAndView("nuevo-usuario", model);
    }

    model.put("mensaje", "El registro fue exitoso");
    model.put("datosLogin", new DatosLogin());

    return new ModelAndView("login", model);
  }

}