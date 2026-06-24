package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EstadoDeViaje;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DatosCrearViaje {
    private String origen;
    private String destino;
    private String fecha;
    private String horario;
    private Long idCombi;
    private Long idConductor;
    private Double distancia;
    private Double valorPorKm;
    private List<Long> idsParadasIntermedias;



 public DatosCrearViaje(){}


}
