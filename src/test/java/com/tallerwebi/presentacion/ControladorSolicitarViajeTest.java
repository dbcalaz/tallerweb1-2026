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
        // Creamos simuladores (Mocks) para aislar la prueba del controlador
        this.servicioViajeMock = mock(ServicioViaje.class);
        this.controladorBusqueda = new ControladorBusqueda(this.servicioViajeMock);

        this.requestMock = mock(HttpServletRequest.class);
        this.sessionMock = mock(HttpSession.class);
        this.usuarioMock = mock(Usuario.class);

        // Encadenamos el comportamiento de la sesión HTTP simulada
        when(this.requestMock.getSession()).thenReturn(this.sessionMock);
        when(this.sessionMock.getAttribute("usuario")).thenReturn(this.usuarioMock);
    }

    // TEST 1: Verifica el camino feliz del buscador
    @Test
    public void siSeIngresaOrigenYDestinoElPedidoEsExitoso() {
        DatosBusqueda datosBusqueda = new DatosBusqueda(origen, destino, "2026-06-15", 1);
        when(servicioViajeMock.buscarViajes(any(DatosBusqueda.class))).thenReturn(new ArrayList<>());

        ModelAndView modelAndView = controladorBusqueda.procesarBusqueda(datosBusqueda);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("listadoViajes"));
    }

    // TEST 2: Comprueba las validaciones obligatorias de inputs vacíos
    @Test
    public void siNoSeIngresaOrigenYDestinoLaSolicitudNoEsExitosa() {
        DatosBusqueda datosBusqueda = new DatosBusqueda("", "", "", null);

        ModelAndView modelAndView = controladorBusqueda.procesarBusqueda(datosBusqueda);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("buscarViajes"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Debe ingresar obligatoriamente Origen, Destino, Fecha y cantidad de Pasajeros"));
    }

    // TEST 3: Verifica el registro correcto de las alertas en espera
    @Test
    public void queSePuedaConfirmarUnaSolicitudDeViajeEnEspera() {
        ModelAndView modelAndView = controladorBusqueda.solicitarViajeEnEspera(origen, destino);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("home"));
        assertThat(modelAndView.getModel().get("mensaje").toString(), containsString("¡Solicitud registrada!"));
    }

    // TEST 4 (EL MÁS IMPORTANTE): Valida la compra múltiple de asientos
    @Test
    public void queSePuedaConfirmarUnAsientoConExitoYRedirijaAViajeEnCurso() {
        Long idViaje = 1L;
        String asientosSeleccionados = "1,2";
        Integer pasajeros = 2; // Simulamos que el usuario está comprando 2 pasajes

        when(servicioViajeMock.buscarPorId(idViaje)).thenReturn(new Viaje());

        ModelAndView modelAndView = controladorBusqueda.confirmarAsiento(idViaje, pasajeros, asientosSeleccionados, requestMock);

        // Verificamos que al finalizar la compra nos mande a la pantalla correcta
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/viajeEnCurso"));

        // COMPROBACIÓN CLAVE: Verificamos que el método reservarAsiento se haya llamado EXACTAMENTE 2 veces (times(2)) porque eran 2 pasajeros
        verify(servicioViajeMock, times(2)).reservarAsiento(idViaje, usuarioMock);

        // Verificamos que las reservas se guarden en la sesión del usuario
        verify(sessionMock, times(1)).setAttribute(eq("misReservas"), anyList());
    }
}