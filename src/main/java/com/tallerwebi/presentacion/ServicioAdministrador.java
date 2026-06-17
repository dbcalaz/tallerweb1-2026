package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Combi;
import com.tallerwebi.dominio.Conductor;
import com.tallerwebi.dominio.EstadoDeCombi;
import com.tallerwebi.dominio.ReporteFalla;

import java.util.List;

public interface ServicioAdministrador {

    List<ReporteFalla> obtenerFallasDeCombis();

    List<Conductor> obtenerConductores();

    void asignarNuevaCombiAConductor(Long idReporte, Long idCombi);

    List<Combi> obtenerCombis();

    List<Combi> obtenerCombis(String valorDeBusqueda);

    void actualizarEstadoCombi(Long idCombi, EstadoDeCombi estado);
}
