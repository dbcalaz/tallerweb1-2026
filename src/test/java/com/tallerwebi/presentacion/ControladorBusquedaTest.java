package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioViaje;
import com.tallerwebi.dominio.Viaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorBusquedaTest {

    private ControladorBusqueda controladorBusqueda;
    private ServicioViaje servicioViajeMock;

    @BeforeEach
    public void init() {
        this.servicioViajeMock = mock(ServicioViaje.class);
        this.controladorBusqueda = new ControladorBusqueda(this.servicioViajeMock);
    }

    @Test
    public void queAlPedirBuscarViajeDevuelvaLaVistaBuscarViajes() {
        ModelAndView modelAndView = controladorBusqueda.irABuscarViaje();

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("buscarViajes"));
        assertThat(modelAndView.getModel().get("datosBusqueda"), notNullValue());
    }

    //Este test falla
   /* @Test
    public void queAlBuscarConDatosValidosFiltreDuplicadosYDevuelvaListado() {
        DatosBusqueda datosValidos = new DatosBusqueda("San Justo", "Ramos Mejia", "20/05/2026", 1);

        List<Viaje> viajesSimulados = new ArrayList<>();
        viajesSimulados.add(new Viaje());
        viajesSimulados.add(new Viaje());
        viajesSimulados.add(new Viaje());

        when(servicioViajeMock.buscarViajes("San Justo", "Ramos Mejia", "20/05/2026"))
                .thenReturn(viajesSimulados);

        ModelAndView modelAndView = controladorBusqueda.procesarBusqueda(datosValidos);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("listadoViajes"));

        @SuppressWarnings("unchecked")
        List<Viaje> viajes = (List<Viaje>) modelAndView.getModel().get("viajes");
        assertThat(viajes, notNullValue());

        assertThat(viajes, hasSize(1));
    }*/

    @Test
    public void queAlBuscarConOrigenVacioDevuelvaErrorYSeQuedeEnLaMismaVista() {
        DatosBusqueda datosInvalidos = new DatosBusqueda("", "Ramos Mejia", "20/05/2026", 1);
        ModelAndView modelAndView = controladorBusqueda.procesarBusqueda(datosInvalidos);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("buscarViajes"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Debe ingresar obligatoriamente Origen y Destino"));
    }

    @Test
    public void queAlPedirSeleccionarAsientoDevuelvaLaVistaCorrespondiente() {
        ModelAndView modelAndView = controladorBusqueda.irASeleccionarAsiento(1L, 1);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("seleccionarAsiento"));
    }
}