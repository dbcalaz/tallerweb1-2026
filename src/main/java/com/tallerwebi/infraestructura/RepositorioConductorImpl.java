package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Conductor;
import com.tallerwebi.dominio.EstadoDeViaje;
import com.tallerwebi.dominio.RepositorioConductor;
import com.tallerwebi.dominio.Viaje;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioConductorImpl implements RepositorioConductor {

    private SessionFactory sessionFactory;

    public RepositorioConductorImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Conductor buscarConductor(String email, String password) {

        return (Conductor) sessionFactory.getCurrentSession().createCriteria(Conductor.class).add(Restrictions.eq("email", email)).add(Restrictions.eq("password", password)).uniqueResult();
    }

    @Override
    public void guardarConductor(Conductor conductor) {
        sessionFactory.getCurrentSession().save(conductor);
    }

    //Con todos los estados de viajes (pendientes, en curso, finalizados.)
    @Override
    public List<Viaje> obtenerViajesPorConductor(Long idConductor){
        return (List<Viaje>) sessionFactory.getCurrentSession()
                .createCriteria(Viaje.class)
                .createAlias("conductor", "c")
                .add(Restrictions.eq("c.id",idConductor))
                .list();
    }

    //Sólo viajes con estado PENDIENTE
    @Override
    public List<Viaje> obtenerViajesPendientesPorConductor(Long idConductor){
        return (List<Viaje>) sessionFactory.getCurrentSession()
                .createCriteria(Viaje.class)
                .createAlias("conductor", "c")
                .add(Restrictions.eq("c.id",idConductor))
                .add(Restrictions.eq("estadoDeViaje", EstadoDeViaje.PENDIENTE))
                .list();
    }

    //Sólo viajes con estado FINALIZADO
    @Override
    public List<Viaje> obtenerViajesFinalizadosPorConductor(Long idConductor){
        return (List<Viaje>) sessionFactory.getCurrentSession()
                .createCriteria(Viaje.class)
                .createAlias("conductor", "c")
                .add(Restrictions.eq("c.id",idConductor))
                .add(Restrictions.eq("estadoDeViaje", EstadoDeViaje.FINALIZADO))
                .list();
    }
}

