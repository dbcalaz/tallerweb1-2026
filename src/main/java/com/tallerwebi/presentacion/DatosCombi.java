package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.TipoDeCombi;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DatosCombi {
    private Integer cantidadAsientos;
    private String transmision;
    private TipoDeCombi tipoDeCombi;


    public DatosCombi(Integer cantidadAsientos, String transmision, TipoDeCombi tipoDeCombi) {
        this.cantidadAsientos = cantidadAsientos;
        this.transmision = transmision;
        this.tipoDeCombi = tipoDeCombi;
    }
}
