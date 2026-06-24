package com.tallerwebi.dominio.excepcion;

public class ValorDeViajeIncompleto extends BondiWayException {
    public ValorDeViajeIncompleto() {
        super("Los valores de Destino y origen deben ser completados");
    }
}
