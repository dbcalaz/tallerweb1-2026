package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class ServicioViajeTest {

  private ServicioViajeImpl servicioViajeImpl;
  private RepositorioViaje repositorioViajeMock;
  private RepositorioReserva repositorioReservaMock;

  @BeforeEach
  public void init() {
    repositorioViajeMock = mock(RepositorioViaje.class);
    repositorioReservaMock = mock(RepositorioReserva.class);
    servicioViajeImpl = new ServicioViajeImpl(repositorioViajeMock, repositorioReservaMock);
  }

  @Test
  public void queSePuedaConfirmarCorrectamenteUnViaje() {
    Viaje viaje = new Viaje();
    viaje.setOrigen("haedo");
    viaje.setDestino("ramos");

    servicioViajeImpl.confirmarViaje(viaje);
    verify(repositorioViajeMock, times(1)).guardarViaje(viaje);
  }
}