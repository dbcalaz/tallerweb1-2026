package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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

        List<Reserva> reservas = servicioperfilUsuario.obtenerReservasPorUsuario(usuario.getId());
        Conductor favorito = servicioperfilUsuario.obtenerConductorFavorito(usuario.getId());

        ModelMap model = new ModelMap();
        model.put("usuario", usuario);
        model.put("reservas", reservas);
        model.put("favorito", favorito != null ? favorito.getNombre() : "Sin datos");
        return new ModelAndView("perfil-usuario", model);

    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new ModelAndView("redirect:/login");
    }


}
