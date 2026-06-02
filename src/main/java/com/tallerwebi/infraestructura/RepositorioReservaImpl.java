package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioReserva;
import com.tallerwebi.dominio.Reserva;
import org.hibernate.SessionFactory;
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
        String hql =
                "FROM Reserva r " +
                        "WHERE r.usuario.id = :idUsuario " +
                        "ORDER BY r.id DESC";

        return sessionFactory
                .getCurrentSession()
                .createQuery(hql, Reserva.class)
                .setParameter("idUsuario", idUsuario)
                .setMaxResults(3)
                .getResultList();
    }

}
