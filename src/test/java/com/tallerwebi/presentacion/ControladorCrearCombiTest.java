package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.BondiWayException;
import com.tallerwebi.dominio.excepcion.CantidadDeAsientosInvalidaException;
import com.tallerwebi.dominio.excepcion.TipoDeCombiInvalidaException;
import com.tallerwebi.dominio.excepcion.TipoDeTransmisionInvalidaException;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class ControladorCrearCombiTest {

    private final Integer NUMERO_ASIENTOS = 18;
    private final TipoDeCombi tipoCombi = TipoDeCombi.ESTANDAR;
    private final String transmision= "MANUAL";
    private final TipoDeCombi tipoCombi2 = TipoDeCombi.TURISTICA;
    private final String patente="abc1234";
    private final String marca= "ford";
    private final String modelo= "trafic";


    ServicioCombi servicioCombi= mock(ServicioCombiImplements.class);
    ControladorCrearCombi controladorCrearCombi = new ControladorCrearCombi(servicioCombi) ;

    @Test
    public void siSeIngresaAsientosTipoDeCombiYTransmisionDeFormaCorrectaLaCreacionEsExitosa(){
        givenNoExisteUnaCombi();
        DatosCombi datosCombi= new DatosCombi(NUMERO_ASIENTOS,tipoCombi,transmision,patente,marca,modelo,1555);
        ModelAndView mv = whenCreoUnaCombie(datosCombi);
        thenLaCreacionDeCombiEsExitoso(mv);
    }

    private void thenLaCreacionDeCombiEsExitoso(ModelAndView mv) {
        assertThat(mv.getViewName(),equalToIgnoringCase("admin/combi-registrada"));
    }

    private ModelAndView whenCreoUnaCombie(DatosCombi datosCombi) {
        return controladorCrearCombi.crearCombi(datosCombi);
    }

    @Test
    public void siIngresoErroneamenteAsientosYTransmisionYtipoDeCombiDeFormaCorrectaLaCreacionFalla() throws BondiWayException {
        givenNoExisteUnaCombi();
        DatosCombi datosCombi = new DatosCombi(1,tipoCombi,transmision,patente,marca,modelo,1666);
        doThrow(new CantidadDeAsientosInvalidaException()).when(servicioCombi).crearCombi(datosCombi);
        ModelAndView mv = whenCreoUnaCombie(datosCombi);
        thenLaCreacionDeCombiEsErroneo(mv,"La cantidad de asientos debe estar entre 10 y 20");
    }

    @Test
    public void siIngresoAsientosYTipoDeCombiCorrectosYTransmisionDeFormaIncorrectaLaCreacionFalla() throws BondiWayException {
        givenNoExisteUnaCombi();
        DatosCombi datosCombi= new DatosCombi(11,tipoCombi,"monual",patente,marca,modelo,1555);
        doThrow(new TipoDeTransmisionInvalidaException()).when(servicioCombi).crearCombi(datosCombi);
        ModelAndView mv = whenCreoUnaCombie(datosCombi);
        thenLaCreacionDeCombiEsErroneo(mv,"El tipo de transmision es incorrecta");
    }

    @Test
    public void siIngresoAsientosYTipoDeTransmisionCorrectosYTipoDeCombiDeFormaIncorrectaLaCreacionFalla() throws BondiWayException {
        givenNoExisteUnaCombi();
        DatosCombi datosCombi= new DatosCombi(11,tipoCombi2,transmision,patente,marca,modelo,1111);
        doThrow(new TipoDeCombiInvalidaException()).when(servicioCombi).crearCombi(datosCombi);
        ModelAndView mv = whenCreoUnaCombie(datosCombi);
        thenLaCreacionDeCombiEsErroneo(mv,"El tipo de combi es incorrecta");
    }

    private void givenNoExisteUnaCombi() {
    }

    private void thenLaCreacionDeCombiEsErroneo(ModelAndView mv, String mensaje) {
        // Se aplico correccion: Se actualiza la ruta esperada a admin/crear-combi
        assertThat( mv.getViewName(),equalToIgnoringCase("admin/crear-combi"));
        assertThat(mv.getModel().get("error").toString(),equalToIgnoringCase(mensaje));
    }
}