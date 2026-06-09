package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class AsignacionCombiConductor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_conductor")
    private Conductor conductor;

    @ManyToOne
    @JoinColumn(name = "id_combi")
    private Combi combi;

    private Boolean combiActiva;

}
