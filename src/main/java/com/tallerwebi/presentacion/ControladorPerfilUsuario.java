package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPerfilUsuario;
import com.tallerwebi.dominio.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorPerfilUsuario {

    ServicioPerfilUsuario servicioperfilUsuario;
    public ControladorPerfilUsuario(ServicioPerfilUsuario servicioperfilUsuario) { this.servicioperfilUsuario = servicioperfilUsuario; }


    @RequestMapping("/perfilUsuario")
    public ModelAndView verPerfil(HttpServletRequest request) {
        Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }
        ModelMap model = new ModelMap();
        model.put("usuario", usuario);
        return new ModelAndView("perfil-usuario", model);

    }


}
