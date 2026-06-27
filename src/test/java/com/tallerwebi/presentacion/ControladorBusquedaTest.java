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

public class ControladorBusquedaTest {

    private ControladorBusqueda controladorBusqueda;
    private ServicioViaje servicioViajeMock;

    @BeforeEach
    public void init() {
        this.servicioViajeMock = mock(ServicioViaje.class);
        this.controladorBusqueda = new ControladorBusqueda(this.servicioViajeMock);
    }

    @Test
    public void queAlBuscarConDatosValidosDevuelvaListado() {
        DatosBusqueda datos = new DatosBusqueda(1L, 2L, "2026-06-30", 1);
        when(servicioViajeMock.buscarViajes(any(DatosBusqueda.class))).thenReturn(new ArrayList<>());

        ModelAndView mav = controladorBusqueda.procesarBusqueda(datos);
        assertThat(mav.getViewName(), equalToIgnoringCase("listadoViajes"));
    }

    @Test
    public void queAlBuscarConCamposVaciosDevuelvaError() {
        DatosBusqueda datos = new DatosBusqueda(null, null, "", 1);
        ModelAndView mav = controladorBusqueda.procesarBusqueda(datos);
        assertThat(mav.getViewName(), equalToIgnoringCase("buscarViajes"));
    }
}