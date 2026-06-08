package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Conductor;
import com.tallerwebi.dominio.Reserva;
import com.tallerwebi.dominio.Usuario;
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
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
@Transactional
public class RepositorioReservaTest {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Autowired
    RepositorioReservaImpl repositorioReserva;

    @Test
    @Rollback
    public void queSePuedaObtenerLasUltimasTresReservasPorUsuario() {

        Usuario usuario = new Usuario();
        session().save(usuario);

        Viaje viaje1 = new Viaje();
        session().save(viaje1);

        Viaje viaje2 = new Viaje();
        session().save(viaje2);

        Viaje viaje3 = new Viaje();
        session().save(viaje3);

        Viaje viaje4 = new Viaje();
        session().save(viaje4);

        Reserva reserva1 = new Reserva();
        reserva1.setUsuario(usuario);
        reserva1.setViaje(viaje1);
        session().save(reserva1);

        Reserva reserva2 = new Reserva();
        reserva2.setUsuario(usuario);
        reserva2.setViaje(viaje2);
        session().save(reserva2);

        Reserva reserva3 = new Reserva();
        reserva3.setUsuario(usuario);
        reserva3.setViaje(viaje3);
        session().save(reserva3);

        Reserva reserva4 = new Reserva();
        reserva4.setUsuario(usuario);
        reserva4.setViaje(viaje4);
        session().save(reserva4);

        session().flush();


        List<Reserva> reservas = repositorioReserva.buscarUltimasReservasPorUsuario(usuario.getId());

        assertThat(reservas.size(),equalTo(3));

        assertThat(reservas.get(0).getId(), equalTo(reserva4.getId()));
        assertThat(reservas.get(1).getId(), equalTo(reserva3.getId()));
        assertThat(reservas.get(2).getId(), equalTo(reserva2.getId()));


    }

    @Test
    @Rollback
    public void queSePuedaobtenerElConductorFavorito(){
        Usuario usuario = new Usuario();
        session().save(usuario);

        Conductor conductor1 = new Conductor();
        conductor1.setNombre("La Conductor 1");
        session().save(conductor1);

        Conductor conductor2 = new Conductor();
        conductor2.setNombre("La Conductor 2");
        session().save(conductor2);

        Viaje viaje1 = new Viaje();
        viaje1.setConductor(conductor1);
        session().save(viaje1);
        Viaje viaje2 = new Viaje();
        viaje2.setConductor(conductor2);
        session().save(viaje2);
        Viaje viaje3 = new Viaje();
        viaje3.setConductor(conductor2);
        session().save(viaje3);
        Viaje viaje4 = new Viaje();
        viaje4.setConductor(conductor2);
        session().save(viaje4);

        Reserva reserva1 = new Reserva();
        reserva1.setUsuario(usuario);
        reserva1.setViaje(viaje1);
        session().save(reserva1);
        Reserva reserva2 = new Reserva();
        reserva2.setUsuario(usuario);
        reserva2.setViaje(viaje2);
        session().save(reserva2);
        Reserva reserva3 = new Reserva();
        reserva3.setUsuario(usuario);
        reserva3.setViaje(viaje3);
        session().save(reserva3);
        Reserva reserva4 = new Reserva();
        reserva4.setUsuario(usuario);
        reserva4.setViaje(viaje4);
        session().save(reserva4);

        Conductor favorito = repositorioReserva.obtenerConductorFavorito(usuario.getId());

        assertEquals(conductor2.getId(),favorito.getId());
    }




}
