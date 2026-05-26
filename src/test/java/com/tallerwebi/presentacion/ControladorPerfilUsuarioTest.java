package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPerfilUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.mock;

public class ControladorPerfilUsuarioTest {

    private ServicioPerfilUsuario perfilUsuarioMock;
    private ControladorPerfilUsuario controladorPerfilUsuario;

    @BeforeEach
    public void init(){
        this.perfilUsuarioMock = mock(ServicioPerfilUsuario.class);
        this.controladorPerfilUsuario = new ControladorPerfilUsuario(this.perfilUsuarioMock);

    }

    @Test
    public void queUnUsuarioPuedaVerSuPerfil(){

        givenSeVePerfil();
        DatosLogin datosLogin = null;
        ModelAndView mav = whenVerPerfil(datosLogin);
        thenElPerfilEstaActivo(mav);
    }

    private void thenElPerfilEstaActivo(ModelAndView mav) {
        assertThat(mav.getViewName(), equalToIgnoringCase("perfilUsuario"));
    }

    private ModelAndView whenVerPerfil(DatosLogin datosLogin) {
        ModelAndView mav = controladorPerfilUsuario.verPerfil(datosLogin);
        return mav;
    }

    private void givenSeVePerfil() {
    }
}
