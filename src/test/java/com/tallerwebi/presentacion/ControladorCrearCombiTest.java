package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class ControladorCrearCombiTest {

    private final Integer NUMERO_ASIENTOS = 20;
    private final TipoDeCombi tipoCombi = TipoDeCombi.ESTANDAR;
    private final String transmision= "MANUAL";
    private final TipoDeCombi tipoCombi2 = TipoDeCombi.TURISTICA;


    ServicioCombi servicioCombi= mock(ServicioCombiImplements.class);
    ControladorCrearCombi controladorCrearCombi = new ControladorCrearCombi(servicioCombi) ;

    @Test
    public void siSeIngresaAsientosTipoDeCombiYTransmisionDeFormaCorrectaLaCreacionEsExitosa(){

        //preparacioon
        givenNoExisteUnaCombi();
        DatosCombi datosCombi= new DatosCombi(NUMERO_ASIENTOS,tipoCombi,transmision);
        //ejecuto
        ModelAndView mv = whenCreoUnaCombie(datosCombi);
        //verifico
        thenLaCreacionDeCombiEsExitoso(mv);



    }

    private void thenLaCreacionDeCombiEsExitoso(ModelAndView mv) {
    assertThat(mv.getViewName(),equalToIgnoringCase("combi-registrada"));
   // assertThat(mv.getModel().get());

    }

    private ModelAndView whenCreoUnaCombie(DatosCombi datosCombi) {
       return controladorCrearCombi.crearCombi(datosCombi);
    }

    @Test
    public void siIngresoErroneamenteAsientosYTransmisionYtipoDeCombiDeFormaCorrectaLaCreacionFalla(){

        //preparacioon
        givenNoExisteUnaCombi();
        //seteo el comportamiento de mi mock de servicioCrearCombi
        doThrow(CantidadDeAsientosInvalidaException.class).when(servicioCombi).crearCombi(1,tipoCombi,transmision);
        DatosCombi datosCombi= new DatosCombi(1,tipoCombi,transmision);
        //ejecuto
        ModelAndView mv = whenCreoUnaCombie(datosCombi);
        //verifico
        thenLaCreacionDeCombiEsErroneo(mv,"La cantidad de asientos debe estar entre 10 y 20");

    }
    @Test
    public void siIngresoAsientosYTipoDeCombiCorrectosYTransmisionDeFormaIncorrectaLaCreacionFalla(){
        givenNoExisteUnaCombi();
        doThrow(TipoDeTransmisionInvalidaException.class).when(servicioCombi).crearCombi(11,tipoCombi,"monual");
        DatosCombi datosCombi= new DatosCombi(11,tipoCombi,"monual");
        ModelAndView mv = whenCreoUnaCombie(datosCombi);
        thenLaCreacionDeCombiEsErroneo(mv,"El tipo de transmision es incorrecta");
    }

    @Test
    public void siIngresoAsientosYTipoDeTransmisionCorrectosYTipoDeCombiDeFormaIncorrectaLaCreacionFalla(){
        givenNoExisteUnaCombi();
        doThrow(TipoDeCombiInvalidaException.class).when(servicioCombi).crearCombi(11,tipoCombi2,transmision);
        DatosCombi datosCombi= new DatosCombi(11,tipoCombi2,transmision);
        ModelAndView mv = whenCreoUnaCombie(datosCombi);
        thenLaCreacionDeCombiEsErroneo(mv,"El tipo de combi es incorrecta");
    }




    private void givenNoExisteUnaCombi() {
    }


    private void thenLaCreacionDeCombiEsErroneo(ModelAndView mv, String mensaje) {
        assertThat( mv.getViewName(),equalToIgnoringCase("crear-combi"));
        assertThat(mv.getModel().get("error").toString(),equalToIgnoringCase(mensaje));

    }
}
