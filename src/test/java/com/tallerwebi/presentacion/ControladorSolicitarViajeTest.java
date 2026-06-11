package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Reserva;
import com.tallerwebi.dominio.ServicioViaje;
import com.tallerwebi.dominio.Viaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;

public class ControladorSolicitarViajeTest {

    private final String origen = "oliden 1543";
    private final String destino = "estacion merlo";

    private ServicioViaje servicioViajeMock;
    private ControladorBusqueda controladorBusqueda;

    private HttpServletRequest requestMock;
    private HttpSession sessionMock;

    @BeforeEach
    public void init() {
        this.servicioViajeMock = mock(ServicioViaje.class);
        this.controladorBusqueda = new ControladorBusqueda(this.servicioViajeMock);

        this.requestMock = mock(HttpServletRequest.class);
        this.sessionMock = mock(HttpSession.class);
        when(this.requestMock.getSession()).thenReturn(this.sessionMock);
    }

    @Test
    public void siSeIngresaOrigenYDestinoElPedidoEsExitoso() {
        DatosBusqueda datosBusqueda = new DatosBusqueda(origen, destino, "2026-06-15", 1);
        when(servicioViajeMock.buscarViajes(origen, destino, "2026-06-15")).thenReturn(new ArrayList<>());

        ModelAndView modelAndView = controladorBusqueda.procesarBusqueda(datosBusqueda);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("listadoViajes"));
    }

    @Test
    public void siNoSeIngresaOrigenYDestinoLaSolicitudNoEsExitosa() {
        DatosBusqueda datosBusqueda = new DatosBusqueda("", "", "", 1);

        ModelAndView modelAndView = controladorBusqueda.procesarBusqueda(datosBusqueda);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("buscarViajes"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Debe ingresar obligatoriamente Origen y Destino"));
    }

    @Test
    public void queSePuedaConfirmarUnaSolicitudDeViajeEnEspera() {
        ModelAndView modelAndView = controladorBusqueda.solicitarViajeEnEspera(origen, destino);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("home"));
        assertThat(modelAndView.getModel().get("mensaje").toString(), containsString("¡Solicitud registrada!"));
    }

    @Test
    public void queSePuedaConfirmarUnAsientoYElViajeQuedeEnCurso() {
        Long idViaje = 1L;
        String asientosSeleccionados = "1,2";
        Integer pasajeros = 1;

        Viaje viajeMock = new Viaje();
        viajeMock.setPrecio(1500.0);

        when(servicioViajeMock.buscarPorId(idViaje)).thenReturn(viajeMock);

        List<Reserva> reservasMock = new ArrayList<>();
        when(sessionMock.getAttribute("misReservas")).thenReturn(reservasMock);

        ModelAndView modelAndView = controladorBusqueda.confirmarAsiento(idViaje, pasajeros, asientosSeleccionados, requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("viajeEnCurso"));
        assertThat(modelAndView.getModel().get("mensaje").toString(), equalToIgnoringCase("¡Asiento(s) confirmado(s) con éxito!"));

        verify(sessionMock, times(1)).setAttribute(eq("misReservas"), anyList());
    }
}