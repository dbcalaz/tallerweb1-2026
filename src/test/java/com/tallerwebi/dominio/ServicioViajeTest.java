package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class ServicioViajeTest {

  private ServicioViajeImpl servicioViajeImpl;
  private RepositorioViaje repositorioViajeMock;

  @BeforeEach
  public void init() {
    repositorioViajeMock = mock(RepositorioViaje.class);
    servicioViajeImpl = new ServicioViajeImpl(repositorioViajeMock);
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