package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Conductor;
import com.tallerwebi.dominio.RepositorioConductor;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RepositorioConductorTest {

    private RepositorioConductor repositorioConductor;
    private SessionFactory sessionFactory;
    private Session session;
    private Criteria criteria;

    @BeforeEach
    public void init() {
        sessionFactory = Mockito.mock(SessionFactory.class);
        session = Mockito.mock(Session.class);
        criteria = Mockito.mock(Criteria.class);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createCriteria(Conductor.class)).thenReturn(criteria);
        when(criteria.add(any(Criterion.class))).thenReturn(criteria);

        repositorioConductor = new RepositorioConductorImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void queSeGuardeCorrectamenteUnConductor() {
        //preparación
        Conductor conductor = new Conductor();
        //ejecución
        repositorioConductor.guardarConductor(conductor);
        //validación
        verify(session).save(conductor);
    }

    @Test
    @Transactional
    @Rollback
    public void queCuandoSeBusqueUnConductorDevuelveConductor() {
        //preparación
        Conductor conductorEsperado = new Conductor();
        when(criteria.uniqueResult()).thenReturn(conductorEsperado);
        //ejecución
        Conductor conductorEncontrado = repositorioConductor.buscarConductor("carlossanchez@email.com","asd");
        //validación
        assertThat(conductorEncontrado, equalTo(conductorEsperado));
    }

}
