package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

public class ControladorDetalleViajeTest {

    private ControladorDetalle controladorDetalle;
    private RepositorioViaje servicioViaje;
    private Viaje viaje;
    ServicioPerfilUsuario servicioPerfilUsuario;
    private ServicioPuntuacion servicioPuntuacion;
    private HttpSession session;
    private HttpServletRequest request;

    @BeforeEach
    public void init() {
        servicioPerfilUsuario = Mockito.mock(ServicioPerfilUsuario.class);
        servicioPuntuacion = Mockito.mock(ServicioPuntuacion.class);
        request = Mockito.mock(HttpServletRequest.class);
        session = Mockito.mock(HttpSession.class);

        controladorDetalle =
                new ControladorDetalle(servicioPerfilUsuario, servicioPuntuacion, (ServicioViaje) servicioViaje);
    }

    @Test
    public void deberiaMostrarDetalleViaje() {

        Long idReserva = 1L;

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Conductor conductor = new Conductor();

        Viaje viaje = new Viaje();
        viaje.setConductor(conductor);

        Reserva reserva = new Reserva();
        reserva.setId(idReserva);
        reserva.setUsuario(usuario);
        reserva.setViaje(viaje);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("usuario")).thenReturn(usuario);
        when(servicioPerfilUsuario.buscarReservaPorId(idReserva))
                .thenReturn(reserva);

        ModelAndView mav = controladorDetalle.verDetalle(
                idReserva, request);

        assertThat(mav.getViewName(), equalTo("detalle-viaje"));
        assertThat(mav.getModelMap().get("reserva"), equalTo(reserva));
        assertThat(mav.getModelMap().get("viaje"), equalTo(viaje));
        assertThat(mav.getModelMap().get("conductor"), equalTo(conductor));

    }

}
