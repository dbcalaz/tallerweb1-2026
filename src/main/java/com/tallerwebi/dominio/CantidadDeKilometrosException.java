package com.tallerwebi.dominio;

public class CantidadDeKilometrosException extends BondiWayException {
    public CantidadDeKilometrosException() {
        super("La cantidad de kilometros no puede ser negativas");
    }
}
