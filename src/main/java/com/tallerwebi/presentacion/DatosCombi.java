package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.TipoDeCombi;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DatosCombi {
    public Integer kilometros;
    private Integer cantidadDeAsientos;
    private String tipoDeTransmision;
    private TipoDeCombi tipoDeCombi;
    private String patente;
    private String marca;
    private String modelo;


    public DatosCombi(Integer numeroAsientos, TipoDeCombi tipoCombi, String transmision, String patente, String marca, String modelo,Integer kilometros) {
    this.cantidadDeAsientos = numeroAsientos;
    this.tipoDeCombi = tipoCombi;
    this.tipoDeTransmision = transmision;
    this.patente = patente;
    this.marca = marca;
    this.modelo = modelo;
    this.kilometros = kilometros;
    }

    public DatosCombi() {

    }
}
