package com.tallerwebi.dominio;

public interface RepositorioCombi {
    void guardar(Combi combi);

    Combi buscarPorPatente(String patente);
}
