package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorSolicitarViajeTest {
    private ServicioViaje servicioViajeMock;
    private ControladorBusqueda controladorBusqueda;

    @BeforeEach
    public void init() {
        this.servicioViajeMock = mock(ServicioViaje.class);
        this.controladorBusqueda = new ControladorBusqueda(this.servicioViajeMock);
    }

    @Test
    public void siSeIngresaOrigenYDestinoElPedidoEsExitoso() {
        DatosBusqueda datosBusqueda = new DatosBusqueda(1L, 2L, "2026-06-15", 1);
        when(servicioViajeMock.buscarViajes(any(DatosBusqueda.class))).thenReturn(new ArrayList<>());
        ModelAndView mav = controladorBusqueda.procesarBusqueda(datosBusqueda);
        assertThat(mav.getViewName(), equalToIgnoringCase("listadoViajes"));
    }
}