package com.tallerwebi.dominio;



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




    public Combi() {}

}
