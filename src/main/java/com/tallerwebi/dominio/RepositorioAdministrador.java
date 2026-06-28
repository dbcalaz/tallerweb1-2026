package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DatosCrearViaje;
import com.tallerwebi.presentacion.DatosFiltro;

import java.util.List;

public interface RepositorioAdministrador {

    /*Combis*/
    List<ReporteFalla> getFallas();

    Combi buscarCombiPorId(Long idCombi);

    void guardarAsignacion(AsignacionCombiConductor asignacion);

    void updateCombiConductor(Long idReporte, Long idCombi);

    AsignacionCombiConductor buscarAsignacionActiva(Long idConductor);

    List<Combi> getCombisPorEstado(EstadoDeCombi estado);

    List<Combi> getCombisFiltradas(DatosFiltro datosFiltro);

    void actualizarCombi(Combi combiExiste);

    /* Conductores*/

    List<Conductor> getConductores(Boolean cuentaHabilitada, String estado);

    Conductor buscarConductorPorId(Long idConductor);

    void actualizarConductor(Conductor conductor);

    void suspenderConductor(Conductor conductor);

    void reactivarConductor(Conductor conductor);

    /*Viajes*/
    List<Parada> getParadas();

    void insertNuevoViaje(Viaje viaje, DatosCrearViaje datos);

    List<Viaje> getViajes();

}
