package com.tallerwebi.dominio.excepcion;

import com.tallerwebi.dominio.BondiWayException;

public class TipoDeTransmisionInvalidaException extends BondiWayException {

    public TipoDeTransmisionInvalidaException() {
        super("El tipo de transmision es incorrecta");
    }
}
