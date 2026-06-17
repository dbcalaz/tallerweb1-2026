package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioViaje;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.Viaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

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
    private Usuario usuarioMock;

    @BeforeEach
    public void init() {
        this.servicioViajeMock = mock(ServicioViaje.class);
        this.controladorBusqueda = new ControladorBusqueda(this.servicioViajeMock);

        this.requestMock = mock(HttpServletRequest.class);
        this.sessionMock = mock(HttpSession.class);
        this.usuarioMock = mock(Usuario.class); // Mockeamos un usuario logueado

        when(this.requestMock.getSession()).thenReturn(this.sessionMock);
        when(this.sessionMock.getAttribute("usuario")).thenReturn(this.usuarioMock);
    }

    @Test
    public void siSeIngresaOrigenYDestinoElPedidoEsExitoso() {
        DatosBusqueda datosBusqueda = new DatosBusqueda(origen, destino, "2026-06-15", 1);
        when(servicioViajeMock.buscarViajes(any(DatosBusqueda.class))).thenReturn(new ArrayList<>());

        ModelAndView modelAndView = controladorBusqueda.procesarBusqueda(datosBusqueda);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("listadoViajes"));
    }

    @Test
    public void siNoSeIngresaOrigenYDestinoLaSolicitudNoEsExitosa() {
        DatosBusqueda datosBusqueda = new DatosBusqueda("", "", "", null);

        ModelAndView modelAndView = controladorBusqueda.procesarBusqueda(datosBusqueda);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("buscarViajes"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Debe ingresar obligatoriamente Origen, Destino, Fecha y cantidad de Pasajeros"));
    }

    @Test
    public void queSePuedaConfirmarUnaSolicitudDeViajeEnEspera() {
        ModelAndView modelAndView = controladorBusqueda.solicitarViajeEnEspera(origen, destino);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("home"));
        assertThat(modelAndView.getModel().get("mensaje").toString(), containsString("¡Solicitud registrada!"));
    }

    @Test
    public void queSePuedaConfirmarUnAsientoConExitoYRedirijaAViajeEnCurso() {
        Long idViaje = 1L;
        String asientosSeleccionados = "1,2";
        Integer pasajeros = 2;

        // Simulamos que el servicio encuentra el viaje cuando el controlador pide sus datos
        when(servicioViajeMock.buscarPorId(idViaje)).thenReturn(new Viaje());

        ModelAndView modelAndView = controladorBusqueda.confirmarAsiento(idViaje, pasajeros, asientosSeleccionados, requestMock);

        // Verificamos únicamente la redirección, ya que el mensaje de éxito fue quitado
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/viajeEnCurso"));

        // Verificamos que el asiento se haya restado en la BD la cantidad de veces solicitada
        verify(servicioViajeMock, times(2)).reservarAsiento(idViaje, usuarioMock);

        // Verificamos que la reserva se haya guardado correctamente en la sesión
        verify(sessionMock, times(1)).setAttribute(eq("misReservas"), anyList());
    }
}