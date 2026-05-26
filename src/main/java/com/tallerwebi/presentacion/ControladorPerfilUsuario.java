package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPerfilUsuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorPerfilUsuario {

    ServicioPerfilUsuario servicioperfilUsuario;
    public ControladorPerfilUsuario(ServicioPerfilUsuario servicioperfilUsuario) { this.servicioperfilUsuario = servicioperfilUsuario; }


    @RequestMapping("/perfilUsuario")
    public ModelAndView verPerfil(DatosLogin datosLogin) {
      //  DatosLogin datosLogin = new DatosLogin();
        ModelMap model = new ModelMap();
        model.put("datosLogin", datosLogin);
        return new ModelAndView("perfil-usuario", model);

    }
}
