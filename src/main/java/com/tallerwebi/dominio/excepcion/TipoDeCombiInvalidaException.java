package com.tallerwebi.dominio.excepcion;

import com.tallerwebi.dominio.BondiWayException;
import com.tallerwebi.dominio.TipoDeCombi;

public class TipoDeCombiInvalidaException extends BondiWayException {

    public TipoDeCombiInvalidaException() {
        super("El tipo de combi es incorrecta");
    }
}
