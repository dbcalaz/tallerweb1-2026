package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ConductorExistente;
import com.tallerwebi.dominio.excepcion.CuentaNoHabilitadaException;
import com.tallerwebi.dominio.excepcion.CuentaSuspendidaException;

import java.util.List;

public interface ServicioConductor {

    Conductor consultarConductor(String email, String password)
            throws CuentaNoHabilitadaException, CuentaSuspendidaException;

    void registrarConductor(Conductor conductor) throws ConductorExistente;

    Conductor buscarPorId(Long idConductor);

    List<Viaje> obtenerViajesDelConductorPorEstado(Long idConductor, EstadoDeViaje estado);

    List<Viaje> obtenerViajesDisponibles();

    Viaje obtenerViajeEnCursoDelConductor(Long idConductor);

    Combi buscarCombiActivePorIdConductor(Long idConductor);

    void aceptarViaje(Long idViaje, Long idConductor);

    void iniciarViaje(Long idViaje, Long idConductor);

    void actualizarRecaudacionEmpleado(Viaje viaje, Conductor conductor);

    void finalizarViaje(Long idViaje, Long idConductor);

    void registrarFalla(ReporteFalla reporteFalla);

    void canelarViaje(Long idViaje, Long idConductor);
}