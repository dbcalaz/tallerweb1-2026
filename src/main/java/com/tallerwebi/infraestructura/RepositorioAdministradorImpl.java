package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ReporteFalla;
import com.tallerwebi.dominio.RepositorioAdministrador;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioAdministradorImpl implements RepositorioAdministrador {

    private SessionFactory sessionFactory;

    public RepositorioAdministradorImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<ReporteFalla> getFallas() {

        return sessionFactory.getCurrentSession()
                .createCriteria(ReporteFalla.class)
                .setResultTransformer(org.hibernate.Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }
}
