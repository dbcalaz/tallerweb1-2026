package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
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
        List<Combi> combis = repositorioAdministrador.getCombis();

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


}