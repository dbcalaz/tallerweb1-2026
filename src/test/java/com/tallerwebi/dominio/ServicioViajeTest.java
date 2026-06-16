package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DatosBusqueda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
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

  @Test
  public void queBusqueViajesLlamandoAlRepositorioConLosDatosCorrectos() {
    DatosBusqueda datos = new DatosBusqueda("San Justo", "Ramos", "2026-06-15", 2);
    List<Viaje> viajesSimulados = new ArrayList<>();
    viajesSimulados.add(new Viaje());

    when(repositorioViajeMock.buscarViajes("San Justo", "Ramos", "2026-06-15", 2))
            .thenReturn(viajesSimulados);

    List<Viaje> resultado = servicioViajeImpl.buscarViajes(datos);

    assertThat(resultado, hasSize(1));
    verify(repositorioViajeMock, times(1)).buscarViajes("San Justo", "Ramos", "2026-06-15", 2);
  }
}