package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.presentacion.DatosCrearViaje;
import com.tallerwebi.presentacion.DatosFiltro;
import com.tallerwebi.presentacion.DatosFiltroViaje;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
    public ReporteFalla getReporteFallePorIdCombi(Long idCombi) {
        return (ReporteFalla) sessionFactory.getCurrentSession()
                .createCriteria(ReporteFalla.class)
                .createAlias("combi","c")
                .add(Restrictions.eq("c.id",idCombi))
                .uniqueResult();
    }

    @Override
    public ReporteFalla getReporteFallePorIdReporte(Long idReporte) {
        return (ReporteFalla) sessionFactory.getCurrentSession()
                .createCriteria(ReporteFalla.class)
                .add(Restrictions.eq("id",idReporte))
                .uniqueResult();
    }

    @Override
    public void updateFalla(ReporteFalla reporte) {
        sessionFactory.getCurrentSession().update(reporte);
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

        reporte.setFechaCreacionReporte(LocalDate.now());
        sessionFactory.getCurrentSession().update(reporte);
    }

    @Override
    public void actualizarCombi(Combi combiExiste) {
        sessionFactory.getCurrentSession().update(combiExiste);
    }

    @Override
    public List<Combi> getCombisPorEstado(EstadoDeCombi estado) {
        if (estado == null) {
            return sessionFactory.getCurrentSession()
                    .createCriteria(Combi.class)
                    .list();
        } else {
            return sessionFactory.getCurrentSession()
                    .createCriteria(Combi.class)
                    .add(Restrictions.eq("estadoDeCombi",estado))
                    .list();
        }
    }

    @Override
    public List<Combi> getCombisFiltradas(DatosFiltro datosFiltro) {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Combi.class);

        if (datosFiltro == null) {
            return criteria.list();
        }
        if (datosFiltro.getEstadoDeCombi() != null) {
            criteria.add(Restrictions.eq("estadoDeCombi", datosFiltro.getEstadoDeCombi()));
        }
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

    @Override
    public Combi buscarCombiPorId(Long idCombi) {
        return (Combi) sessionFactory.getCurrentSession()
                .createCriteria(Combi.class)
                .add(Restrictions.eq("id", idCombi))
                .uniqueResult();
    }

    /* Conductores*/
    @Override
    public List<Conductor> getConductores(Boolean cuentaHabilitada, EstadoConductor estado) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Conductor.class);

        if (cuentaHabilitada != null) {
            criteria.add(Restrictions.eq("cuentaHabilitada", cuentaHabilitada));
        }

        if (estado != null) {
            criteria.add(Restrictions.eq("estadoConductor", estado));
        }

        return criteria.list();
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
        if (conductor.getEstadoConductor().equals(EstadoConductor.EN_VIAJE)) {
            conductor.setEstadoConductor(EstadoConductor.SUSPENSION_PENDIENTE);
        } else {
            conductor.setEstadoConductor(EstadoConductor.SUSPENDIDO);
        }
        sessionFactory.getCurrentSession().update(conductor);
    }

    @Override
    public void reactivarConductor(Conductor conductor) {
        conductor.setEstadoConductor(EstadoConductor.DISPONIBLE);
        sessionFactory.getCurrentSession().update(conductor);
    }

    @Override
    public void rechazarSolicitudConductor(Conductor conductor) {
        conductor.setCuentaHabilitada(false);
        conductor.setEstadoConductor(EstadoConductor.RECHAZADO);
        sessionFactory.getCurrentSession().update(conductor);
    }

    /*Viajes*/
    @Override
    public List<Parada> getParadas() {
        return sessionFactory.getCurrentSession().createCriteria(Parada.class)
                .list();
    }

    @Override
    public void insertNuevoViaje(Viaje viaje, DatosCrearViaje datos) {
        Session session = sessionFactory.getCurrentSession();

        session.save(viaje);
        int orden = 1;

        Parada origen = session.get(Parada.class, datos.getOrigenId());

        ViajeParada vpOrigen = new ViajeParada();
        vpOrigen.setViaje(viaje);
        vpOrigen.setParada(origen);
        vpOrigen.setOrden(orden++);

        session.save(vpOrigen);

        if (datos.getParadasIntermedias() != null) {

            for (Long idParada : datos.getParadasIntermedias()) {

                Parada parada = session.get(Parada.class, idParada);

                ViajeParada vp = new ViajeParada();
                vp.setViaje(viaje);
                vp.setParada(parada);
                vp.setOrden(orden++);

                session.save(vp);
            }
        }

        Parada destino = session.get(Parada.class, datos.getDestinoId());

        ViajeParada vpDestino = new ViajeParada();
        vpDestino.setViaje(viaje);
        vpDestino.setParada(destino);
        vpDestino.setOrden(orden);

        session.save(vpDestino);
    }

    @Override
    public List<Viaje> getViajes(DatosFiltroViaje filtro) {

        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(Viaje.class, "v");

        if (filtro.getFecha() != null) {
            criteria.add(Restrictions.eq("v.fecha", filtro.getFecha()));
        }

        if (filtro.getTipoDeViaje() != null) {
            criteria.add(Restrictions.eq("v.tipoDeViaje", filtro.getTipoDeViaje()));
        }

        if (filtro.getEstadoDeViaje() != null) {
            criteria.add(Restrictions.eq("v.estadoDeViaje", filtro.getEstadoDeViaje()));
        }

        if (filtro.getIdConductor() != null) {
            criteria.createAlias("v.conductor", "conductor");
            criteria.add(Restrictions.eq("conductor.id", filtro.getIdConductor()));
        }

        criteria.createAlias("v.paradas", "vp");
        criteria.createAlias("vp.parada", "parada");

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        return criteria.list();
    }
}
