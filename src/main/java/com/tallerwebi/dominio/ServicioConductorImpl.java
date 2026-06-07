package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ConductorExistente;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioConductorImpl implements ServicioConductor {

    private RepositorioConductor repositorioConductor;

    public ServicioConductorImpl(RepositorioConductor repositorioConductor){
        this.repositorioConductor = repositorioConductor;
    }

    @Override
    public Conductor consultarConductor(String email, String password) {
        return repositorioConductor.buscarConductor(email,password);
    }

    @Override
    public void registrarConductor(Conductor conductor) throws ConductorExistente {
        Conductor conductorExistente = repositorioConductor.buscarConductor(conductor.getEmail(), conductor.getPassword());
        if (conductorExistente != null) {
            throw new ConductorExistente();
        }
        repositorioConductor.guardarConductor(conductor);
    }

    //Todos los viajes con todos los estados del viajes
    @Override
    public List<Viaje> obtenerViajesDelConductor(Long idConductor){
        if(idConductor == null){
            throw new IllegalArgumentException("El id del conductor es obligatorio");
        }
        return repositorioConductor.obtenerViajesPorConductor(idConductor);
    }

    //Todos los viajes con estado = PENDIENTE
    @Override
    public List<Viaje> obtenerViajesPendientesDelConductor(Long idConductor){
        if(idConductor == null){
            throw new IllegalArgumentException("El id del conductor es obligatorio");
        }
        return repositorioConductor.obtenerViajesPendientesPorConductor(idConductor);
    }

    //Todos los viajes con estado = FINALIZADO
    @Override
    public List<Viaje> obtenerViajesFinalizadosDelConductor(Long idConductor){
        if(idConductor == null){
            throw new IllegalArgumentException("El id del conductor es obligatorio");
        }
        return repositorioConductor.obtenerViajesFinalizadosPorConductor(idConductor);
    }

    @Override
    public Combi buscarCombiActivePorIdConductor(Long id) {
        return repositorioConductor.obtenerCombiActivaPorIdConductor(id);
    }


}
