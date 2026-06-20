package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.ServicioAdministrador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ControladorAdministradorTest {

    private ControladorAdministrador controladorAdministrador;
    private ServicioAdministrador servicioAdministrador;

    @BeforeEach
    public void init() {

        servicioAdministrador = Mockito.mock(ServicioAdministrador.class);

        controladorAdministrador = new ControladorAdministrador(servicioAdministrador);
    }

   /* @Test
    public void queSeObtenganCorrectamenteLasCombis() {
        List<Combi> combis = List.of(Mockito.mock(Combi.class), Mockito.mock(Combi.class));

        when(servicioAdministrador.obtenerCombis()).thenReturn(combis);
        when(servicioAdministrador.obtenerCantidadCombis()).thenReturn(2L);

        ModelAndView modelAndView = controladorAdministrador.listarCombis("DISPONIBLE");

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("admin/combis"));
        assertThat(modelAndView.getModel().get("combis"), equalTo(combis));
        assertThat(modelAndView.getModel().get("cantidadCombis"), equalTo(2L));
    }*/

    @Test
    public void queSeObtenganCorrectamenteLasFallasDeCombis() {
        List<ReporteFalla> reportes = List.of(Mockito.mock(ReporteFalla.class));

        when(servicioAdministrador.obtenerFallasDeCombis()).thenReturn(reportes);
        DatosFiltro datosFiltro = new DatosFiltro();
        datosFiltro.setEstadoDeCombi(EstadoDeCombi.EN_VIAJE);

        ModelAndView modelAndView = controladorAdministrador.listarCombis(datosFiltro);

        assertThat(modelAndView.getModel().get("reportes"), equalTo(reportes));
    }

    @Test
    public void queSePuedaAsignarUnaNuevaCombiAUnConductor() {
        Long idReporte = 1L;
        Long idCombi = 2L;

        ModelAndView modelAndView = controladorAdministrador.asignarNuevaCombiAConductor(idReporte, idCombi);

        verify(servicioAdministrador).asignarNuevaCombiAConductor(idReporte, idCombi);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/admin/combis"));
    }

    @Test
    public void queSeObtenganCorrectamenteLosConductores() {
        List<Conductor> conductores = List.of(Mockito.mock(Conductor.class));

        when(servicioAdministrador.obtenerConductores()).thenReturn(conductores);

        ModelAndView modelAndView = controladorAdministrador.conductores();

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("admin/conductores"));
        assertThat(modelAndView.getModel().get("conductores"), equalTo(conductores));
    }

    @Test
    public void queSeObtenganCorrectamenteLosConductoresPendientesDeAprobacion() {
        List<Conductor> pendientes = List.of(Mockito.mock(Conductor.class));

        when(servicioAdministrador.obtenerConductoresPendientes()).thenReturn(pendientes);
        when(servicioAdministrador.obtenerCantidadDeConductoresPendientes()).thenReturn(1L);

        ModelAndView modelAndView = controladorAdministrador.conductores();

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("admin/conductores"));
        assertThat(modelAndView.getModel().get("conductoresPendientes"), equalTo(pendientes));
        assertThat(modelAndView.getModel().get("pendientes"), equalTo(1L));
    }

    @Test
    public void queSePuedaHabilitarUnConductor() {
        Long idConductor = 1L;
        Long idCombi = 2L;

        ModelAndView modelAndView = controladorAdministrador.asignarCombiHabilitacionConductor(idConductor, idCombi);

        verify(servicioAdministrador).habilitarConductor(idConductor, idCombi);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/admin/conductores"));
    }

    @Test
    public void queSePuedaSuspenderUnConductor() {
        Long idConductor = 1L;

        ModelAndView modelAndView = controladorAdministrador.suspenderConductor(idConductor);

        verify(servicioAdministrador).suspenderConductor(idConductor);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/admin/conductores"));
    }

    @Test
    public void queSePuedaReactivarUnConductor() {
        Long idConductor = 1L;

        ModelAndView modelAndView = controladorAdministrador.reactivarConductor(idConductor);

        verify(servicioAdministrador).reactivarConductor(idConductor);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/admin/conductores"));
    }

    //aca test de combis
    @Test
    public void queSeObtenganTodasLasCombisConEstadoEN_VIAJE() {
        // Preparación
        List<Combi> combisEnViaje = List.of(Mockito.mock(Combi.class), Mockito.mock(Combi.class));
        DatosFiltro datosFiltro = new DatosFiltro();
        datosFiltro.setEstadoDeCombi(EstadoDeCombi.EN_VIAJE);
        when(servicioAdministrador.obtenerCombisFiltradas(datosFiltro)).thenReturn(combisEnViaje);

        // Ejecución
        ModelAndView modelAndView = controladorAdministrador.listarCombis(datosFiltro);

        // Validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("admin/combis-listas"));
        assertThat(modelAndView.getModel().get("listaCombis"), equalTo(combisEnViaje));
        verify(servicioAdministrador).obtenerCombisFiltradas(datosFiltro);
    }


    @Test
    public void queSeObtenganLasCombisEnMantenimiento() {
        // Preparación
        List<Combi> combisEnMantenimiento = List.of(Mockito.mock(Combi.class));
        DatosFiltro datosFiltro = new DatosFiltro();
        datosFiltro.setEstadoDeCombi(EstadoDeCombi.EN_MANTENIMIENTO);
        when(servicioAdministrador.obtenerCombisFiltradas(datosFiltro)).thenReturn(combisEnMantenimiento);

        // Ejecución
        ModelAndView modelAndView = controladorAdministrador.listarCombis(datosFiltro);

        // Validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("admin/combis-listas"));
        assertThat(modelAndView.getModel().get("listaCombis"), equalTo(combisEnMantenimiento));
        verify(servicioAdministrador).obtenerCombisFiltradas(datosFiltro);
    }


    @Test
    public void queSeObtenganLasCombisDisponibles() {
        // Preparación
        List<Combi> combisDisponibles = List.of(Mockito.mock(Combi.class), Mockito.mock(Combi.class), Mockito.mock(Combi.class));
        DatosFiltro datosFiltro = new DatosFiltro();
        datosFiltro.setEstadoDeCombi(EstadoDeCombi.DISPONIBLE);
        when(servicioAdministrador.obtenerCombisFiltradas(datosFiltro)).thenReturn(combisDisponibles);

        // Ejecución
        ModelAndView modelAndView = controladorAdministrador.listarCombis(datosFiltro);

        // Validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("admin/combis-listas"));
        assertThat(modelAndView.getModel().get("listaCombis"), equalTo(combisDisponibles));
        verify(servicioAdministrador).obtenerCombisFiltradas(datosFiltro);
    }

    @Test
    public void queSeObtenganTodasLasCombis() {
        // Preparación
        List<Combi> todasLasCombis = List.of(Mockito.mock(Combi.class), Mockito.mock(Combi.class));
        DatosFiltro datosFiltro = new DatosFiltro();

        when(servicioAdministrador.obtenerCombisFiltradas(datosFiltro)).thenReturn(todasLasCombis);

        // Ejecución
        ModelAndView modelAndView = controladorAdministrador.listarCombis(datosFiltro);

        // Validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("admin/combis-listas"));
        assertThat(modelAndView.getModel().get("listaCombis"), equalTo(todasLasCombis));
        verify(servicioAdministrador).obtenerCombisFiltradas(datosFiltro);
    }
}
