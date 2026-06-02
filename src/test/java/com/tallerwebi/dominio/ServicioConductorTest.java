package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ConductorExistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioConductorTest {

    private ServicioConductor servicioConductor;
    private RepositorioConductor repositorioConductor;
    private Conductor conductor;

    @BeforeEach
    void init() {
        repositorioConductor = Mockito.mock(RepositorioConductor.class);
        servicioConductor = new ServicioConductorImpl(repositorioConductor);
        conductor = Mockito.mock(Conductor.class);
        when(conductor.getId()).thenReturn(1L);
    }

    @Test
    void queSeRegistreCorrectamenteUnConductor() throws ConductorExistente {
        //preparación
        Conductor conductor = new Conductor();
        conductor.setNombre("Carlos");
        conductor.setApellido("Sanchez");
        conductor.setDocumento("12345678");
        conductor.setPassword("asd");
        conductor.setLicencia(TipoDeLicencia.D1);
        conductor.setTelefono("1123456789");

        when(repositorioConductor.buscarConductor(conductor.getEmail(), conductor.getPassword())).thenReturn(null);

        //ejecución
        servicioConductor.registrarConductor(conductor);

        //validación
        verify(repositorioConductor, times(1)).guardarConductor(conductor);
    }

    @Test
    void queNoSeRegistreCorrectamenteUnConductorSiYaExiste() {
        //preparación
        Conductor conductor = new Conductor();
        conductor.setEmail("carlossanchez@mail.com");
        conductor.setPassword("asd");

        when(repositorioConductor.buscarConductor(conductor.getEmail(), conductor.getPassword())).thenReturn(new  Conductor());

        //validación
        assertThrows(ConductorExistente.class, ()-> servicioConductor.registrarConductor(conductor));
        verify(repositorioConductor, times(0)).guardarConductor(conductor);
    }

    @Test
    public void queSePuedaVisualizarLosViajesDelConductor() {
        Long idConductor = conductor.getId();

        List<Viaje> viajesEsperados = new ArrayList<>();
        viajesEsperados.add(new Viaje());
        viajesEsperados.add(new Viaje());

        when(repositorioConductor.obtenerViajesPorConductor(idConductor)).thenReturn(viajesEsperados);

        List<Viaje> viajesObtenidos = servicioConductor.obtenerViajesDelConductor(idConductor);

        assertEquals(2, viajesObtenidos.size());
        verify(repositorioConductor, times(1)).obtenerViajesPorConductor(idConductor);
    }
}
