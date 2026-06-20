package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DatosFiltro;
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
        servicioAdministrador = new ServicioAdministradorImpl(repositorioAdministrador, repositorioCombi);
    }

    @Test
    public void queSeObtenganCorrectamenteLasCombis() {
        // Preparación
        List<Combi> combis = List.of(new Combi(), new Combi());

        when(repositorioAdministrador.getCombisFiltradas(null)).thenReturn(combis);

        //Ejecución
        List<Combi> resultado = servicioAdministrador.obtenerCombisFiltradas(null);

        assertEquals(2, resultado.size());
        verify(repositorioAdministrador, times(1)).getCombisFiltradas(null);
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


    //aca test de combis
    @Test
    public void queSeObtenganTodasLasCombisConEstadoEN_VIAJE() {
        // Preparación
        List<Combi> combisEnViaje = List.of(new Combi(), new Combi());
        DatosFiltro datosFiltro = new DatosFiltro();
        datosFiltro.setEstadoDeCombi(EstadoDeCombi.EN_VIAJE);

        // Ahora mockeamos obtenerCombisFiltradas
        when(repositorioAdministrador.getCombisFiltradas(datosFiltro)).thenReturn(combisEnViaje);

        // Ejecución
        List<Combi> resultado = servicioAdministrador.obtenerCombisFiltradas(datosFiltro);

        // Validación
        assertEquals(2, resultado.size());
        assertEquals(combisEnViaje, resultado);
        verify(repositorioAdministrador, times(1)).getCombisFiltradas(datosFiltro);
    }

    @Test
    public void queSeObtenganLasCombisEnMantenimiento() {
        // Preparación
        List<Combi> combisEnMantenimiento = List.of(new Combi());
        DatosFiltro datosFiltro = new DatosFiltro();
        datosFiltro.setEstadoDeCombi(EstadoDeCombi.EN_MANTENIMIENTO);

        when(repositorioAdministrador.getCombisFiltradas(datosFiltro)).thenReturn(combisEnMantenimiento);

        // Ejecución
        List<Combi> resultado = servicioAdministrador.obtenerCombisFiltradas(datosFiltro);

        // Validación
        assertEquals(1, resultado.size());
        assertEquals(combisEnMantenimiento, resultado);
        verify(repositorioAdministrador, times(1)).getCombisFiltradas(datosFiltro);
    }

    @Test
    public void queSeObtenganLasCombisDisponibles() {
        // Preparación
        List<Combi> combisDisponibles = List.of(new Combi(), new Combi(), new Combi());
        DatosFiltro datosFiltro = new DatosFiltro();
        datosFiltro.setEstadoDeCombi(EstadoDeCombi.DISPONIBLE);

        when(repositorioAdministrador.getCombisFiltradas(datosFiltro)).thenReturn(combisDisponibles);

        // Ejecución
        List<Combi> resultado = servicioAdministrador.obtenerCombisFiltradas(datosFiltro);

        // Validación
        assertEquals(3, resultado.size());
        assertEquals(combisDisponibles, resultado);
        verify(repositorioAdministrador, times(1)).getCombisFiltradas(datosFiltro);
    }

    @Test
    public void queSeObtenganTodasLasCombis() {
        // Preparación
        List<Combi> todasLasCombis = List.of(new Combi(), new Combi(), new Combi(), new Combi());

        when(repositorioAdministrador.getCombisFiltradas(null)).thenReturn(todasLasCombis);

        // Ejecución
        List<Combi> resultado = servicioAdministrador.obtenerCombisFiltradas(null);

        // Validación
        assertEquals(4, resultado.size());
        assertEquals(todasLasCombis, resultado);
        verify(repositorioAdministrador, times(1)).getCombisFiltradas( null);
    }


}