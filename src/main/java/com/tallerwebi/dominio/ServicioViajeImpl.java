package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DatosBusqueda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class ServicioViajeImpl implements ServicioViaje {

    private RepositorioViaje repositorioViaje;
    private RepositorioReserva repositorioReserva;

    @Autowired
    public ServicioViajeImpl(RepositorioViaje repositorioViaje, RepositorioReserva repositorioReserva) {
        this.repositorioViaje = repositorioViaje;
        this.repositorioReserva = repositorioReserva;
    }

    @Override
    public void confirmarViaje(Viaje viaje) {
        repositorioViaje.guardarViaje(viaje);
    }

    @Override
    public List<Viaje> buscarViajes(DatosBusqueda datosBusqueda) {
        return repositorioViaje.buscarViajes(datosBusqueda.getIdOrigen(), datosBusqueda.getIdDestino(), datosBusqueda.getFecha(), datosBusqueda.getPasajeros());
    }

    @Override
    public List<Parada> obtenerTodasLasParadas() {
        return repositorioViaje.obtenerTodasLasParadas();
    }

    @Override
    public void reservarAsiento(Long idViaje, Usuario usuarioLogueado) {
        Viaje viaje = repositorioViaje.buscarPorId(idViaje);
        if (viaje != null && viaje.getAsientosDisponibles() > 0) {
            viaje.setAsientosDisponibles(viaje.getAsientosDisponibles() - 1);
            repositorioViaje.actualizar(viaje);
        } else {
            throw new RuntimeException("El viaje seleccionado no posee asientos disponibles.");
        }
    }

    @Override
    public void liberarAsiento(Long idViaje) {
        Viaje viaje = repositorioViaje.buscarPorId(idViaje);
        if (viaje != null) {
            viaje.setAsientosDisponibles(viaje.getAsientosDisponibles() + 1);
            repositorioViaje.actualizar(viaje);
        }
    }

    @Override
    public Viaje buscarPorId(Long id) {
        return repositorioViaje.buscarPorId(id);
    }

    @Override
    public void guardarReserva(Reserva reserva) {
        repositorioViaje.guardarReserva(reserva);
    }

    @Override
    public List<Integer> obtenerAsientosOcupados(Long idViaje) {
        return repositorioViaje.obtenerAsientosOcupados(idViaje);
    }

    @Override
    public void eliminarReserva(Long idReserva) {
        repositorioViaje.eliminarReserva(idReserva);
    }

    @Override
    public void verificarViajes24Horas(Long idViaje) {
        Viaje viaje = repositorioViaje.buscarPorId(idViaje);

        if (viaje != null) {
            int capacidadTotal = viaje.getCombi().getCantidadDeAsientos();
            int cupoMinimo = (int) (capacidadTotal * 0.30);

            int asientosOcupados = capacidadTotal - viaje.getAsientosDisponibles();

            if (viaje.getConductor() == null || asientosOcupados < cupoMinimo) {
                viaje.setEstadoDeViaje(EstadoDeViaje.CANCELADO);
                repositorioViaje.actualizar(viaje);
            }
        }
    }

    // Calculamos el precio en base a un viaje y los tramos específicos buscados
    public double calcularPrecioPorTramo(Viaje viaje, Long idOrigen, Long idDestino) {
        int totalTramos = viaje.getParadas().size() - 1;

        if (totalTramos <= 0) {
            return viaje.getPrecio();
        }

        ViajeParada paradaOrigen = null;
        ViajeParada paradaDestino = null;

        for (ViajeParada vp : viaje.getParadas()) {
            if (vp.getParada().getId().equals(idOrigen)) {
                paradaOrigen = vp;
            }
            if (vp.getParada().getId().equals(idDestino)) {
                paradaDestino = vp;
            }
        }

        if (paradaOrigen == null || paradaDestino == null) {
            return viaje.getPrecio();
        }

        int tramosDelPasajero = paradaDestino.getOrden() - paradaOrigen.getOrden();
        if (tramosDelPasajero <= 0) {
            return viaje.getPrecio();
        }

        double precioPorTramo = viaje.getPrecio() / (double) totalTramos;
        return precioPorTramo * tramosDelPasajero;
    }

    // Este metodo tiene que ser llamado por el metodo que crea la reserva
    public double calcularPrecio(Reserva reserva) {
        if (reserva.getParadaOrigen() == null || java.util.Objects.equals(reserva.getParadaOrigen(), null) || reserva.getParadaDestino() == null) {
            return reserva.getViaje().getPrecio();
        }

        Viaje viaje = reserva.getViaje();
        int totalTramos = viaje.getParadas().size() - 1;

        if (totalTramos <= 0) {
            return viaje.getPrecio();
        }

        int tramosDelPasajero = reserva.getParadaDestino().getOrden() - reserva.getParadaOrigen().getOrden();
        double precioPorTramo = viaje.getPrecio() / (double) totalTramos;

        return precioPorTramo * tramosDelPasajero;
    }

    // Modificamos para calcular en base al orden inicial (así el origen suma 0 minutos)
    public LocalTime calcularHorarioParada(Viaje viaje, ViajeParada viajeParada) {
        if (viaje.getParadas() == null || viaje.getParadas().isEmpty()) {
            return viaje.getHorario();
        }
        // Obtenemos el orden de la primera parada del recorrido completo
        int ordenBase = viaje.getParadas().get(0).getOrden();

        // Calculamos la diferencia de paradas multiplicada por 15 minutos
        int minutosEstimados = (viajeParada.getOrden() - ordenBase) * 15;

        return viaje.getHorario().plusMinutes(minutosEstimados);
    }

    @Override
    public List<Reserva> buscarReservasPorEstado(Long idUsuario, EstadoReserva estado) {
        List<Reserva> misReservas = repositorioViaje.buscarReservasPorEstado(idUsuario, estado);

        for (Reserva reserva : misReservas) {
            org.hibernate.Hibernate.initialize(reserva.getPasajeros());
            org.hibernate.Hibernate.initialize(reserva.getViaje());

            if (reserva.getViaje() != null) {
                org.hibernate.Hibernate.initialize(reserva.getViaje().getParadas());
                org.hibernate.Hibernate.initialize(reserva.getViaje().getCombi());
            }
        }

        return misReservas;
    }
}