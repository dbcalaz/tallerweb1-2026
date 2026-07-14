package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorDetalle {

    private ServicioViaje servicioViaje;
    private ServicioPerfilUsuario servicioPerfilUsuario;
    private ServicioPuntuacion servicioPuntuacion;

    @Autowired
    public ControladorDetalle(ServicioPerfilUsuario servicioPerfilUsuario, ServicioPuntuacion servicioPuntuacion,  ServicioViaje servicioViaje) {
        this.servicioPerfilUsuario = servicioPerfilUsuario;
        this.servicioPuntuacion = servicioPuntuacion;
        this.servicioViaje = servicioViaje;

    }

    public ControladorDetalle(ServicioPerfilUsuario servicioPerfilUsuario) {
        this.servicioPerfilUsuario = servicioPerfilUsuario;
    }

    @RequestMapping("/detalle-viaje/{id}")
    public ModelAndView verDetalle(@PathVariable Long id, HttpServletRequest request) {

        ModelMap model = new ModelMap();
        Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuario");

        if (usuarioLogueado == null) {
            return new ModelAndView("redirect:/login");
        }

        Reserva reserva = servicioPerfilUsuario.buscarReservaPorId(id);

        if (reserva == null) {
            return new ModelAndView("redirect:/perfil-usuario");
        }

        if (!reserva.getUsuario().getId().equals(usuarioLogueado.getId())) {
            return new ModelAndView("redirect:/perfil-usuario");
        }

        boolean yaPuntuo = servicioPuntuacion.yaPuntuo(usuarioLogueado.getId(), reserva.getId());

        // Calculamos el precio por asiento basándonos en el total del tramo y la cantidad de pasajeros
        double precioPorAsiento = 0.0;
        if (reserva.getPasajeros() != null && !reserva.getPasajeros().isEmpty()) {
            precioPorAsiento = reserva.getPrecioTotal() / reserva.getPasajeros().size();
        } else {
            precioPorAsiento = reserva.getPrecioTotal();
        }

        model.put("yaPuntuo", yaPuntuo);
        model.put("reserva", reserva);
        model.put("viaje", reserva.getViaje());
        model.put("conductor", reserva.getViaje().getConductor());
        model.put("precioPorAsiento", precioPorAsiento); // Enviamos el dato a la vista

        return new ModelAndView("detalle-viaje", model);
    }
}