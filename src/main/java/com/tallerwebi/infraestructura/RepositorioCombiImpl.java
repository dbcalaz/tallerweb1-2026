package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Combi;
import com.tallerwebi.dominio.RepositorioCombi;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("RespositorioCombi")
public class RepositorioCombiImpl implements RepositorioCombi {


    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioCombiImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void guardar(Combi combi) {
        sessionFactory.getCurrentSession().save(combi);

    }

    @Override
    public Combi buscarPorPatente(String patente) {
        return (Combi) sessionFactory.getCurrentSession().createCriteria(Combi.class).add(Restrictions.eq("patente", patente)).uniqueResult();

    }

    @Override
    public Combi buscarPorId(Long idCombi) {
        return (Combi) sessionFactory.getCurrentSession().createCriteria(Combi.class).add(Restrictions.eq("id",idCombi)).uniqueResult();
    }
}



