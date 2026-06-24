package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DatosFiltro;

import java.util.List;

public interface ServicioAdministrador {

    /*Combis*/
    List<ReporteFalla> obtenerFallasDeCombis();

    void asignarNuevaCombiAConductor(Long idReporte, Long idCombi);

    List<Combi> obtenerCombisFiltradas(DatosFiltro datosFiltro);

    List <Combi> obtenerCombisDisponibles();

    Long obtenerCantidadCombis();

    /*Conductor*/
    List<Conductor> obtenerConductores(Boolean estadoCuenta, String estado);

    void habilitarConductor(Long idConductor, Long idCombi);

    void suspenderConductor(Long idConductor);

    void reactivarConductor(Long idConductor);

    void actualizarEstadoCombi(Long idCombi, EstadoDeCombi estado);


}
