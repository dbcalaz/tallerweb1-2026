package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DatosCrearViaje;
import com.tallerwebi.presentacion.DatosFiltro;
import com.tallerwebi.presentacion.DatosFiltroViaje;

import java.util.List;

public interface ServicioAdministrador {

    /*Combis*/
    List<ReporteFalla> obtenerFallasDeCombis();

    void asignarNuevaCombiAConductor(Long idReporte, Long idCombi);

    List<Combi> obtenerCombisFiltradas(DatosFiltro datosFiltro);

    List<Combi> obtenerCombisPorEstado(EstadoDeCombi estado);

    void resolverFalla(Long idReporte);

    /*Conductor*/
    List<Conductor> obtenerConductores(Boolean estadoCuenta, EstadoConductor estado);

    void habilitarConductor(Long idConductor, Long idCombi);

    void suspenderConductor(Long idConductor);

    void reactivarConductor(Long idConductor);

    void actualizarEstadoCombi(Long idCombi, EstadoDeCombi estado);

    /*Viajes*/
    List<Parada> obtenerParadas();

    void crearNuevoViaje(DatosCrearViaje datos);

    List<Viaje> obtenerViajes(DatosFiltroViaje filtro);

    void rechazarSolicitud(Long idConductor);
}
