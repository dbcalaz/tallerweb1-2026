package com.tallerwebi.dominio.excepcion;

public class ValorDeDistanciaYKmNoPermitido extends BondiWayException {
    public ValorDeDistanciaYKmNoPermitido() {
        super("No se puede incluir valores negativos en distancia y km");
    }
}
