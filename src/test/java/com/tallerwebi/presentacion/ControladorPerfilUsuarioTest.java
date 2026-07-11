package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.ServicioPerfilUsuario;
import com.tallerwebi.dominio.ServicioPuntuacion;
import com.tallerwebi.dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.when;

public class ControladorPerfilUsuarioTest {

    private ControladorPerfilUsuario controladorPerfilUsuario;
    private ServicioPerfilUsuario servicioPerfilUsuario;
    private ServicioPuntuacion servicioPuntuacion;
    private Usuario usuario;
    private HttpSession session;
    private HttpServletRequest request;

    @BeforeEach
    void init(){
        servicioPerfilUsuario = Mockito.mock(ServicioPerfilUsuario.class);
        controladorPerfilUsuario = new ControladorPerfilUsuario(servicioPerfilUsuario, servicioPuntuacion);

        session = Mockito.mock(HttpSession.class);
        request = Mockito.mock(HttpServletRequest.class);

        usuario = Mockito.mock(Usuario.class);

        when(usuario.getId()).thenReturn(1L);
        when(usuario.getEmail()).thenReturn("juan@gmail.com");
        when(usuario.getRol()).thenReturn("usuario");

    }

    @Test
    public void queUnUsuarioLogueadoPuedaVerSuPerfil(){

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("usuario")).thenReturn(usuario);

        ModelAndView modelAndView = controladorPerfilUsuario.verPerfil(request);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("perfil-usuario"));

    }

    @Test
    public void queSiNoHayUsuarioEnSesionRedirijaAlLogin(){

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("usuario")).thenReturn(null);

        ModelAndView modelAndView = controladorPerfilUsuario.verPerfil(request);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }


}
