package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Repository
public class RepositorioViajeImpl implements RepositorioViaje {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioViajeImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarViaje(Viaje viaje) {
        sessionFactory.getCurrentSession().save(viaje);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Viaje> buscarViajes(Long idOrigen, Long idDestino, String fecha, Integer pasajeros) {
        DetachedCriteria subqueryDestino = DetachedCriteria.forClass(ViajeParada.class, "vpDestino")
                .add(Restrictions.eq("vpDestino.parada.id", idDestino))
                .add(Restrictions.eqProperty("vpDestino.viaje.id", "v.id"))
                .setProjection(Projections.property("vpDestino.orden"));

        return sessionFactory.getCurrentSession().createCriteria(Viaje.class, "v")
                .createAlias("v.paradas", "vpOrigen")
                .add(Restrictions.eq("vpOrigen.parada.id", idOrigen))
                .add(Subqueries.propertyLt("vpOrigen.orden", subqueryDestino))
                .add(Restrictions.eq("v.fecha", LocalDate.parse(fecha)))
                .add(Restrictions.ge("v.asientosDisponibles", pasajeros))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Parada> obtenerTodasLasParadas() {
        return sessionFactory.getCurrentSession().createCriteria(Parada.class).list();
    }

    @Override
    public Viaje buscarPorId(Long id) {
        return sessionFactory.getCurrentSession().get(Viaje.class, id);
    }

    @Override
    public void actualizar(Viaje viaje) {
        sessionFactory.getCurrentSession().update(viaje);
    }

    @Override
    public Long contarViajesPorUsuario(long idUsuario) {
        return (Long) sessionFactory.getCurrentSession().createCriteria(Reserva.class)
                .add(Restrictions.eq("usuario.id", idUsuario))
                .add(Restrictions.eq("estadoReserva", EstadoReserva.FINALIZADA))
                .setProjection(Projections.rowCount())
                .uniqueResult();
    }

    @Override
    public Long contarViajesCanceladosPorUsuario(long idUsuario) {
        return (Long) sessionFactory.getCurrentSession().createCriteria(Reserva.class)
                .add(Restrictions.eq("usuario.id", idUsuario))
                .add(Restrictions.eq("estadoReserva", EstadoReserva.CANCELADA))
                .setProjection(Projections.rowCount())
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Viaje> buscarUltimosViajesDelUsuario(Long idUsuario) {
        return sessionFactory.getCurrentSession().createCriteria(Viaje.class)
                .add(Restrictions.eq("usuario.id", idUsuario))
                .addOrder(Order.desc("id"))
                .setMaxResults(3)
                .list();
    }

    @Override
    public void guardarReserva(Reserva reserva) {
        sessionFactory.getCurrentSession().save(reserva);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> obtenerAsientosOcupados(Long idViaje) {
        return sessionFactory.getCurrentSession().createCriteria(Pasajero.class, "p")
                .createAlias("p.reserva", "r")
                .add(Restrictions.eq("r.viaje.id", idViaje))
                .add(Restrictions.isNotNull("p.numeroAsiento"))
                .add(Restrictions.not(Restrictions.in("r.estadoReserva",
                        Arrays.asList(EstadoReserva.CANCELADA, EstadoReserva.CANCELADA_POR_CONDUCTOR))))
                .setProjection(Projections.property("p.numeroAsiento"))
                .list();
    }

    @Override
    public void eliminarReserva(Long idReserva) {
        Reserva reserva = sessionFactory.getCurrentSession().get(Reserva.class, idReserva);
        if (reserva != null) sessionFactory.getCurrentSession().delete(reserva);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Reserva> buscarReservasPorEstado(Long idUsuario, EstadoReserva estado) {
        return sessionFactory.getCurrentSession().createCriteria(Reserva.class, "r")
                .setFetchMode("r.pasajeros", FetchMode.JOIN)
                .add(Restrictions.eq("r.usuario.id", idUsuario))
                .add(Restrictions.eq("r.estadoReserva", estado))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }
}