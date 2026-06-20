package com.tallerwebi.dominio.excepcion;

public class CantidadDeAsientosInvalidaException extends BondiWayException {
    public CantidadDeAsientosInvalidaException() {
        super("La cantidad de asientos debe estar entre 10 y 20");
    }
}
