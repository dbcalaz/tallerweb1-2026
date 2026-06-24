package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Conductor;
import com.tallerwebi.dominio.EstadoDeViaje;
import com.tallerwebi.dominio.RepositorioConductor;
import com.tallerwebi.dominio.Viaje;
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
public class RepositorioConductorTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RepositorioConductor repositorioConductor;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Test
    @Rollback
    public void queSeGuardeCorrectamenteUnConductor() {

        Conductor conductor = new Conductor();
        conductor.setEmail("test@email.com");
        conductor.setPassword("1234");

        repositorioConductor.guardarConductor(conductor);

        assertThat(conductor.getId(), notNullValue());
    }

    @Test
    @Rollback
    public void queCuandoSeBusqueUnConductorDevuelveConductor() {

        Conductor conductor = new Conductor();
        conductor.setEmail("carlossanchez@email.com");
        conductor.setPassword("asd");

        session().save(conductor);

        Conductor conductorEncontrado = repositorioConductor.buscarConductor(conductor.getEmail(), conductor.getPassword());

        assertThat(conductorEncontrado, notNullValue());
        assertThat(conductorEncontrado.getEmail(), equalTo(conductor.getEmail()));
    }

    /*@Test
    @Rollback
    public void queSeObtenganCorrectamenteLosViajesPendientesDeUnConductor() {

        Conductor conductor = new Conductor();
        session().save(conductor);

        Viaje viaje1 = new Viaje();
        viaje1.setConductor(conductor);
        viaje1.setEstadoDeViaje(EstadoDeViaje.PENDIENTE);
        session().save(viaje1);

        Viaje viaje2 = new Viaje();
        viaje2.setConductor(conductor);
        viaje2.setEstadoDeViaje(EstadoDeViaje.PENDIENTE);
        session().save(viaje2);

        Viaje viaje3 = new Viaje();
        viaje3.setConductor(conductor);
        viaje3.setEstadoDeViaje(EstadoDeViaje.FINALIZADO);
        session().save(viaje3);

        List<Viaje> viajes = repositorioConductor.obtenerViajesPendientesPorConductor(conductor.getId());

        assertThat(viajes.size(), equalTo(2));
    }

    @Test
    @Rollback
    public void queSeObtenganCorrectamenteLosViajesFinalizadosDeUnConductor() {

        Conductor conductor = new Conductor();
        session().save(conductor);

        Viaje viaje1 = new Viaje();
        viaje1.setConductor(conductor);
        viaje1.setEstadoDeViaje(EstadoDeViaje.PENDIENTE);
        session().save(viaje1);

        Viaje viaje2 = new Viaje();
        viaje2.setConductor(conductor);
        viaje2.setEstadoDeViaje(EstadoDeViaje.PENDIENTE);
        session().save(viaje2);

        Viaje viaje3 = new Viaje();
        viaje3.setConductor(conductor);
        viaje3.setEstadoDeViaje(EstadoDeViaje.FINALIZADO);
        session().save(viaje3);

        List<Viaje> viajes = repositorioConductor.obtenerViajesFinalizadosPorConductor(conductor.getId());

        assertThat(viajes.size(), equalTo(1));
    }*/
}