package com.tallerwebi.dominio.excepcion;

public class ValorDeHoraYFechaDestinoIncompleta extends BondiWayException {
    public ValorDeHoraYFechaDestinoIncompleta() {
        super("Los campos de fecha y hora son obligatorios");
    }
}
