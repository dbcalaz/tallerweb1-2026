package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.RepositorioCombiImpl;
import com.tallerwebi.dominio.excepcion.CantidadDeAsientosInvalidaException;
import com.tallerwebi.dominio.excepcion.TipoDeCombiInvalidaException;
import com.tallerwebi.dominio.excepcion.TipoDeTransmisionInvalidaException;
import org.junit.jupiter.api.Test;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioCombiTest {


    private final Integer cantidadAsientos =11;
    private final Integer cantidadAsientosErroneo =5;
    private final String transmision = "MANUAL";
    private final TipoDeCombi tipoDeCombi =TipoDeCombi.ESTANDAR;
    private final String marca ="FORD";
    private final String modelo ="trafic";
    RepositorioCombi repositorioCombi= mock(RepositorioCombiImpl.class);
    ServicioCombi servicioCombi = new ServicioCombiImplements(repositorioCombi);

    @Test
    public void siSeIngresaAsientosTipoDeCombiYTransmisionDeFormaCorrectaLaCreacionEsExitosa(){
        givenNoExisteCombi();

       Combi  combiCreada= whenCreoCombi(cantidadAsientos,transmision,tipoDeCombi, "ABCD1234",marca,modelo);

       thenLaCreacionEsExitosa(combiCreada);

    }
    private void thenLaCreacionEsExitosa(Combi combiCreada) {
        verify(repositorioCombi,times(1)).guardar(combiCreada);
        assertThat(combiCreada,is(notNullValue()));
    }

    private Combi whenCreoCombi(Integer cantidadAsientos, String transmision, TipoDeCombi tipoDeCombi, String patente,String marca, String modelo) {
     return  servicioCombi.crearCombi(cantidadAsientos,tipoDeCombi,transmision,patente,marca,modelo);
    }

    private void givenNoExisteCombi() {
    }
    @Test
    public void siIngresoErroneamenteAsientosYTransmisionYtipoDeCombiDeFormaCorrectaLaCreacionFalla(){
        givenNoExisteCombi();
        assertThrows(CantidadDeAsientosInvalidaException.class, ()-> whenCreoCombi(cantidadAsientosErroneo,transmision,tipoDeCombi, "ABCD1234",marca,modelo));

    }

    @Test
    public void siIngresoAsientosYTipoDeCombiCorrectosYTransmisionDeFormaIncorrectaLaCreacionFalla(){
        givenNoExisteCombi();
        assertThrows(TipoDeTransmisionInvalidaException.class, ()-> whenCreoCombi(cantidadAsientos,"manual",tipoDeCombi, "ABCD1234",marca,modelo));

    }
    @Test
    public void siIngresoAsientosYTransmisionCorrectosYTipoDeCombiDeFormaIncorrectaLaCreacionFalla(){
        //given creo una combi
        assertThrows(TipoDeCombiInvalidaException.class, ()-> whenCreoCombi(cantidadAsientos,transmision,null, "ABCD1234",marca,modelo));

    }
    @Test
    public void siIngresoNueveAsientosLaCreacionFalla() {

    givenNoExisteCombi();
    assertThrows(CantidadDeAsientosInvalidaException.class, ()-> whenCreoCombi(9,"MANUAL",tipoDeCombi, "ABCD1234",marca,modelo));
    }
    @Test
    public void siIngresoVeintiUnoAsientosLaCreacionFalla(){
        givenNoExisteCombi();

        assertThrows(CantidadDeAsientosInvalidaException.class, ()-> whenCreoCombi(21,"AUTOMATICA",tipoDeCombi, "ABCD1234",marca,modelo));

    }
    @Test
    public void siSeAgregaCombiConPatenteRepetidaLaCreacionFalla(){

        when(repositorioCombi.buscarPorPatente("ABCD1234")).thenReturn(new Combi());






        //then
        assertThrows(CombiExistenteException.class, ()->whenCreoCombi(11,transmision,tipoDeCombi,"ABCD1234",marca,modelo));

    }


}
