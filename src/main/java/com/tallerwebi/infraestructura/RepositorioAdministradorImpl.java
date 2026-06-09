package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.SessionFactory;
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

    @Override
    public List<ReporteFalla> getFallas() {

        return sessionFactory.getCurrentSession()
                .createCriteria(ReporteFalla.class)
                .setResultTransformer(org.hibernate.Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @Override
    public List<Conductor> getConductores() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Conductor.class)
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

        reporte.setResuelta(true);
        reporte.setFechaRealizadoReporte(new Date());

        sessionFactory.getCurrentSession().update(reporte);
    }

    @Override
    public List<Combi> getCombis() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Combi.class)
                .list();
    }
}
