package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioAdministradorImpl implements ServicioAdministrador {

    RepositorioAdministrador repositorioAdministrador;

    public ServicioAdministradorImpl(RepositorioAdministrador repositorioAdministrador) {
        this.repositorioAdministrador = repositorioAdministrador;
    }

    @Override
    public List<ReporteFalla> obtenerFallasDeCombis() {
        return repositorioAdministrador.getFallas();
    }

    @Override
    public List<Conductor> obtenerConductores() {
        return repositorioAdministrador.getConductores();
    }

    @Override
    public void asignarNuevaCombiAConductor(Long idReporte, Long idCombi) {
        repositorioAdministrador.updateCombiConductor(idReporte,idCombi);
    }

    @Override
    public List<Combi> obtenerCombis() {
        return repositorioAdministrador.getCombis();
    }

    @Override
    public Long obtenerCantidadCombis() {
        return repositorioAdministrador.getCantidadDeCombis();
    }

    @Override
    public List<Conductor> obtenerConductoresPendientes() {
        return repositorioAdministrador.getConductoresPendientes();
    }

    @Override
    public Long obtenerCantidadDeConductoresPendientes() {
        return repositorioAdministrador.getCantidadDeConductoresPendientes();
    }

    @Override
    public void habilitarConductor(Long idConductor, Long idCombi) {

        Conductor conductor = repositorioAdministrador.buscarConductorPorId(idConductor);

        if(conductor == null){
            throw new RuntimeException("No existe el conductor seleccionado");
        }

        Combi combi = repositorioAdministrador.buscarCombiPorId(idCombi);

        if(combi == null){
            throw new RuntimeException("No existe la combi seleccionada");
        }

        if(conductor.isSuspendido()){
            throw new RuntimeException("El conductor está suspendido");
        }

        if(conductor.isCuentaHabilitada()){
            throw new RuntimeException("El conductor ya fue habilitado");
        }

        conductor.setCuentaHabilitada(true);
        repositorioAdministrador.actualizarConductor(conductor);

        AsignacionCombiConductor asignacion = new AsignacionCombiConductor();

        asignacion.setConductor(conductor);
        asignacion.setCombi(combi);
        asignacion.setCombiActiva(true);

        repositorioAdministrador.guardarAsignacion(asignacion);
    }

    @Override
    public void suspenderConductor(Long idConductor) {
        Conductor conductor = repositorioAdministrador.buscarConductorPorId(idConductor);

        if(conductor == null){
            throw new RuntimeException("No existe el conductor seleccionado");
        }

        repositorioAdministrador.suspenderConductor(conductor);
    }

    @Override
    public void reactivarConductor(Long idConductor) {
        Conductor conductor = repositorioAdministrador.buscarConductorPorId(idConductor);

        if(conductor == null){
            throw new RuntimeException("No existe el conductor seleccionado");
        }

        repositorioAdministrador.reactivarConductor(conductor);
    }

}
