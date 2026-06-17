package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Puntuacion;
import com.tallerwebi.dominio.RepositorioPuntuacion;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Repository
@Transactional
public class RepositorioPuntuacionImpl implements RepositorioPuntuacion {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void guardarPuntuacion(Puntuacion puntuacion) {
        sessionFactory.getCurrentSession().save(puntuacion);
    }

    @Override
    public boolean existePuntuacioPorUsuarioYReserva(Long idUsuario, Long idReserva) {

        Long count = (Long) sessionFactory.getCurrentSession()
                .createCriteria(Puntuacion.class)
                .add(Restrictions.eq("usuario.id", idUsuario))
                .add(Restrictions.eq("reserva.id", idReserva))
                .setProjection(Projections.rowCount())
                .uniqueResult();

        return count != null && count > 0;
    }
}
