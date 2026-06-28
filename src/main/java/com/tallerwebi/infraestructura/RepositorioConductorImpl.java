package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class RepositorioConductorImpl implements RepositorioConductor {

    private final SessionFactory sessionFactory;

    public RepositorioConductorImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Conductor buscarConductor(String email, String password) {
        return (Conductor) sessionFactory.getCurrentSession()
                .createCriteria(Conductor.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("password", password))
                .uniqueResult();
    }

    @Override
    public Conductor buscarPorId(Long idConductor) {
        return (Conductor) sessionFactory.getCurrentSession()
                .createCriteria(Conductor.class)
                .add(Restrictions.eq("id", idConductor))
                .uniqueResult();
    }

    //Para conductores que se registran
    @Override
    public void guardarConductor(Conductor conductor) {
        conductor.setCuentaHabilitada(false);
        sessionFactory.getCurrentSession().save(conductor);
    }

    //Para conductores ya registrados
    @Override
    public void actualizarConductor(Conductor conductor) {
        sessionFactory.getCurrentSession().saveOrUpdate(conductor);
    }

    @Override
    public List<Viaje> obtenerViajesDelConductorPorEstado(Long idConductor, EstadoDeViaje estado) {
        return (List<Viaje>) sessionFactory.getCurrentSession()
                .createCriteria(Viaje.class, "v")
                .createAlias("v.conductor", "c")
                .createAlias("v.paradas", "vp")
                .createAlias("vp.parada", "p")
                .add(Restrictions.eq("c.id", idConductor))
                .add(Restrictions.eq("v.estadoDeViaje", estado))
                .addOrder(Order.asc("vp.orden")) // ✅ AGREGADO
                .list();
    }

    @Override
    public List<Viaje> obtenerViajesDisponiblesParaConductor() {
        return (List<Viaje>) sessionFactory.getCurrentSession()
                .createCriteria(Viaje.class, "v")
                .createAlias("v.paradas", "vp")
                .createAlias("vp.parada", "p")
                .add(Restrictions.eq("v.estadoDeViaje", EstadoDeViaje.DISPONIBLE))
                .add(Restrictions.isNull("v.conductor"))
                .addOrder(Order.asc("vp.orden"))
                .list();
    }

    @Override
    public Viaje buscarViajePorId(Long idViaje) {
        return (Viaje) sessionFactory.getCurrentSession()
                .createCriteria(Viaje.class)
                .add(Restrictions.eq("id", idViaje))
                .uniqueResult();
    }

    @Override
    public Viaje obtenerViajeEnCursoDelConductor(Long idConductor) {
        return (Viaje) sessionFactory.getCurrentSession()
                .createCriteria(Viaje.class, "v")
                .createAlias("v.conductor", "c")
                .createAlias("v.paradas", "vp")
                .createAlias("vp.parada", "p")
                .add(Restrictions.eq("c.id", idConductor))
                .add(Restrictions.eq("v.estadoDeViaje", EstadoDeViaje.EN_CURSO))
                .uniqueResult();
    }

    @Override
    public void guardarViaje(Viaje viaje) {
        sessionFactory.getCurrentSession().saveOrUpdate(viaje);
    }

    @Override
    public Combi obtenerCombiActivaPorIdConductor(Long idConductor) {
        AsignacionCombiConductor asignacion =
                (AsignacionCombiConductor) sessionFactory.getCurrentSession()
                        .createCriteria(AsignacionCombiConductor.class)
                        .createAlias("conductor", "c")
                        .add(Restrictions.eq("c.id", idConductor))
                        .add(Restrictions.eq("combiActiva", true))
                        .uniqueResult();

        if (asignacion == null) {
            return null;
        }

        return asignacion.getCombi();
    }

    @Override
    public void actualizarEstadoCombi(Combi combiEnViaje) {
        sessionFactory.getCurrentSession().saveOrUpdate(combiEnViaje);
    }

    @Override
    public void guardarFalla(ReporteFalla reporteFalla) {
        sessionFactory.getCurrentSession().save(reporteFalla);
    }

}