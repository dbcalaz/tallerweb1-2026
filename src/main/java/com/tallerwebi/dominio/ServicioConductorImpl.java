package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ConductorExistente;
import com.tallerwebi.dominio.excepcion.CuentaNoHabilitadaException;
import com.tallerwebi.dominio.excepcion.CuentaSuspendidaException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioConductorImpl implements ServicioConductor {

    private final RepositorioConductor repositorioConductor;

    public ServicioConductorImpl(RepositorioConductor repositorioConductor) {
        this.repositorioConductor = repositorioConductor;
    }

    @Override
    public Conductor consultarConductor(String email, String password)
            throws CuentaNoHabilitadaException, CuentaSuspendidaException {

        Conductor conductor = repositorioConductor.buscarConductor(email, password);

        if (conductor != null && !conductor.isCuentaHabilitada()) {
            throw new CuentaNoHabilitadaException("La cuenta no esta habilitada por un administrador.");
        }

        if (conductor != null && conductor.isSuspendido()) {
            throw new CuentaSuspendidaException("Su cuenta se encuentra suspendida.");
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
    public Conductor buscarPorId(Long idConductor) {
        validarIdConductor(idConductor);
        return repositorioConductor.buscarPorId(idConductor);
    }

    @Override
    public List<Viaje> obtenerViajesDelConductor(Long idConductor) {
        validarIdConductor(idConductor);
        return repositorioConductor.obtenerViajesDelConductor(idConductor);
    }

    @Override
    public List<Viaje> obtenerViajesDelConductorPorEstado(Long idConductor, EstadoDeViaje estado) {
        validarIdConductor(idConductor);

        if (estado == null) {
            throw new IllegalArgumentException("El estado del viaje es obligatorio");
        }

        return repositorioConductor.obtenerViajesDelConductorPorEstado(idConductor, estado);
    }

    @Override
    public List<Viaje> obtenerViajesDisponibles() {
        return repositorioConductor.obtenerViajesDisponiblesParaConductor();
    }

    @Override
    public Viaje obtenerViajeEnCursoDelConductor(Long idConductor) {
        validarIdConductor(idConductor);
        return repositorioConductor.obtenerViajeEnCursoDelConductor(idConductor);
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

        viaje.setConductor(conductor);
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

        if (viaje.getEstadoDeViaje() != EstadoDeViaje.ASIGNADO) {
            throw new IllegalStateException("Solo se puede iniciar un viaje asignado");
        }

        Viaje viajeEnCurso = repositorioConductor.obtenerViajeEnCursoDelConductor(idConductor);
        if (viajeEnCurso != null && !viajeEnCurso.getId().equals(idViaje)) {
            throw new IllegalStateException("Ya tenés otro viaje en curso");
        }

        conductor.setEnViaje(true);
        conductor.setDisponible(false);

        viaje.setEstadoDeViaje(EstadoDeViaje.EN_CURSO);

        repositorioConductor.actualizarConductor(conductor);
        repositorioConductor.guardarViaje(viaje);
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

        if (viaje.getEstadoDeViaje() != EstadoDeViaje.EN_CURSO) {
            throw new IllegalStateException("Solo se puede finalizar un viaje en curso");
        }

        viaje.setEstadoDeViaje(EstadoDeViaje.FINALIZADO);

        if (conductor.isSuspensionPendiente()) {
            conductor.setEnViaje(false);
            conductor.setDisponible(false);
            conductor.setSuspendido(true);
            conductor.setSuspensionPendiente(false);
        } else {
            conductor.setEnViaje(false);
            conductor.setDisponible(true);
        }

        repositorioConductor.actualizarConductor(conductor);
        repositorioConductor.guardarViaje(viaje);
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

        repositorioConductor.guardarFalla(reporteFalla);
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