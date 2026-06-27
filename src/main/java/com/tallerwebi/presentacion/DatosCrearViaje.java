package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.TipoDeViaje;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class DatosCrearViaje {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fecha;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime horario;
    private Double precio;
    private TipoDeViaje tipoDeViaje;
    private Long origenId;
    private Long destinoId;
    private List<Long> paradasIntermedias;

}