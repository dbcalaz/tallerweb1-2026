package com.tallerwebi.presentacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;

public class ControladorBusquedaTest {

    private ControladorBusqueda controladorBusqueda;

    @BeforeEach
    public void init() {
        this.controladorBusqueda = new ControladorBusqueda();
    }

    // 1. Test para verificar que el inicio del flujo cargue bien
    @Test
    public void queAlPedirBuscarViajeDevuelvaLaVistaBuscarViajes() {
        ModelAndView modelAndView = controladorBusqueda.irABuscarViaje();

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("buscarViajes"));
        assertThat(modelAndView.getModel().get("datosBusqueda"), notNullValue());
    }

    // 2. Test ACTUALIZADO: Ahora verifica que mande al listado en vez de a los asientos
    @Test
    public void queAlBuscarConDatosValidosDevuelvaElListadoDeViajes() {
        DatosBusqueda datosValidos = new DatosBusqueda("San Justo", "Ramos Mejia", "20/05/2026");

        ModelAndView modelAndView = controladorBusqueda.procesarBusqueda(datosValidos);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("listadoViajes"));

        assertThat(modelAndView.getModel().get("origen").toString(), equalToIgnoringCase("San Justo"));
        assertThat(modelAndView.getModel().get("destino").toString(), equalToIgnoringCase("Ramos Mejia"));

        @SuppressWarnings("unchecked")
        List<ViajeDisponible> viajes = (List<ViajeDisponible>) modelAndView.getModel().get("viajes");
        assertThat(viajes, notNullValue());
        assertThat(viajes, hasSize(3));
    }

    @Test
    public void queAlBuscarConOrigenVacioDevuelvaErrorYSeQuedeEnLaMismaVista() {
        DatosBusqueda datosInvalidos = new DatosBusqueda("", "Ramos Mejia", "20/05/2026");
        ModelAndView modelAndView = controladorBusqueda.procesarBusqueda(datosInvalidos);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("buscarViajes"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Debe ingresar obligatoriamente Origen y Destino"));
    }

    @Test
    public void queAlPedirSeleccionarAsientoDevuelvaLaVistaCorrespondiente() {
        ModelAndView modelAndView = controladorBusqueda.irASeleccionarAsiento();

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("seleccionarAsiento"));
    }
}