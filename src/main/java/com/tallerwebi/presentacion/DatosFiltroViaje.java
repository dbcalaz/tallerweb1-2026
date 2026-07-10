package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EstadoDeViaje;
import com.tallerwebi.dominio.TipoDeViaje;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class DatosFiltroViaje {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    private TipoDeViaje tipoDeViaje;
    private EstadoDeViaje estadoDeViaje;
    private Long idConductor;

}