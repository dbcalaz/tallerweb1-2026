package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioCombi {
    void guardar(Combi combi);

    Combi buscarPorPatente(String patente);

    List<Combi> obtenerTodasLasCombis();
}
