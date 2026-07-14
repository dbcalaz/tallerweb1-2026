package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDate;

import static org.hibernate.criterion.Restrictions.eq;

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

    // ------------------------
    //NOTA IMPORTANTE  ----------->  REEMPLAZAR TODAS LAS CONSULTAS POR CRITERIA
    // ------------------------
    @Override
    public List<Viaje> buscarViajes(Long idOrigen, Long idDestino, String fecha, Integer pasajeros) {
        String hql = "SELECT DISTINCT v FROM Viaje v " +
                "JOIN v.paradas vpOrigen " +
                "JOIN v.paradas vpDestino " +
                "WHERE vpOrigen.parada.id = :idOrigen " +
                "AND vpDestino.parada.id = :idDestino " +
                "AND vpOrigen.orden < vpDestino.orden " +
                "AND v.fecha = :fecha " +
                "AND v.asientosDisponibles >= :pasajeros";

        LocalDate fechaLocalDate = LocalDate.parse(fecha);

        return sessionFactory.getCurrentSession()
                .createQuery(hql, Viaje.class)
                .setParameter("idOrigen", idOrigen)
                .setParameter("idDestino", idDestino)
                .setParameter("fecha", fechaLocalDate)
                .setParameter("pasajeros", pasajeros)
                .getResultList();
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
        return (Long) sessionFactory.getCurrentSession()
                .createCriteria(Reserva.class)
                .add(eq("usuario.id", idUsuario))
                .add(eq("estadoReserva", EstadoReserva.FINALIZADA))
                .setProjection(Projections.rowCount())
                .uniqueResult();
    }

    @Override
    public Long contarViajesCanceladosPorUsuario(long idUsuario) {
        return (Long) sessionFactory.getCurrentSession()
                .createCriteria(Reserva.class)
                .add(eq("usuario.id", idUsuario))
                .add(eq("estadoReserva", EstadoReserva.CANCELADA))
                .setProjection(Projections.rowCount())
                .uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Viaje> buscarUltimosViajesDelUsuario(Long idUsuario) {
        String hql = "FROM Viaje v WHERE v.usuario.id = :idUsuario ORDER BY v.id DESC";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Viaje.class)
                .setParameter("idUsuario", idUsuario)
                .setMaxResults(3)
                .getResultList();
    }

    @Override
    public void guardarReserva(Reserva reserva) {
        sessionFactory.getCurrentSession().save(reserva);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> obtenerAsientosOcupados(Long idViaje) {
        // Agregamos la condición para excluir reservas canceladas
        String hql = "SELECT p.numeroAsiento FROM Pasajero p WHERE p.reserva.viaje.id = :idViaje AND p.numeroAsiento IS NOT NULL AND p.reserva.estadoReserva NOT IN ('CANCELADA', 'CANCELADA_POR_CONDUCTOR')";

        return sessionFactory.getCurrentSession()
                .createQuery(hql, Integer.class)
                .setParameter("idViaje", idViaje)
                .getResultList();
    }

    @Override
    public void eliminarReserva(Long idReserva) {
        Reserva reserva = sessionFactory.getCurrentSession().get(Reserva.class, idReserva);
        if (reserva != null) {
            sessionFactory.getCurrentSession().delete(reserva);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Reserva> buscarReservasPorEstado(Long idUsuario, EstadoReserva estado) {
        String hql = "SELECT DISTINCT r FROM Reserva r " +
                "LEFT JOIN FETCH r.pasajeros " +
                "WHERE r.usuario.id = :idUsuario " +
                "AND r.estadoReserva = :estado";

        return sessionFactory.getCurrentSession()
                .createQuery(hql, Reserva.class)
                .setParameter("idUsuario", idUsuario)
                .setParameter("estado", estado)
                .getResultList();
    }
}