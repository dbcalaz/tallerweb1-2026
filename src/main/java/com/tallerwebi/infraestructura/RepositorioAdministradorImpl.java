package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.presentacion.DatosCrearViaje;
import com.tallerwebi.presentacion.DatosFiltro;
import org.hibernate.Criteria;
import org.hibernate.Session;
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

    @Override
    public Combi buscarCombiPorId(Long idCombi) {
        return (Combi) sessionFactory.getCurrentSession()
                .createCriteria(Combi.class)
                .add(Restrictions.eq("id", idCombi))
                .uniqueResult();
    }

    /* Conductores*/
    @Override
    public List<Conductor> getConductores(Boolean cuentaHabilitada, String estado) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Conductor.class);

        if (cuentaHabilitada != null) {
            criteria.add(Restrictions.eq("cuentaHabilitada", cuentaHabilitada));
        }

        if (estado != null && !estado.isEmpty() && !estado.equalsIgnoreCase("todos")) {
            switch (estado.toLowerCase()) {
                case "disponible":
                    criteria.add(Restrictions.eq("disponible", true));
                    break;

                case "en_viaje":
                    criteria.add(Restrictions.eq("enViaje", true));
                    break;

                case "suspendido":
                    criteria.add(Restrictions.eq("suspendido", true));
                    break;

                case "suspension_pendiente":
                    criteria.add(Restrictions.eq("suspensionPendiente", true));
                    break;
            }
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
        if (conductor.isEnViaje()) {
            conductor.setSuspensionPendiente(true);
        } else {
            conductor.setSuspendido(true);
            conductor.setDisponible(false);
            conductor.setEnViaje(false);
            conductor.setSuspensionPendiente(false);
        }
        sessionFactory.getCurrentSession().update(conductor);
    }

    @Override
    public void reactivarConductor(Conductor conductor) {
        conductor.setSuspendido(false);
        conductor.setSuspensionPendiente(false);
        conductor.setEnViaje(false);
        conductor.setDisponible(true);
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
    public List<Viaje> getViajes() {
        return sessionFactory.getCurrentSession()
                .createCriteria(Viaje.class, "v")
                .createAlias("paradas", "p")
                .createAlias("p.parada", "parada")
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }
}
