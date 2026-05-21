package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Combi;
import com.tallerwebi.dominio.ServicioCombi;
import com.tallerwebi.dominio.TipoDeCombi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class ControladorCrearCombiTest {

    private final Integer NUMERO_ASIENTOS = 20;
    private final TipoDeCombi tipoCombi = TipoDeCombi.ESTANDAR;
    private final String transmision= "MANUAL";
    Combi combi = new Combi();


    private ControladorCrearCombi controladorCrearCombi = new ControladorCrearCombi() ;

    @Test
    public void siSeIngresaAsientosTipoCombiYTransmisionElRegistroEsCorrecto(){

        //preparacioon
        givenExisteUnaCombi();
        //ejecuto
        ModelAndView mv = whenCreoUnaCombie(combi);
        //verifico
        thenELRegistroDeCombiEsExitoso(mv);

    }

    private void thenELRegistroDeCombiEsExitoso(ModelAndView mv) {
    assertThat(mv.getViewName(),equalToIgnoringCase("combi-registrada"));


    }

    private ModelAndView whenCreoUnaCombie(Combi datosCombi) {
        combi.setTipoDeTransmision(transmision);
        combi.setTipoDeCombi(tipoCombi);
        combi.setCantidadDeAsientos(NUMERO_ASIENTOS);
        ModelAndView mav= controladorCrearCombi.crearCombi(combi);

         return new ModelAndView("combi-registrada");
    }

    private void givenExisteUnaCombi() {
    }
    @Test
    public void siSeNoIngresaAsientosTipoCombiYTransmisionElRegistroEsIncorrecto(){

        //preparacioon
        givenExisteUnaCombi();
        //ejecuto
        ModelAndView mv = whenCreoUnaCombieSinTodosLosParametros(combi);
        //verifico
        thenELRegistroDeCombiEsErroneo(mv);

    }

    private ModelAndView whenCreoUnaCombieSinTodosLosParametros(Combi combi) {
        combi.setTipoDeTransmision("MANUAL");
        combi.setCantidadDeAsientos(11);
        combi.setTipoDeCombi(null);
        ModelAndView mav= controladorCrearCombi.crearCombi(combi);

        return new ModelAndView("crear-combi");
    }

    private void thenELRegistroDeCombiEsErroneo(ModelAndView mv) {
        assertThat( mv.getViewName(),equalToIgnoringCase("crear-combi"));

    }
}
