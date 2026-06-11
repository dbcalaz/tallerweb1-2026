package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ServicioReservaTest {

    private RepositorioUsuario repositorioUsuario;
    private RepositorioReserva repositorioReserva;
    private ServicioPerfilUsuario servicioPerfilUsuario;

    @BeforeEach
    void init(){
        repositorioUsuario = Mockito.mock(RepositorioUsuario.class);
        repositorioReserva = Mockito.mock(RepositorioReserva.class);
        servicioPerfilUsuario = Mockito.mock(ServicioPerfilUsuario.class);

        servicioPerfilUsuario = new ServicioPerfilUsuarioImpl(repositorioUsuario, repositorioReserva);
        Usuario usuario = new Usuario();
        usuario.setId((long) 1L);
    }

    @Test
    public void queSePuedaobtenerElConductorFavorito(){

        Usuario usuario = new Usuario();

        Conductor conductor1 = new Conductor();
        conductor1.setNombre("Juanito");

        Conductor conductor2 = new Conductor();
        conductor2.setNombre("Pedro");

        Viaje viaje1 = new Viaje();
        viaje1.setConductor(conductor1);
        Viaje viaje2 = new Viaje();
        viaje2.setConductor(conductor2);
        Viaje viaje3 = new Viaje();
        viaje3.setConductor(conductor1);
        Viaje viaje4 = new Viaje();
        viaje4.setConductor(conductor1);

        Reserva reserva1 = new Reserva(viaje1,"3",2.500);
        reserva1.setViaje(viaje1);
        Reserva reserva2 = new Reserva(viaje2,"2",5.500);
        reserva2.setViaje(viaje2);
        Reserva reserva3 = new Reserva(viaje3,"5",6.500);
        reserva3.setViaje(viaje3);
        Reserva reserva4 = new Reserva(viaje4,"6",4.500);
        reserva4.setViaje(viaje4);

        reserva1.setUsuario(usuario);
        reserva2.setUsuario(usuario);
        reserva3.setUsuario(usuario);
        reserva4.setUsuario(usuario);

        List<Reserva> reservas = new ArrayList<>();
        reservas.add(reserva1);
        reservas.add(reserva2);
        reservas.add(reserva3);
        reservas.add(reserva4);

        when(repositorioReserva.buscarUltimasReservasPorUsuario(1L)).thenReturn(reservas);

        Conductor favorito = servicioPerfilUsuario.obtenerConductorFavorito(1L);

        assertEquals(conductor1, favorito);

    }
}
