package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.presentacion.DatosFiltro;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class RepositorioAdministradorImpl implements RepositorioAdministrador {

    private SessionFactory sessionFactory;

    public RepositorioAdministradorImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /* Combis */
    @Override
    public List<ReporteFalla> getFallas() {
        return sessionFactory.getCurrentSession()
                .createCriteria(ReporteFalla.class)
                .list();
    }

    @Override
    public AsignacionCombiConductor buscarAsignacionActiva(Long idConductor) {
        return (AsignacionCombiConductor) sessionFactory.getCurrentSession()
                .createCriteria(AsignacionCombiConductor.class)
                .add(Restrictions.eq("conductor.id", idConductor))
                .add(Restrictions.eq("combiActiva", true))
                .uniqueResult();
    }

    @Override
    public void guardarAsignacion(AsignacionCombiConductor asignacion) {
        sessionFactory.getCurrentSession().save(asignacion);
    }

    @Override
    public void updateCombiConductor(Long idReporte, Long idCombi) {
        ReporteFalla reporte = (ReporteFalla) sessionFactory.getCurrentSession()
                .createCriteria(ReporteFalla.class)
                .add(Restrictions.eq("id", idReporte))
                .uniqueResult();

        Combi nuevaCombi = (Combi) sessionFactory.getCurrentSession()
                .createCriteria(Combi.class)
                .add(Restrictions.eq("id", idCombi))
                .uniqueResult();

        Conductor conductor = reporte.getConductor();

        AsignacionCombiConductor asignacionActual = buscarAsignacionActiva(conductor.getId());

        asignacionActual.setCombiActiva(false);

        sessionFactory.getCurrentSession().update(asignacionActual);

        AsignacionCombiConductor nuevaAsignacion = new AsignacionCombiConductor();

        nuevaAsignacion.setConductor(conductor);
        nuevaAsignacion.setCombi(nuevaCombi);
        nuevaAsignacion.setCombiActiva(true);
        sessionFactory.getCurrentSession().save(nuevaAsignacion);

        reporte.setFechaCreacionReporte(new Date());
        sessionFactory.getCurrentSession().update(reporte);
    }

   /* @Override
    public List<Combi> getCombis() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Combi.class)
                .list();
    }

    @Override
    public List<Combi> obtenerCombisFiltradas(String valorDeBusqueda) {
        EstadoDeCombi estado = EstadoDeCombi.valueOf(valorDeBusqueda);

        return sessionFactory.getCurrentSession()
                .createCriteria(Combi.class)
                .add(Restrictions.eq("estadoDeCombi", estado))
                .list();
    }*/



    @Override
    public void actualizarCombi(Combi combiExiste) {
        sessionFactory.getCurrentSession().update(combiExiste);
    }

    @Override
    public List<Combi> getCombisDisponibles(){
        return sessionFactory.getCurrentSession()
                .createCriteria(Combi.class).add(Restrictions.eq("estadoDeCombi", EstadoDeCombi.DISPONIBLE))
                .list();
    }

    @Override
    public List<Combi> getCombisFiltradas(DatosFiltro datosFiltro) {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Combi.class);

        if (datosFiltro == null) {
            return criteria.list();
        }

        // Filtro Exacto (Enum)
        if (datosFiltro.getEstadoDeCombi() != null) {
            criteria.add(Restrictions.eq("estadoDeCombi", datosFiltro.getEstadoDeCombi()));
        }

        // Filtros de Texto (Usamos ilike y % para búsquedas parciales y case-insensitive)
        if (datosFiltro.getMarca() != null && !datosFiltro.getMarca().trim().isEmpty()) {
            criteria.add(Restrictions.ilike("marca", "%" + datosFiltro.getMarca() + "%"));
        }

        if (datosFiltro.getModelo() != null && !datosFiltro.getModelo().trim().isEmpty()) {
            criteria.add(Restrictions.ilike("modelo", "%" + datosFiltro.getModelo() + "%"));
        }

        if (datosFiltro.getPatente() != null && !datosFiltro.getPatente().trim().isEmpty()) {
            criteria.add(Restrictions.ilike("patente", "%" + datosFiltro.getPatente() + "%"));
        }

        return criteria.list();
    }

    public Long getCantidadDeCombis() {
        return (Long) sessionFactory.getCurrentSession()
                .createCriteria(Combi.class)
                .setProjection(Projections.rowCount())
                .uniqueResult();
    }

    @Override
    public Combi buscarCombiPorId(Long idCombi) {
        return (Combi) sessionFactory.getCurrentSession()
                .createCriteria(Combi.class)
                .add(Restrictions.eq("id", idCombi))
                .uniqueResult();
    }

    /* Conductores*/
    @Override
    public List<Conductor> getConductores() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Conductor.class)
                .add(Restrictions.eq("cuentaHabilitada", true))
                //.add(Restrictions.eq("suspendido", false))
                .list();
    }

    @Override
    public List<Conductor> getConductoresPendientes() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Conductor.class)
                .add(Restrictions.eq("cuentaHabilitada",false))
                .list();
    }

    @Override
    public Long getCantidadDeConductoresPendientes() {
        return (Long) sessionFactory.getCurrentSession()
                .createCriteria(Conductor.class)
                .add(Restrictions.eq("cuentaHabilitada",false))
                .setProjection(Projections.rowCount())
                .uniqueResult();
    }

    @Override
    public Conductor buscarConductorPorId(Long idConductor) {
        return (Conductor) sessionFactory.getCurrentSession()
                .createCriteria(Conductor.class)
                .add(Restrictions.eq("id", idConductor))
                .uniqueResult();
    }

    @Override
    public void actualizarConductor(Conductor conductor) {
        sessionFactory.getCurrentSession().update(conductor);
    }

    @Override
    public void suspenderConductor(Conductor conductor) {
        conductor.setSuspendido(true);
        conductor.setEnViaje(false);
        conductor.setDisponible(false);
        sessionFactory.getCurrentSession().update(conductor);
    }

    @Override
    public void reactivarConductor(Conductor conductor) {
        conductor.setSuspendido(false);
        conductor.setEnViaje(false);
        conductor.setDisponible(true);
        sessionFactory.getCurrentSession().update(conductor);
    }






}
