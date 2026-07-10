package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioReserva;
import com.tallerwebi.dominio.Reserva;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioReservaImpl implements RepositorioReserva {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {this.sessionFactory = sessionFactory;}

    @Override
    public void guardar(Reserva reserva) {
        sessionFactory.getCurrentSession().save(reserva);
    }

    @Override
    public List<Reserva> buscarUltimasReservasPorUsuario(long idUsuario) {

        return (List<Reserva>) sessionFactory
                .getCurrentSession()
                .createCriteria(Reserva.class)
                .add(Restrictions.eq("usuario.id",idUsuario))
                .addOrder(Order.desc("id"))
                .setMaxResults(4)
                .list();

    }

    @Override
    public Reserva buscarReservaPorId(Long id){

        Reserva reserva = (Reserva) sessionFactory
                .getCurrentSession()
                .createCriteria(Reserva.class)
                .setFetchMode("pasajeros", FetchMode.JOIN)
                .setFetchMode("viaje", FetchMode.JOIN)
                .add(Restrictions.eq("id", id))
                .uniqueResult();

        return reserva;
    }

    @Override
    public void actualizarEstadoReserva(Reserva reserva) {
        sessionFactory.getCurrentSession().update(reserva);
    }
}
