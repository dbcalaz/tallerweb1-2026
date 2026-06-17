package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioViaje;
import com.tallerwebi.dominio.Reserva;
import com.tallerwebi.dominio.Viaje;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public List<Viaje> buscarViajes(String origen, String destino, String fecha, Integer pasajeros) {
        return sessionFactory.getCurrentSession().createCriteria(Viaje.class)
                .add(Restrictions.eq("origen", origen))
                .add(Restrictions.eq("destino", destino))
                .add(Restrictions.eq("fecha", fecha))
                .add(Restrictions.ge("asientosDisponibles", pasajeros))
                .list();
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
        String hql = "SELECT r.numeroAsiento FROM Reserva r WHERE r.viaje.id = :idViaje AND r.numeroAsiento IS NOT NULL";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Integer.class)
                .setParameter("idViaje", idViaje)
                .getResultList();
    }

    @Override
    public void eliminarReserva(Long idReserva) {
        sessionFactory.getCurrentSession()
                .createQuery("DELETE FROM Reserva WHERE id = :idReserva")
                .setParameter("idReserva", idReserva)
                .executeUpdate();
    }
}