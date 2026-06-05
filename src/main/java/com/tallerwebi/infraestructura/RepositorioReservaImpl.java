package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioReserva;
import com.tallerwebi.dominio.Reserva;
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

    }

    @Override
    public List<Reserva> buscarUltimasReservasPorUsuario(long idUsuario) {

        return (List<Reserva>) sessionFactory
                .getCurrentSession()
                .createCriteria(Reserva.class)
                .add(Restrictions.eq("usuario.id",idUsuario))
                .addOrder(Order.desc("id"))
                .list();

    }

}
