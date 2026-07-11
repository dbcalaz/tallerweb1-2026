package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorPerfilUsuario {

    ServicioPerfilUsuario servicioperfilUsuario;
    ServicioPuntuacion servicioPuntuacion;

    @Autowired
    public ControladorPerfilUsuario(
            ServicioPerfilUsuario servicioperfilUsuario,
            ServicioPuntuacion servicioPuntuacion) {

        this.servicioperfilUsuario = servicioperfilUsuario;
        this.servicioPuntuacion = servicioPuntuacion;
    }


    @RequestMapping("/perfilUsuario")
    public ModelAndView verPerfil(HttpServletRequest request) {
        Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");

        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        List <Reserva> reservas = servicioperfilUsuario.obtenerReservasPorUsuario(usuario.getId());
        Long viajesFinalizados = servicioperfilUsuario.obtenerCantidadDeviajesPorEstadoPorUsuario(usuario.getId(), EstadoReserva.FINALIZADA);
        Long viajesCancelados = servicioperfilUsuario.obtenerCantidadDeviajesPorEstadoPorUsuario(usuario.getId(), EstadoReserva.CANCELADA);
        Long viajesEnCurso = servicioperfilUsuario.obtenerCantidadDeviajesPorEstadoPorUsuario(usuario.getId(), EstadoReserva.EN_CURSO);
        Long viajesProgramados = servicioperfilUsuario.obtenerCantidadDeviajesPorEstadoPorUsuario(usuario.getId(), EstadoReserva.CONFIRMADA);

        ModelMap model = new ModelMap();

        for (Reserva reserva : reservas) {
            reserva.setYaPuntuada(
                    servicioPuntuacion.yaPuntuo(usuario.getId(), reserva.getId())
            );
        }

        model.put("usuario", usuario);
        model.put("reservas", reservas);
        model.put("viajesFinalizados", viajesFinalizados != null ? viajesFinalizados : 0);
        model.put("viajesCancelados", viajesCancelados != null ? viajesCancelados : 0 );
        model.put("viajesEnCurso", viajesEnCurso != null ? viajesEnCurso : 0 );
        model.put("viajesProgramados", viajesProgramados != null ? viajesProgramados : 0 );

        return new ModelAndView("perfil-usuario", model);
    }

    @RequestMapping(path = "/cancelar-reserva", method = RequestMethod.POST)
    public ModelAndView cancelarReserva(@RequestParam Long idReserva, HttpServletRequest request) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        Reserva reserva = servicioperfilUsuario.buscarReservaPorId(idReserva);

        servicioperfilUsuario.cancelarReserva(reserva);

        return new ModelAndView("redirect:/perfilUsuario");
    }

    @RequestMapping(path="/calificar-conductor", method=RequestMethod.POST)
    public ModelAndView calificarConductor(@RequestParam Long idReserva, @RequestParam Integer puntos, HttpServletRequest request) {

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        if(usuario == null){
            return new ModelAndView("redirect:/login");
        }

        Reserva reserva = servicioperfilUsuario.buscarReservaPorId(idReserva);

        if(reserva == null){
            return new ModelAndView("redirect:/perfilUsuario");
        }

        if(!reserva.getUsuario().getId().equals(usuario.getId())){
            return new ModelAndView("redirect:/perfilUsuario");
        }

        servicioPuntuacion.calificarConductor(reserva, usuario, puntos);

        return new ModelAndView("redirect:/perfilUsuario");
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
