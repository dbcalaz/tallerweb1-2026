package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DatosFiltro;

import java.util.List;

public interface RepositorioAdministrador {

    /*Combis*/
    List<ReporteFalla> getFallas();

    Combi buscarCombiPorId(Long idCombi);

    void guardarAsignacion(AsignacionCombiConductor asignacion);

    void updateCombiConductor(Long idReporte, Long idCombi);

    AsignacionCombiConductor buscarAsignacionActiva(Long idConductor);

    List<Combi> getCombisDisponibles();

    List<Combi> getCombisFiltradas(DatosFiltro datosFiltro);

    void actualizarCombi(Combi combiExiste);

    Long getCantidadDeCombis();

    /* Conductores*/

    List<Conductor> getConductores(Boolean estadoCuenta);

    Conductor buscarConductorPorId(Long idConductor);

    void actualizarConductor(Conductor conductor);

    void suspenderConductor(Conductor conductor);

    void reactivarConductor(Conductor conductor);


}
