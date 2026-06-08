package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Conductor;
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
                .setMaxResults(3)
                .list();

    }

    @Override
    public Conductor obtenerConductorFavorito(Long idUsuario) {

        List<Reserva> reservas = sessionFactory
                .getCurrentSession()
                .createCriteria(Reserva.class)
                .add(Restrictions.eq("usuario.id", idUsuario))
                .list();

        Conductor favorito = null;
        int maxCantidad = 0;

        for (Reserva reserva : reservas) {
            Conductor conductor = reserva.getViaje().getConductor();

            int cantidad = 0;

            for (Reserva otraReserva : reservas) {
                if (otraReserva.getViaje()
                        .getConductor()
                        .getId()
                        .equals(conductor.getId())) {
                    cantidad++;

                }
            }

            if (cantidad > maxCantidad) {
                maxCantidad = cantidad;
                favorito = conductor;
            }
        }

        return favorito;
    }

}
