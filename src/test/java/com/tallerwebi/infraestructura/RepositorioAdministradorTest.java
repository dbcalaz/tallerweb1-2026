package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.presentacion.DatosFiltro;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
@Transactional
public class RepositorioAdministradorTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RepositorioAdministrador repositorioAdministrador;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Test
    @Rollback
    public void queSePuedaBuscarUnaCombiPorId() {
        //Preparación
        Combi combi = new Combi();

        session().save(combi);

        //Ejecución
        Combi combiEncontrada = repositorioAdministrador.buscarCombiPorId(combi.getId());

        //Validación
        assertThat(combiEncontrada, notNullValue());
        assertThat(combiEncontrada.getId(), equalTo(combi.getId()));
    }

    @Test
    @Rollback
    public void queSeObtenganTodasLasCombis() {
        //Preparación
        Combi combi1 = new Combi();
        Combi combi2 = new Combi();

        session().save(combi1);
        session().save(combi2);

        //Ejecución
        List<Combi> combis = repositorioAdministrador.getCombisFiltradas(null);

        //Validación
        assertThat(combis.size(), equalTo(2));
    }

    @Test
    @Rollback
    public void queSeObtengaCorrectamenteLaCantidadDeCombis() {
        //Preparación
        Combi combi1 = new Combi();
        Combi combi2 = new Combi();

        session().save(combi1);
        session().save(combi2);

        //Ejecución
        Long cantidad = repositorioAdministrador.getCantidadDeCombis();

        //Validación
        assertThat(cantidad, equalTo(2L));
    }

    @Test
    @Rollback
    public void queSeObtenganLosConductoresPendientes() {
        //Preparación
        Conductor pendiente = new Conductor();
        pendiente.setCuentaHabilitada(false);

        Conductor habilitado = new Conductor();
        habilitado.setCuentaHabilitada(true);

        session().save(pendiente);
        session().save(habilitado);

        //Ejecución
        List<Conductor> conductores = repositorioAdministrador.getConductoresPendientes();

        //Validación
        assertThat(conductores.size(), equalTo(1));
    }

    @Test
    @Rollback
    public void queSePuedaBuscarUnConductorPorId() {
        //Preparación
        Conductor conductor = new Conductor();
        conductor.setNombre("Carlos");

        session().save(conductor);

        //Ejecución
        Conductor encontrado = repositorioAdministrador.buscarConductorPorId(conductor.getId());

        //Validación
        assertThat(encontrado, notNullValue());
        assertThat(encontrado.getId(), equalTo(conductor.getId()));
    }

    @Test
    @Rollback
    public void queSePuedaSuspenderUnConductor() {
        //Preparación
        Conductor conductor = new Conductor();

        conductor.setSuspendido(false);
        conductor.setDisponible(true);
        conductor.setEnViaje(true);

        session().save(conductor);

        //Ejecución
        repositorioAdministrador.suspenderConductor(conductor);

        Conductor actualizado = (Conductor) session().get(Conductor.class, conductor.getId());

        //Validación
        assertThat(actualizado.isSuspendido(), is(true));
        assertThat(actualizado.isDisponible(), is(false));
        assertThat(actualizado.isEnViaje(), is(false));
    }

    @Test
    @Rollback
    public void queSePuedaReactivarUnConductor() {
        //Preparación
        Conductor conductor = new Conductor();

        conductor.setSuspendido(true);
        conductor.setDisponible(false);
        conductor.setEnViaje(false);

        session().save(conductor);

        //Ejecución
        repositorioAdministrador.reactivarConductor(conductor);

        Conductor actualizado = (Conductor) session().get(Conductor.class, conductor.getId());

        //Validación
        assertThat(actualizado.isSuspendido(), is(false));
        assertThat(actualizado.isDisponible(), is(true));
        assertThat(actualizado.isEnViaje(), is(false));
    }


    //test de combis

    @Test
    @Rollback
    public void queSeObtenganLasCombisConEstadoEnViaje() {
        // Preparación
        Combi combiEnViaje1 = new Combi();
        combiEnViaje1.setEstadoDeCombi(EstadoDeCombi.EN_VIAJE);

        Combi combiEnViaje2 = new Combi();
        combiEnViaje2.setEstadoDeCombi(EstadoDeCombi.EN_VIAJE);

        Combi combiDisponible = new Combi();
        combiDisponible.setEstadoDeCombi(EstadoDeCombi.DISPONIBLE);

        session().save(combiEnViaje1);
        session().save(combiEnViaje2);
        session().save(combiDisponible);

        DatosFiltro datosFiltro = new DatosFiltro();
        datosFiltro.setEstadoDeCombi(EstadoDeCombi.EN_VIAJE);

        // Ejecución
        List<Combi> combis = repositorioAdministrador.getCombisFiltradas(datosFiltro);

        // Validación
        assertThat(combis, notNullValue());
        assertThat(combis.size(), equalTo(2));
        assertThat(combis.get(0).getEstadoDeCombi(), equalTo(EstadoDeCombi.EN_VIAJE));
        assertThat(combis.get(1).getEstadoDeCombi(), equalTo(EstadoDeCombi.EN_VIAJE));
    }

    @Test
    @Rollback
    public void queSeObtenganLasCombisEnMantenimiento() {
        // Preparación
        Combi combiMantenimiento = new Combi();
        combiMantenimiento.setEstadoDeCombi(EstadoDeCombi.EN_MANTENIMIENTO);

        Combi combiEnViaje = new Combi();
        combiEnViaje.setEstadoDeCombi(EstadoDeCombi.EN_VIAJE);

        session().save(combiMantenimiento);
        session().save(combiEnViaje);
        DatosFiltro datosFiltro = new DatosFiltro();
        datosFiltro.setEstadoDeCombi(EstadoDeCombi.EN_MANTENIMIENTO);

        // Ejecución
        List<Combi> combis = repositorioAdministrador.getCombisFiltradas(datosFiltro);

        // Validación
        assertThat(combis, notNullValue());
        assertThat(combis.size(), equalTo(1));
        assertThat(combis.get(0).getEstadoDeCombi(), equalTo(EstadoDeCombi.EN_MANTENIMIENTO));
    }

    @Test
    @Rollback
    public void queSeObtenganLasCombisDisponibles() {
        // Preparación
        Combi combiDisponible1 = new Combi();
        combiDisponible1.setEstadoDeCombi(EstadoDeCombi.DISPONIBLE);

        Combi combiDisponible2 = new Combi();
        combiDisponible2.setEstadoDeCombi(EstadoDeCombi.DISPONIBLE);

        Combi combiMantenimiento = new Combi();
        combiMantenimiento.setEstadoDeCombi(EstadoDeCombi.EN_MANTENIMIENTO);
        DatosFiltro datosFiltro = new DatosFiltro();
        datosFiltro.setEstadoDeCombi(EstadoDeCombi.DISPONIBLE);

        session().save(combiDisponible1);
        session().save(combiDisponible2);
        session().save(combiMantenimiento);

        // Ejecución
        List<Combi> combis = repositorioAdministrador.getCombisFiltradas(datosFiltro);

        // Validación
        assertThat(combis, notNullValue());
        assertThat(combis.size(), equalTo(2));
        assertThat(combis.get(0).getEstadoDeCombi(), equalTo(EstadoDeCombi.DISPONIBLE));
        assertThat(combis.get(1).getEstadoDeCombi(), equalTo(EstadoDeCombi.DISPONIBLE));
    }

    @Test
    @Rollback
    public void queSeObtenganTodasLasCombisSinImportarElEstado() {
        // Preparación
        Combi combiDisponible = new Combi();
        combiDisponible.setEstadoDeCombi(EstadoDeCombi.DISPONIBLE);

        Combi combiEnViaje = new Combi();
        combiEnViaje.setEstadoDeCombi(EstadoDeCombi.EN_VIAJE);

        Combi combiMantenimiento = new Combi();
        combiMantenimiento.setEstadoDeCombi(EstadoDeCombi.EN_MANTENIMIENTO);

        session().save(combiDisponible);
        session().save(combiEnViaje);
        session().save(combiMantenimiento);

        // Ejecución

        List<Combi> todasLasCombis = repositorioAdministrador.getCombisFiltradas(null);

        // Validación
        assertThat(todasLasCombis, notNullValue());
        assertThat(todasLasCombis.size(), equalTo(3));
    }


}