package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioAdministrador {

    /*Combis*/
    List<ReporteFalla> getFallas();

    Combi buscarCombiPorId(Long idCombi);

    List<Conductor> getConductores();

    void guardarAsignacion(AsignacionCombiConductor asignacion);

    void updateCombiConductor(Long idReporte, Long idCombi);

    AsignacionCombiConductor buscarAsignacionActiva(Long idConductor);

    List<Combi> getCombis();

    Long getCantidadDeCombis();

    /* Conductores*/

    List<Conductor> getConductoresPendientes();

    Long getCantidadDeConductoresPendientes();

    Conductor buscarConductorPorId(Long idConductor);

    void actualizarConductor(Conductor conductor);

    void suspenderConductor(Conductor conductor);

    void reactivarConductor(Conductor conductor);
}
