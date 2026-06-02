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
    private String patente;
    private String marca;
    private String modelo;


    public DatosCombi(Integer numeroAsientos, TipoDeCombi tipoCombi, String transmision, String patente, String marca, String modelo) {
    }

    public DatosCombi() {

    }
}
