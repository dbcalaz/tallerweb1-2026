package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.BondiWayException;
import com.tallerwebi.presentacion.DatosCrearViaje;
import com.tallerwebi.presentacion.DatosFiltro;

import java.util.List;

public interface ServicioAdministrador {

    List<ReporteFalla> obtenerFallasDeCombis();

    List<Conductor> obtenerConductores();

    void asignarNuevaCombiAConductor(Long idReporte, Long idCombi);

    List<Combi> obtenerCombisFiltradas(DatosFiltro datosFiltro);
    List <Combi> obtenerCombisDisponibles();

    Long obtenerCantidadCombis();

    List<Conductor> obtenerConductoresPendientes();

    Long obtenerCantidadDeConductoresPendientes();

    void habilitarConductor(Long idConductor, Long idCombi);

    void suspenderConductor(Long idConductor);

    void reactivarConductor(Long idConductor);

    void actualizarEstadoCombi(Long idCombi, EstadoDeCombi estado);


    void guardarViaje(DatosCrearViaje datosCrearViaje) throws BondiWayException;
}
