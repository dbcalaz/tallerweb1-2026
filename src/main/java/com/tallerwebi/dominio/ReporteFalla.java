package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class ReporteFalla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Conductor conductor;

    @ManyToOne
    private Combi combi;

    private String descripcion;

    private Boolean resuelta;

    private Date fechaCreacionReporte;

    private Date fechaRealizadoReporte;

}
