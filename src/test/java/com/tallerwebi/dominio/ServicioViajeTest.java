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

    // AHORA SETEAMOS ENTIDADES PARADA EN LUGAR DE TEXTO
    Parada origen = new Parada("Haedo", -34.64, -58.59);
    Parada destino = new Parada("Ramos Mejía", -34.64, -58.56);
    viaje.setOrigen(origen);
    viaje.setDestino(destino);

    servicioViajeImpl.confirmarViaje(viaje);
    verify(repositorioViajeMock, times(1)).guardarViaje(viaje);
  }

  @Test
  public void queBusqueViajesLlamandoAlRepositorioConLosDatosCorrectos() {
    // AHORA PASAMOS LOS IDs NUMÉRICOS (Long)
    DatosBusqueda datos = new DatosBusqueda(1L, 2L, "2026-06-15", 2);
    List<Viaje> viajesSimulados = new ArrayList<>();
    viajesSimulados.add(new Viaje());

    // ACTUALIZAMOS EL MOCK PARA QUE ESPERE LOS IDs
    when(repositorioViajeMock.buscarViajes(1L, 2L, "2026-06-15", 2))
            .thenReturn(viajesSimulados);

    List<Viaje> resultado = servicioViajeImpl.buscarViajes(datos);

    assertThat(resultado, hasSize(1));
    verify(repositorioViajeMock, times(1)).buscarViajes(1L, 2L, "2026-06-15", 2);
  }
}