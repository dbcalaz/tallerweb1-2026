package com.tallerwebi.dominio.excepcion;

import com.tallerwebi.dominio.BondiWayException;

public class CantidadDeAsientosInvalidaException extends BondiWayException {
    public CantidadDeAsientosInvalidaException() {
        super("La cantidad de asientos debe estar entre 10 y 20");
    }
}
