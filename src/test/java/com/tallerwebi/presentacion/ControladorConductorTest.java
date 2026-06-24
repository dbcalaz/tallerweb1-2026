package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.ConductorExistente;
import com.tallerwebi.dominio.excepcion.CuentaNoHabilitadaException;
import com.tallerwebi.dominio.excepcion.CuentaSuspendidaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorConductorTest {

    private ControladorConductor controladorConductor;
    private Conductor conductor;
    private DatosLogin datosLogin;
    private ServicioConductor servicioConductor;
    private HttpSession session;
    private HttpServletRequest request;

    @BeforeEach
    void init() {
        datosLogin = new DatosLogin("carlossanchez@mail.com","zxc");
        servicioConductor = Mockito.mock(ServicioConductor.class);
        controladorConductor = new ControladorConductor(servicioConductor);
        session = Mockito.mock(HttpSession.class);
        request = Mockito.mock(HttpServletRequest.class);
        conductor = Mockito.mock(Conductor.class);
        when(conductor.getId()).thenReturn(1L);
        when(conductor.getNombre()).thenReturn("Carlos");
        when(conductor.getApellido()).thenReturn("Sanchez");
        when(conductor.getEmail()).thenReturn("carlossanchez@mail.com");
        when(conductor.getDocumento()).thenReturn("12345678");
        when(conductor.getTelefono()).thenReturn("1123456789");
        when(conductor.getPassword()).thenReturn("zxc");
        when(conductor.getLicencia()).thenReturn(TipoDeLicencia.valueOf("D1"));
    }

    /*Registro*/
    @Test
    public void queSeRegistreCorrectamenteUnConductor() {

        //ejecución
        ModelAndView modelAndView = controladorConductor.registrarConductor(conductor);

        //validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login-conductor"));
        assertThat(modelAndView.getModel().get("mensaje").toString(), equalToIgnoringCase("Conductor registrado correctamente"));
        verify(servicioConductor).registrarConductor(conductor);
    }

    @Test
    public void cuandoSeRegistreUnConductorExistenteLanzeError() throws ConductorExistente {
        //preparación
        doThrow(ConductorExistente.class).when(servicioConductor).registrarConductor(conductor);

        //ejecucuón
        ModelAndView modelAndView = controladorConductor.registrarConductor(conductor);

        //validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-conductor"));
        assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("El conductor ya existe"));
    }

    /*Login*/
    @Test
    public void conCredencialesCorrectasElLoginEsExitoso() throws CuentaNoHabilitadaException, CuentaSuspendidaException {
        //preparación
        Conductor conductorEncontrado = Mockito.mock(Conductor.class);
        when(conductorEncontrado.getNombre()).thenReturn("Carlos");
        when(conductorEncontrado.getPassword()).thenReturn("asd");

        when(request.getSession()).thenReturn(session);
        when(servicioConductor.consultarConductor(anyString(), anyString())).thenReturn(conductorEncontrado);

        //ejecución
        ModelAndView modelAndView = controladorConductor.validarLoginConductor(datosLogin, request);

        //validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home-conductor"));
    }

    @Test
    public void conCredencialesIncorrectasElLoginNoEsExitosoYRedirigeAlogin() throws CuentaNoHabilitadaException, CuentaSuspendidaException {
        //preparación
        when(servicioConductor.consultarConductor(anyString(), anyString())).thenReturn(null);

        //ejecución
        ModelAndView modelAndView = controladorConductor.validarLoginConductor(datosLogin, request);

        //validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("login-conductor"));
    }

    /*@Test
    public void queSeObtenganCorrectamenteViajesAsociadoAUnConductorPorSuId() {
        // preparación
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("conductor")).thenReturn(conductor);

        List<Viaje> pendientes = List.of(Mockito.mock(Viaje.class), Mockito.mock(Viaje.class));
        List<Viaje> finalizados = List.of(Mockito.mock(Viaje.class));

        when(servicioConductor.obtenerViajesPendientesDelConductor(conductor.getId())).thenReturn(pendientes);
        when(servicioConductor.obtenerViajesFinalizadosDelConductor(conductor.getId())).thenReturn(finalizados);

        // ejecución
        ModelAndView modelAndView = controladorConductor.homeConductor(request);

        // validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("home-conductor"));
        assertThat(modelAndView.getModel().get("conductor"), equalTo(conductor));
        assertThat(modelAndView.getModel().get("viajesPendientes"), equalTo(pendientes));
        assertThat(modelAndView.getModel().get("viajesFinalizados"), equalTo(finalizados));

        verify(servicioConductor, times(1)).obtenerViajesPendientesDelConductor(conductor.getId());
        verify(servicioConductor, times(1)).obtenerViajesFinalizadosDelConductor(conductor.getId());
    }*/

    @Test
    public void queSePuedaReportarUnaFallaCorrectamente() {
        // Preparación
        Combi combi = new Combi();
        ReporteFalla nuevoReporteFalla = new ReporteFalla();

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("conductor")).thenReturn(conductor);

        when(servicioConductor.buscarCombiActivePorIdConductor(conductor.getId())).thenReturn(combi);

        // Ejecución
        ModelAndView modelAndView = controladorConductor.reportarFalla(nuevoReporteFalla, request);

        // Validación
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home-conductor"));
        verify(servicioConductor, times(1)).registrarFalla(any(ReporteFalla.class));
    }
}
