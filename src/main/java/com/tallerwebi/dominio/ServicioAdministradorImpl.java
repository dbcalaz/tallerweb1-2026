package com.tallerwebi.dominio;



import com.tallerwebi.dominio.excepcion.BondiWayException;
import com.tallerwebi.dominio.excepcion.ValorDeDistanciaYKmNoPermitido;
import com.tallerwebi.dominio.excepcion.ValorDeHoraYFechaDestinoIncompleta;
import com.tallerwebi.dominio.excepcion.ValorDeViajeIncompleto;
import com.tallerwebi.presentacion.DatosCrearViaje;
import com.tallerwebi.presentacion.DatosFiltro;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioAdministradorImpl implements ServicioAdministrador {

    RepositorioAdministrador repositorioAdministrador;
    RepositorioCombi repositorioCombi;
    RepositorioViaje repositorioViaje;

    public ServicioAdministradorImpl(RepositorioAdministrador repositorioAdministrador, RepositorioCombi repositorioCombi,RepositorioViaje repositorioViaje) {
        this.repositorioAdministrador = repositorioAdministrador;
        this.repositorioCombi = repositorioCombi;
        this.repositorioViaje= repositorioViaje;
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

    public void guardarViaje(DatosCrearViaje datos) throws BondiWayException {
        if(datos.getValorPorKm()<=0 || datos.getDistancia() <=0 ){
            throw new ValorDeDistanciaYKmNoPermitido();
        }
        if(datos.getOrigen().isEmpty() ||  datos.getDestino().isEmpty()){
            throw new ValorDeViajeIncompleto();
        }
        if(datos.getHorario().isEmpty() || datos.getFecha().isEmpty()){
            throw new ValorDeHoraYFechaDestinoIncompleta();
        }
        Combi combi = repositorioAdministrador.buscarCombiPorId(datos.getIdCombi());
        Conductor conductor = repositorioAdministrador.buscarConductorPorId(datos.getIdConductor());
        List<Parada> paradasElegidas = repositorioViaje.obtenerParadasPorIds(datos.getIdsParadasIntermedias());
        Viaje nuevoViaje = new Viaje(datos,conductor, combi,paradasElegidas);

        repositorioViaje.guardarViaje(nuevoViaje);
    }


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
