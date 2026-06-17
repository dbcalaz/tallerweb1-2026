package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.ServicioAdministrador;
import org.hibernate.criterion.Restrictions;
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
    public List<Combi> obtenerCombis(String parametro){
        return repositorioAdministrador.obtenerCombisFiltradas(parametro);



}

    @Override
    public void actualizarEstadoCombi(Long idCombi, EstadoDeCombi estado) {
        Combi combiExiste= repositorioCombi.buscarPorId(idCombi);
        if(combiExiste!=null){
            combiExiste.setEstadoDeCombi(estado);
            repositorioAdministrador.actualizarCombi(combiExiste);
        }

    }


}
