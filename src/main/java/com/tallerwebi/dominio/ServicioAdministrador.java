package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioAdministrador {

    List<ReporteFalla> obtenerFallasDeCombis();

    List<Conductor> obtenerConductores();

    void asignarNuevaCombiAConductor(Long idReporte, Long idCombi);

    List<Combi> obtenerCombis();
    List<Combi> obtenerCombis(String parametro);
    List <Combi> obtenerCombisDisponibles();

    Long obtenerCantidadCombis();

    List<Conductor> obtenerConductoresPendientes();

    Long obtenerCantidadDeConductoresPendientes();

    void habilitarConductor(Long idConductor, Long idCombi);

    void suspenderConductor(Long idConductor);

    void reactivarConductor(Long idConductor);

    void actualizarEstadoCombi(Long idCombi, EstadoDeCombi estado);
}
