package com.tallerwebi.dominio.excepcion;

import com.tallerwebi.dominio.BondiWayException;

public class CombiExistenteException extends BondiWayException {
    public CombiExistenteException(String patente) {
        super("La combi con la patente " + patente + " ya esta registrada en el sistema.");
    }
}
