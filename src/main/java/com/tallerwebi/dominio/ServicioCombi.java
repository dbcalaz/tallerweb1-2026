package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.BondiWayException;
import com.tallerwebi.presentacion.DatosCombi;

public interface ServicioCombi {


    Combi crearCombi(DatosCombi datosCombi) throws BondiWayException;

   // List<Combi> obtenerFlota();


    ;
}
