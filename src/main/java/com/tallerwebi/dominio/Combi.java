package com.tallerwebi.dominio;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class Combi {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private TipoDeCombi tipoDeCombi;
    private Integer cantidadDeAsientos;
    private String tipoDeTransmision;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoDeCombi getTipoDeCombi() {
        return tipoDeCombi;
    }

    public void setTipoDeCombi(TipoDeCombi tipoDeCombi) {
        this.tipoDeCombi = tipoDeCombi;
    }

    public String getTipoDeTransmision() {
        return tipoDeTransmision;
    }

    public void setTipoDeTransmision(String tipoDeTransmision) {
        this.tipoDeTransmision = tipoDeTransmision;
    }

    public Integer getCantidadDeAsientos() {
        return cantidadDeAsientos;
    }

    public void setCantidadDeAsientos(Integer cantidadDeAsientos) {
        this.cantidadDeAsientos = cantidadDeAsientos;
    }
}
