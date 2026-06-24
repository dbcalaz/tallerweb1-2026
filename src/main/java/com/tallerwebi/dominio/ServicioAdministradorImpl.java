package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DatosFiltro;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioAdministradorImpl implements ServicioAdministrador {

    RepositorioAdministrador repositorioAdministrador;
    RepositorioCombi repositorioCombi;

    public ServicioAdministradorImpl(RepositorioAdministrador repositorioAdministrador, RepositorioCombi repositorioCombi) {
        this.repositorioAdministrador = repositorioAdministrador;
        this.repositorioCombi = repositorioCombi;
    }

    /*Combis*/
    @Override
    public List<ReporteFalla> obtenerFallasDeCombis() {
        return repositorioAdministrador.getFallas();
    }

    @Override
    public void asignarNuevaCombiAConductor(Long idReporte, Long idCombi) {
        repositorioAdministrador.updateCombiConductor(idReporte,idCombi);
    }

    @Override
    public List<Combi> obtenerCombisFiltradas(DatosFiltro datosFiltro){
        return repositorioAdministrador.getCombisFiltradas(datosFiltro);
}

    @Override
    public List<Combi> obtenerCombisDisponibles() {
        return repositorioAdministrador.getCombisDisponibles();
    }

    @Override
    public void actualizarEstadoCombi(Long idCombi, EstadoDeCombi estado) {
        Combi combiExiste= repositorioCombi.buscarPorId(idCombi);
        if(combiExiste!=null){
            combiExiste.setEstadoDeCombi(estado);
            repositorioAdministrador.actualizarCombi(combiExiste);
        }
    }

    public Long obtenerCantidadCombis() {
        return repositorioAdministrador.getCantidadDeCombis();
    }

    /*Conductor*/
    @Override
    public List<Conductor> obtenerConductores(Boolean estadoCuenta, String estado) {
        return repositorioAdministrador.getConductores(estadoCuenta, estado);
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
