package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioAdministradorTest {

    private ServicioAdministrador servicioAdministrador;
    private RepositorioAdministrador repositorioAdministrador;
    private RepositorioCombi repositorioCombi;

    @BeforeEach
    void init() {
        repositorioAdministrador = Mockito.mock(RepositorioAdministrador.class);
        repositorioCombi = Mockito.mock(RepositorioCombi.class);
        servicioAdministrador = new ServicioAdministradorImpl(repositorioAdministrador,repositorioCombi);
    }

    @Test
    public void queSeObtenganCorrectamenteLasCombis() {
        // Preparación
        List<Combi> combis = List.of(new Combi(), new Combi());

        when(repositorioAdministrador.getCombis()).thenReturn(combis);

        //Ejecución
        List<Combi> resultado = servicioAdministrador.obtenerCombis();

        assertEquals(2, resultado.size());
        verify(repositorioAdministrador, times(1)).getCombis();
    }

    @Test
    public void queSeObtenganCorrectamenteLosConductores() {
        // Preparación
        List<Conductor> conductores = List.of(new Conductor(), new Conductor());

        when(repositorioAdministrador.getConductores()).thenReturn(conductores);

        //Ejecución
        List<Conductor> resultado = servicioAdministrador.obtenerConductores();

        assertEquals(2, resultado.size());
        verify(repositorioAdministrador, times(1)).getConductores();
    }

    @Test
    public void queSePuedaHabilitarUnConductor() {
        // Preparación
        Long idConductor = 1L;
        Long idCombi = 2L;

        Conductor conductor = new Conductor();
        conductor.setCuentaHabilitada(false);
        conductor.setSuspendido(false);

        Combi combi = new Combi();

        when(repositorioAdministrador.buscarConductorPorId(idConductor)).thenReturn(conductor);
        when(repositorioAdministrador.buscarCombiPorId(idCombi)).thenReturn(combi);

        //Ejecución
        servicioAdministrador.habilitarConductor(idConductor, idCombi);

        //Validación
        verify(repositorioAdministrador, times(1)).actualizarConductor(conductor);
        verify(repositorioAdministrador, times(1)).guardarAsignacion(any(AsignacionCombiConductor.class));
    }

    @Test
    public void queNoSePuedaHabilitarUnConductorInexistente() {
        // Preparación
        Long idConductor = 1L;
        Long idCombi = 2L;

        when(repositorioAdministrador.buscarConductorPorId(idConductor)).thenReturn(null);

        //Ejecución
        RuntimeException exception = assertThrows(RuntimeException.class, () -> servicioAdministrador.habilitarConductor(idConductor, idCombi));

        //Validación
        assertEquals("No existe el conductor seleccionado", exception.getMessage());
    }

    @Test
    public void queNoSePuedaHabilitarSiLaCombiNoExiste() {
        // Preparación
        Long idConductor = 1L;
        Long idCombi = 2L;

        Conductor conductor = new Conductor();

        when(repositorioAdministrador.buscarConductorPorId(idConductor)).thenReturn(conductor);
        when(repositorioAdministrador.buscarCombiPorId(idCombi)).thenReturn(null);

        //Ejecución
        RuntimeException exception = assertThrows(RuntimeException.class, () -> servicioAdministrador.habilitarConductor(idConductor, idCombi));

        //Validación
        assertEquals("No existe la combi seleccionada", exception.getMessage());
    }

   /* @Test
    public void queNoSePuedaHabilitarUnConductorSuspendido() {
        // Preparación
        Long idConductor = 1L;
        Long idCombi = 2L;

        Conductor conductor = new Conductor();
        conductor.setSuspendido(true);

        Combi combi = new Combi();

        when(repositorioAdministrador.buscarConductorPorId(idConductor)).thenReturn(conductor);
        when(repositorioAdministrador.buscarCombiPorId(idCombi)).thenReturn(combi);

        //Ejecución
        RuntimeException exception = assertThrows(RuntimeException.class, () -> servicioAdministrador.habilitarConductor(idConductor, idCombi));

        //Validación
        assertEquals("El conductor está suspendido", exception.getMessage());
    }*/

    @Test
    public void queNoSePuedaHabilitarUnConductorYaHabilitado() {
        // Preparación
        Long idConductor = 1L;
        Long idCombi = 2L;

        Conductor conductor = new Conductor();
        conductor.setCuentaHabilitada(true);

        Combi combi = new Combi();

        when(repositorioAdministrador.buscarConductorPorId(idConductor)).thenReturn(conductor);
        when(repositorioAdministrador.buscarCombiPorId(idCombi)).thenReturn(combi);

        //Ejecución
        RuntimeException exception = assertThrows(RuntimeException.class, () -> servicioAdministrador.habilitarConductor(idConductor, idCombi));

        //Validación
        assertEquals("El conductor ya fue habilitado", exception.getMessage());
    }

    @Test
    public void queSePuedaSuspenderUnConductor() {
        // Preparación
        Long idConductor = 1L;

        Conductor conductor = new Conductor();

        when(repositorioAdministrador.buscarConductorPorId(idConductor)).thenReturn(conductor);

        //Ejecución
        servicioAdministrador.suspenderConductor(idConductor);

        //Validación
        verify(repositorioAdministrador, times(1)).suspenderConductor(conductor);
    }

    @Test
    public void queNoSePuedaSuspenderUnConductorInexistente() {
        // Preparación
        Long idConductor = 1L;

        when(repositorioAdministrador.buscarConductorPorId(idConductor)).thenReturn(null);

        //Ejecución
        RuntimeException exception = assertThrows(RuntimeException.class, () -> servicioAdministrador.suspenderConductor(idConductor));

        //Validación
        assertEquals("No existe el conductor seleccionado", exception.getMessage());
    }

    @Test
    public void queSePuedaReactivarUnConductor() {
        // Preparación
        Long idConductor = 1L;

        Conductor conductor = new Conductor();

        when(repositorioAdministrador.buscarConductorPorId(idConductor)).thenReturn(conductor);

        //Ejecución
        servicioAdministrador.reactivarConductor(idConductor);

        //Validación
        verify(repositorioAdministrador, times(1)).reactivarConductor(conductor);
    }

    @Test
    public void queNoSePuedaReactivarUnConductorInexistente() {
        // Preparación
        Long idConductor = 1L;

        when(repositorioAdministrador.buscarConductorPorId(idConductor)).thenReturn(null);

        //Ejecución
        RuntimeException exception = assertThrows(RuntimeException.class, () -> servicioAdministrador.reactivarConductor(idConductor));

        //Validación
        assertEquals("No existe el conductor seleccionado", exception.getMessage());
    }


}