package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Conductor;
import com.tallerwebi.dominio.RepositorioConductor;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class RepositorioConductorImpl implements RepositorioConductor {

    private SessionFactory sessionFactory;

    public RepositorioConductorImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    @Override
    public Conductor buscarConductor(String email, String password) {

        return (Conductor) sessionFactory.getCurrentSession().createCriteria(Conductor.class).add(Restrictions.eq("email", email)).add(Restrictions.eq("password", password)).uniqueResult();
    }

    @Override
    public void guardarConductor(Conductor conductor) {
        sessionFactory.getCurrentSession().save(conductor);
    }
}
