package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.CantidadDeAsientosInvalidaException;
import com.tallerwebi.dominio.excepcion.TipoDeCombiInvalidaException;
import com.tallerwebi.dominio.excepcion.TipoDeTransmisionInvalidaException;
import org.junit.jupiter.api.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServicioCombiTest {


    private final Integer cantidadAsientos =11;
    private final Integer cantidadAsientosErroneo =5;
    private final String transmision = "MANUAL";
    private final TipoDeCombi tipoDeCombi =TipoDeCombi.ESTANDAR;
    ServicioCombi servicioCombi = new ServicioCombiImplements();

    @Test
    public void siSeIngresaAsientosTipoDeCombiYTransmisionDeFormaCorrectaLaCreacionEsExitosa(){
        givenNoExisteCombi();

       Combi  combiCreada= whenCreoCombi(cantidadAsientos,transmision,tipoDeCombi);

       thenLaCreacionEsExitosa(combiCreada);

    }
    private void thenLaCreacionEsExitosa(Combi combiCreada) {
        assertThat(combiCreada,is(notNullValue()));
    }

    private Combi whenCreoCombi(Integer cantidadAsientos, String transmision, TipoDeCombi tipoDeCombi) {
     return  servicioCombi.crearCombi(cantidadAsientos,tipoDeCombi,transmision);
    }

    private void givenNoExisteCombi() {
    }
    @Test
    public void siIngresoErroneamenteAsientosYTransmisionYtipoDeCombiDeFormaCorrectaLaCreacionFalla(){
        givenNoExisteCombi();
        assertThrows(CantidadDeAsientosInvalidaException.class, ()-> whenCreoCombi(cantidadAsientosErroneo,transmision,tipoDeCombi));

    }

    @Test
    public void siIngresoAsientosYTipoDeCombiCorrectosYTransmisionDeFormaIncorrectaLaCreacionFalla(){
        givenNoExisteCombi();
        assertThrows(TipoDeTransmisionInvalidaException.class, ()-> whenCreoCombi(cantidadAsientos,"manual",tipoDeCombi));

    }
    @Test
    public void siIngresoAsientosYTransmisionCorrectosYTipoDeCombiDeFormaIncorrectaLaCreacionFalla(){
        givenNoExisteCombi();
        assertThrows(TipoDeCombiInvalidaException.class, ()-> whenCreoCombi(cantidadAsientos,transmision,null));

    }
    @Test
    public void siIngresoNueveAsientosLaCreacionFalla() {

    givenNoExisteCombi();
    assertThrows(CantidadDeAsientosInvalidaException.class, ()-> whenCreoCombi(9,"MANUAL",tipoDeCombi));
    }
    @Test
    public void siIngresoVeintiUnoAsientosLaCreacionFalla(){
        givenNoExisteCombi();
        assertThrows(CantidadDeAsientosInvalidaException.class, ()-> whenCreoCombi(21,"AUTOMATICA",tipoDeCombi));

    }


}
