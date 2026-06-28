package com.tallerwebi.dominio;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
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
    @Enumerated(EnumType.STRING)
    private EstadoReporteFalla estadoReporte;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaCreacionReporte;
    private LocalDate fechaResueltoReporte;

}
