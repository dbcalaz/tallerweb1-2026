package com.tallerwebi.infraestructura;

import org.hibernate.SessionFactory;
import com.tallerwebi.dominio.Combi;
import com.tallerwebi.dominio.RepositorioCombi;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})

public class RespositorioCrearCombiTest {

    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    RepositorioCombi repositorioCombi;



    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnacombi(){
        //given
        Combi combi= new Combi();
        combi.setCantidadDeAsientos(11);
        combi.setTipoDeTransmision("MANUAL");

        //when
        repositorioCombi.guardar(combi);

        //then
        assertThat(combi.getId(), notNullValue());

    }

    @Test
    @Transactional
    @Rollback
    public void queNoSePuedaBuscarUnaCombiConLaPatente(){
        //given  creo las dos combis

        Combi combi= new Combi();
        combi.setCantidadDeAsientos(11);
        combi.setPatente("ABCD12345");

        sessionFactory.getCurrentSession().save(combi);

        Combi combi2 = new Combi();
        combi2.setCantidadDeAsientos(11);
        combi2.setPatente("ABCD1234");

        sessionFactory.getCurrentSession().save(combi2);

        Combi combiEncontrada= repositorioCombi.buscarPorPatente("ABCD1234");

        //then
        assertThat(combiEncontrada, notNullValue());
        assertThat(combiEncontrada.getPatente(),equalToIgnoringCase("ABCD1234"));











    }
}
