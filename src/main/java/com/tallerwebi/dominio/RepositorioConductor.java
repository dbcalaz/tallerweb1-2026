package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioConductor {

    Conductor buscarConductor(String email, String password);

    Conductor buscarPorId(Long idConductor);

    void guardarConductor(Conductor conductor);

    List<Viaje> obtenerViajesDelConductorPorEstado(Long idConductor, EstadoDeViaje estado);

    List<Viaje> obtenerViajesDisponiblesParaConductor();

    Viaje buscarViajePorId(Long idViaje);

    Viaje obtenerViajeEnCursoDelConductor(Long idConductor);

    void guardarViaje(Viaje viaje);

    Combi obtenerCombiActivaPorIdConductor(Long idConductor);

    void guardarFalla(ReporteFalla reporteFalla);

    void actualizarConductor(Conductor conductor);
}