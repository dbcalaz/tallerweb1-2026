package com.tallerwebi.dominio;



import com.tallerwebi.presentacion.DatosCombi;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Combi {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoDeCombi tipoDeCombi;
    private Integer cantidadDeAsientos;
    private String tipoDeTransmision;
    private String patente;
    private String marca;
    private String modelo;
    @Enumerated(EnumType.STRING)
    private EstadoDeCombi estadoDeCombi;
    private Integer kilometros;


    public Combi(DatosCombi datos) {
        this.tipoDeCombi = datos.getTipoDeCombi();
        this.cantidadDeAsientos = datos.getCantidadDeAsientos();
        this.tipoDeTransmision = datos.getTipoDeTransmision();
        this.patente = datos.getPatente();
        this.marca = datos.getMarca();
        this.modelo = datos.getModelo();
        this.kilometros = datos.getKilometros();
        this.estadoDeCombi = EstadoDeCombi.DISPONIBLE;
    }

    public Combi() {

    }
}


