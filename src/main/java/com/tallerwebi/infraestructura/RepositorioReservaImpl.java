package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Conductor;
import com.tallerwebi.dominio.RepositorioReserva;
import com.tallerwebi.dominio.Reserva;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                .setMaxResults(4)
                .list();

    }

    @Override
    public Conductor obtenerConductorFavorito(Long idUsuario) {

        List<Reserva> reservas = sessionFactory
                .getCurrentSession()
                .createCriteria(Reserva.class)
                .add(Restrictions.eq("usuario.id", idUsuario))
                .list();

        Map<Conductor, Integer> contador = new HashMap<>();

        for (Reserva reserva : reservas) {
            Conductor conductor = reserva.getViaje().getConductor();

            contador.put(conductor, contador.getOrDefault(conductor, 0) + 1);
        }

        Conductor favorito = null;
        int max = 0;

        for (Map.Entry<Conductor, Integer> entry : contador.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                favorito = entry.getKey();
            }
        }

        return favorito;
//        Conductor favorito = null;
//        int maxCantidad = 0;
//
//        for (Reserva reserva : reservas) {
//            Conductor conductor = reserva.getViaje().getConductor();
//
//            int cantidad = 0;
//
//            for (Reserva otraReserva : reservas) {
//                if (otraReserva.getViaje()
//                        .getConductor()
//                        .getId()
//                        .equals(conductor.getId())) {
//                    cantidad++;
//
//                }
//            }
//
//            if (cantidad > maxCantidad) {
//                maxCantidad = cantidad;
//                favorito = conductor;
//            }
//        }
//
//        return favorito;
//    }

    }

    @Override
    public Reserva buscarReservaPorId(Long id){
        return (Reserva) sessionFactory
                .getCurrentSession()
                .createCriteria(Reserva.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }
}
