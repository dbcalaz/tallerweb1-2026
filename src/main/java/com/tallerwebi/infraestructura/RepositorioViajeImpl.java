package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;

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
                .add(eq("origen", origen))
                .add(eq("destino", destino))
                .add(eq("fecha", fecha))
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

    @Override
    public Long contarViajesPorUsuario(long idUsuario) {
        return (Long) sessionFactory.getCurrentSession()
                .createCriteria(Reserva.class)
                .add(eq("usuario.id", idUsuario))
                .add(eq("estadoReserva", EstadoReserva.FINALIZADA))
                .setProjection(Projections.rowCount())
                .uniqueResult();
    }

    @Override
    public Long contarViajesCanceladosPorUsuario(long idUsuario) {
        return (Long) sessionFactory.getCurrentSession()
                .createCriteria(Reserva.class)
                .add(eq("usuario.id", idUsuario))
                .add(eq("estadoReserva", EstadoReserva.CANCELADA))
                .setProjection(Projections.rowCount())
                .uniqueResult();
    }

    @Override
    public List<Parada> obtenerParadasPorIds(List<Long> idsParadasIntermedias) {

        // 1. Validación de seguridad fundamental
        if (idsParadasIntermedias == null || idsParadasIntermedias.isEmpty()) {
            return new ArrayList<>(); // Si no mandaron paradas, devolvemos una lista vacía
        }

        // 2. Consulta con Criteria
        return sessionFactory.getCurrentSession()
                .createCriteria(Parada.class)
                .add(Restrictions.in("id", idsParadasIntermedias)) // Buscamos en la propiedad "id"
                .list();
    }

    @Override
    public List<Parada> getParadasDisponibles() {
        return sessionFactory.getCurrentSession().createCriteria(Parada.class).list();
    }


}