package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Viaje;

public class ViajeDisponible {
    private Long id;
    private String origen;
    private String destino;
    private String horario;
    private Double precio;
    private Integer asientosDisponibles;
    private String tipoDeViaje;
    private Double calificacionConductor;
    private String nombreConductor;
    private String marcaModeloCombi;
    private String fecha;

    // Paradas IDs para poder persistir la reserva correctamente con sus paradas origen/destino
    private Long idParadaOrigen;
    private Long idParadaDestino;

    public ViajeDisponible(Long id, String origen, String destino, String horario, Double precio,
                           Integer asientos, String tipoDeViaje, Double calificacionConductor,
                           String nombreConductor, String marcaModeloCombi, String fecha,
                           Long idParadaOrigen, Long idParadaDestino) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.horario = horario;
        this.precio = precio;
        this.asientosDisponibles = asientos;
        this.tipoDeViaje = tipoDeViaje;
        this.calificacionConductor = calificacionConductor;
        this.nombreConductor = nombreConductor;
        this.marcaModeloCombi = marcaModeloCombi;
        this.fecha = fecha;
        this.idParadaOrigen = idParadaOrigen;
        this.idParadaDestino = idParadaDestino;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }
    public String getHorario() { return horario; }
    public Double getPrecio() { return precio; }
    public Integer getAsientosDisponibles() { return asientosDisponibles; }
    public String getTipoDeViaje() { return tipoDeViaje; }
    public Double getCalificacionConductor() { return calificacionConductor; }
    public String getNombreConductor() { return nombreConductor; }
    public String getMarcaModeloCombi() { return marcaModeloCombi; }
    public String getFecha() { return fecha; }
    public Long getIdParadaOrigen() { return idParadaOrigen; }
    public Long getIdParadaDestino() { return idParadaDestino; }
}