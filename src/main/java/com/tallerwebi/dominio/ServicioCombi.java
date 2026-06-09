package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.CantidadDeAsientosInvalidaException;
import com.tallerwebi.presentacion.DatosCombi;

import java.util.List;

public interface ServicioCombi {


    Combi crearCombi(DatosCombi datosCombi) throws  BondiWayException;

   // List<Combi> obtenerFlota();


    ;
}
