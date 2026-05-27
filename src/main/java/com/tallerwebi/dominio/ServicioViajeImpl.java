package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioViajeImpl implements ServicioViaje {

    private RepositorioViaje repositorioViaje;

    @Autowired
    public ServicioViajeImpl(RepositorioViaje repositorio) {
        this.repositorioViaje = repositorio;
    }

    @Override
    public void confirmarViaje(Viaje viaje) {
        repositorioViaje.guardarViaje(viaje);
    }

    @Override
    public List<Viaje> buscarViajes(String origen, String destino, String fecha) {
        return repositorioViaje.buscarViajes(origen, destino, fecha);
    }

    @Override
    public void reservarAsiento(Long idViaje, Usuario usuarioLogueado) {
        Viaje viaje = repositorioViaje.buscarPorId(idViaje);
        if (viaje != null && viaje.getAsientosDisponibles() > 0) {
            viaje.setAsientosDisponibles(viaje.getAsientosDisponibles() - 1);
            repositorioViaje.actualizar(viaje);
        } else {
            throw new RuntimeException("El viaje no existe o no tiene asientos disponibles.");
        }
    }

    @Override
    public Viaje buscarPorId(Long id) {
        return repositorioViaje.buscarPorId(id);
    }
}