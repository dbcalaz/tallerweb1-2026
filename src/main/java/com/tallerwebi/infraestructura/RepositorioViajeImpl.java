package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioViaje;
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
    public List<Viaje> buscarViajes(String origen, String destino, String fecha) {
        return sessionFactory.getCurrentSession().createCriteria(Viaje.class)
                .add(Restrictions.eq("origen", origen))
                .add(Restrictions.eq("destino", destino))
                .add(Restrictions.eq("fecha", fecha))
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


}