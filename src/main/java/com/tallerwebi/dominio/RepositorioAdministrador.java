package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioAdministrador {

    List<ReporteFalla> getFallas();

    List<Conductor> getConductores();

    void updateCombiConductor(Long idReporte, Long idCombi);

    AsignacionCombiConductor buscarAsignacionActiva(Long idConductor);

    List<Combi> getCombis();
}
