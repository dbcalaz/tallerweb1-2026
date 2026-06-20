package com.tallerwebi.dominio.excepcion;

public class TipoDeTransmisionInvalidaException extends BondiWayException {

    public TipoDeTransmisionInvalidaException() {
        super("El tipo de transmision es incorrecta");
    }
}
