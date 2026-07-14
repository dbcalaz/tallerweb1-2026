package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioConductorImpl implements ServicioConductor {

    private final RepositorioConductor repositorioConductor;
    private ServicioPuntuacion servicioPuntuacion;

    public ServicioConductorImpl(RepositorioConductor repositorioConductor,  ServicioPuntuacion servicioPuntuacion) {
        this.repositorioConductor = repositorioConductor;
        this.servicioPuntuacion = servicioPuntuacion;
    }

    @Override
    public Conductor consultarConductor(String email, String password)
            throws CuentaNoHabilitadaException, CuentaSuspendidaException, SolicitudRechazadaException {

        Conductor conductor = repositorioConductor.buscarConductor(email, password);

        if (conductor != null && !conductor.isCuentaHabilitada() && conductor.getEstadoConductor().equals(EstadoConductor.PENDIENTE_APROBACION)) {
            throw new CuentaNoHabilitadaException("La cuenta no esta habilitada por un administrador.");
        }

        if (conductor != null && conductor.getEstadoConductor().equals(EstadoConductor.SUSPENDIDO)) {
            throw new CuentaSuspendidaException("Su cuenta se encuentra suspendida.");
        }

        if (conductor != null && conductor.getEstadoConductor().equals(EstadoConductor.RECHAZADO)) {
            throw new SolicitudRechazadaException("Su solicitud fue rechazada.");
        }

        return conductor;
    }

    @Override
    public void registrarConductor(Conductor conductor) throws ConductorExistente {
        Conductor conductorExistente =
                repositorioConductor.buscarConductor(conductor.getEmail(), conductor.getPassword());

        if (conductorExistente != null) {
            throw new ConductorExistente();
        }

        repositorioConductor.guardarConductor(conductor);
    }

    @Override
    @Transactional
    public Conductor buscarPorId(Long id) {
        Conductor conductor = repositorioConductor.buscarPorId(id);

        if (conductor != null) {
            servicioPuntuacion.actualizarPromedioConductor(conductor);
        }

        return conductor;
    }

    @Override
    public List<Viaje> obtenerViajesDelConductorPorEstado(Long idConductor, EstadoDeViaje estado) {
        validarIdConductor(idConductor);

        if (estado == null) {
            throw new IllegalArgumentException("El estado del viaje es obligatorio");
        }

        List<Viaje> viajes = repositorioConductor.obtenerViajesDelConductorPorEstado(idConductor, estado);

        for (Viaje viaje : viajes) {
            org.hibernate.Hibernate.initialize(viaje.getReservas());
            for (Reserva reserva : viaje.getReservas()) {
                org.hibernate.Hibernate.initialize(reserva.getPasajeros());
            }
        }

        return viajes;
    }

    @Override
    public List<Viaje> obtenerViajesDisponibles() {
        return repositorioConductor.obtenerViajesDisponiblesParaConductor();
    }

    @Override
    public Viaje obtenerViajeEnCursoDelConductor(Long idConductor) {
        validarIdConductor(idConductor);

        Viaje viaje = repositorioConductor.obtenerViajeEnCursoDelConductor(idConductor);

        if (viaje != null) {
            org.hibernate.Hibernate.initialize(viaje.getReservas());
            for (Reserva reserva : viaje.getReservas()) {
                org.hibernate.Hibernate.initialize(reserva.getPasajeros());
            }
        }

        return viaje;
    }

    @Override
    public Combi buscarCombiActivePorIdConductor(Long idConductor) {
        validarIdConductor(idConductor);
        return repositorioConductor.obtenerCombiActivaPorIdConductor(idConductor);
    }

    @Override
    public void aceptarViaje(Long idViaje, Long idConductor) {
        validarIdViaje(idViaje);
        validarIdConductor(idConductor);

        Conductor conductor = repositorioConductor.buscarPorId(idConductor);
        if (conductor == null) {
            throw new IllegalArgumentException("No existe el conductor");
        }

        Viaje viajeEnCurso = repositorioConductor.obtenerViajeEnCursoDelConductor(idConductor);
        if (viajeEnCurso != null) {
            throw new IllegalStateException("No podés aceptar un viaje porque ya tenés un viaje en curso");
        }

        List<Viaje> viajesAsignados =
                repositorioConductor.obtenerViajesDelConductorPorEstado(idConductor, EstadoDeViaje.ASIGNADO);

        if (viajesAsignados != null && !viajesAsignados.isEmpty()) {
            throw new IllegalStateException("Ya tenés un viaje asignado pendiente de iniciar");
        }

        Viaje viaje = repositorioConductor.buscarViajePorId(idViaje);
        if (viaje == null) {
            throw new IllegalArgumentException("No existe el viaje");
        }

        if (viaje.getEstadoDeViaje() != EstadoDeViaje.DISPONIBLE) {
            throw new IllegalStateException("El viaje ya no está disponible");
        }

        if (viaje.getConductor() != null) {
            throw new IllegalStateException("El viaje ya tiene un conductor asignado");
        }

        Combi combi = repositorioConductor.obtenerCombiActivaPorIdConductor(conductor.getId());

        viaje.setConductor(conductor);
        viaje.setCombi(combi);
        viaje.setAsientosDisponibles(combi.getCantidadDeAsientos());
        viaje.setEstadoDeViaje(EstadoDeViaje.ASIGNADO);

        repositorioConductor.guardarViaje(viaje);
    }

    @Override
    public void iniciarViaje(Long idViaje, Long idConductor) {
        validarIdViaje(idViaje);
        validarIdConductor(idConductor);

        Viaje viaje = repositorioConductor.buscarViajePorId(idViaje);
        if (viaje == null) {
            throw new IllegalArgumentException("No existe el viaje");
        }

        validarTitularidadDelViaje(viaje, idConductor);

        Conductor conductor = repositorioConductor.buscarPorId(idConductor);
        if (conductor == null) {
            throw new IllegalArgumentException("No existe el conductor");
        }

        Combi combiEnViaje = repositorioConductor.obtenerCombiActivaPorIdConductor(conductor.getId());

        if (viaje.getEstadoDeViaje() != EstadoDeViaje.ASIGNADO) {
            throw new IllegalStateException("Solo se puede iniciar un viaje asignado");
        }

        Viaje viajeEnCurso = repositorioConductor.obtenerViajeEnCursoDelConductor(conductor.getId());

        if (viajeEnCurso != null && !viajeEnCurso.getId().equals(idViaje)) {
            throw new IllegalStateException("Ya tenés otro viaje en curso");
        }

        conductor.setEstadoConductor(EstadoConductor.EN_VIAJE);
        combiEnViaje.setEstadoDeCombi(EstadoDeCombi.EN_VIAJE);
        viaje.setEstadoDeViaje(EstadoDeViaje.EN_CURSO);

        // SOLO pasamos a EN_CURSO las reservas que estaban CONFIRMADAS
        // Evitamos afectar a las que el usuario ya canceló previamente.
        for (Reserva reserva : viaje.getReservas()) {
            if (reserva.getEstadoReserva() == EstadoReserva.CONFIRMADA) {
                reserva.setEstadoReserva(EstadoReserva.EN_CURSO);
            }
        }

        repositorioConductor.actualizarConductor(conductor);
        repositorioConductor.guardarViaje(viaje);
        repositorioConductor.actualizarEstadoCombi(combiEnViaje);
    }

    @Override
    public void canelarViaje(Long idViaje, Long idConductor) {
        validarIdViaje(idViaje);
        validarIdConductor(idConductor);

        Viaje viaje = repositorioConductor.buscarViajePorId(idViaje);
        if (viaje == null) {
            throw new IllegalArgumentException("No existe el viaje");
        }

        validarTitularidadDelViaje(viaje, idConductor);

        Conductor conductor = repositorioConductor.buscarPorId(idConductor);
        if (conductor == null) {
            throw new IllegalArgumentException("No existe el conductor");
        }

        Combi combiEnViaje = repositorioConductor.obtenerCombiActivaPorIdConductor(conductor.getId());

        viaje.setEstadoDeViaje(EstadoDeViaje.CANCELADO);
        combiEnViaje.setEstadoDeCombi(EstadoDeCombi.DISPONIBLE);
        conductor.setEstadoConductor(EstadoConductor.DISPONIBLE);

        // Actualizo el estado de las reservas SOLAMENTE si estaban confirmadas.
        for (Reserva reserva : viaje.getReservas()) {
            if (reserva.getEstadoReserva() == EstadoReserva.CONFIRMADA) {
                reserva.setEstadoReserva(EstadoReserva.CANCELADA_POR_CONDUCTOR);
            }
        }

        repositorioConductor.actualizarConductor(conductor);
        repositorioConductor.guardarViaje(viaje);
        repositorioConductor.actualizarEstadoCombi(combiEnViaje);
    }

    @Override
    public void actualizarRecaudacionEmpleado(Viaje viaje, Conductor conductor){
        Double recaudacion = viaje.getRecaudacionTotal();
        Double gananciaConductor = recaudacion * 0.75;
        conductor.setGanancia(conductor.getGanancia() + gananciaConductor);
    }

    @Override
    public void finalizarViaje(Long idViaje, Long idConductor) {
        validarIdViaje(idViaje);
        validarIdConductor(idConductor);

        Viaje viaje = repositorioConductor.buscarViajePorId(idViaje);
        if (viaje == null) {
            throw new IllegalArgumentException("No existe el viaje");
        }

        validarTitularidadDelViaje(viaje, idConductor);

        Conductor conductor = repositorioConductor.buscarPorId(idConductor);
        if (conductor == null) {
            throw new IllegalArgumentException("No existe el conductor");
        }

        Combi combiEnViaje = repositorioConductor.obtenerCombiActivaPorIdConductor(conductor.getId());

        if (viaje.getEstadoDeViaje() != EstadoDeViaje.EN_CURSO) {
            throw new IllegalStateException("Solo se puede finalizar un viaje en curso");
        }

        viaje.setEstadoDeViaje(EstadoDeViaje.FINALIZADO);

        // ganancias del conductor
        actualizarRecaudacionEmpleado(viaje, conductor);

        combiEnViaje.setEstadoDeCombi(EstadoDeCombi.DISPONIBLE);

        if (conductor.getEstadoConductor().equals(EstadoConductor.SUSPENSION_PENDIENTE)) {
            conductor.setEstadoConductor(EstadoConductor.SUSPENDIDO);
        } else {
            conductor.setEstadoConductor(EstadoConductor.DISPONIBLE);
        }

        // Actualizo el estado de las reservas a FINALIZADA SOLAMENTE si estaban EN_CURSO
        for (Reserva reserva : viaje.getReservas()) {
            if (reserva.getEstadoReserva() == EstadoReserva.EN_CURSO) {
                reserva.setEstadoReserva(EstadoReserva.FINALIZADA);
            }
        }

        repositorioConductor.actualizarConductor(conductor);
        repositorioConductor.guardarViaje(viaje);
        repositorioConductor.actualizarEstadoCombi(combiEnViaje);
    }

    @Override
    public void registrarFalla(ReporteFalla reporteFalla) {
        if (reporteFalla == null) {
            throw new IllegalArgumentException("El reporte de falla es obligatorio");
        }

        if (reporteFalla.getCombi() == null) {
            throw new IllegalArgumentException("Falta la combi del reporte");
        }

        if (reporteFalla.getConductor() == null) {
            throw new IllegalArgumentException("Falta el conductor del reporte");
        }

        if (reporteFalla.getDescripcion() == null || reporteFalla.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción de la falla es obligatoria");
        }

        Combi combiConFalla = reporteFalla.getCombi();
        combiConFalla.setEstadoDeCombi(EstadoDeCombi.EN_MANTENIMIENTO);

        repositorioConductor.guardarFalla(reporteFalla);
        repositorioConductor.actualizarEstadoCombi(combiConFalla);
    }

    private void validarIdConductor(Long idConductor) {
        if (idConductor == null) {
            throw new IllegalArgumentException("El id del conductor es obligatorio");
        }
    }

    private void validarIdViaje(Long idViaje) {
        if (idViaje == null) {
            throw new IllegalArgumentException("El id del viaje es obligatorio");
        }
    }

    private void validarTitularidadDelViaje(Viaje viaje, Long idConductor) {
        if (viaje.getConductor() == null || viaje.getConductor().getId() == null) {
            throw new IllegalStateException("El viaje no tiene conductor asignado");
        }

        if (!viaje.getConductor().getId().equals(idConductor)) {
            throw new IllegalStateException("El viaje no pertenece al conductor logueado");
        }
    }
}