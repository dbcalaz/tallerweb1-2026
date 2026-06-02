package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Reserva;
import com.tallerwebi.dominio.ServicioPerfilUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.Viaje;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorPerfilUsuario {

    ServicioPerfilUsuario servicioperfilUsuario;
    public ControladorPerfilUsuario(ServicioPerfilUsuario servicioperfilUsuario) { this.servicioperfilUsuario = servicioperfilUsuario; }


    @RequestMapping("/perfilUsuario")
    public ModelAndView verPerfil(HttpServletRequest request) {
        Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");

        System.out.println(
                request.getSession().getAttribute("usuario")
        );


        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        System.out.println("Usuario logueado ID: " + usuario.getId());
        System.out.println("Usuario logueado Email: " + usuario.getEmail());

        List<Reserva> reservas = servicioperfilUsuario.obtenerReservasPorUsuario(usuario.getId());

        System.out.println("Cantidad reservas: " + reservas.size());

        for (Reserva r : reservas) {
            System.out.println(
                    "Reserva " + r.getId() +
                            " Viaje: " + r.getViaje().getOrigen() +
                            " -> " + r.getViaje().getDestino()
            );
        }

        ModelMap model = new ModelMap();
        model.put("usuario", usuario);
        model.put("reservas", reservas);
        return new ModelAndView("perfil-usuario", model);

    }


}
