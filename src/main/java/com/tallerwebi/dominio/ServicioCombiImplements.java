package com.tallerwebi.dominio;


import com.tallerwebi.presentacion.DatosCombi;
import com.tallerwebi.dominio.excepcion.CombiExistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import com.tallerwebi.dominio.excepcion.CantidadDeAsientosInvalidaException;
import com.tallerwebi.dominio.excepcion.TipoDeCombiInvalidaException;
import com.tallerwebi.dominio.excepcion.TipoDeTransmisionInvalidaException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioCombiImplements implements ServicioCombi {
    private RepositorioCombi repositorioCombi;

    @Autowired
    public ServicioCombiImplements(RepositorioCombi repositorioCombi) {
        this.repositorioCombi= repositorioCombi;
    }





    @Override
    public Combi crearCombi(DatosCombi datosCombi) throws BondiWayException {

        if(datosCombi.getCantidadAsientos()<10 || datosCombi.getCantidadAsientos()>20){
                throw new CantidadDeAsientosInvalidaException() ;
        }
        if (!("MANUAL".equals(datosCombi.getTransmision()) || "AUTOMATICA".equals(datosCombi.getTransmision()))) {
            throw new TipoDeTransmisionInvalidaException();
        }
        if(!(datosCombi.getTipoDeCombi()  == TipoDeCombi.ESTANDAR || datosCombi.getTipoDeCombi() == TipoDeCombi.TURISTICA)){
            throw new TipoDeCombiInvalidaException();
        }
        if (this.repositorioCombi.buscarPorPatente(datosCombi.getPatente())!=null) {
            throw new CombiExistenteException(datosCombi.getPatente());
        }

        Combi combi = new Combi();
        combi.setTipoDeCombi(datosCombi.getTipoDeCombi());
        combi.setCantidadDeAsientos(datosCombi.getCantidadAsientos());
        combi.setTipoDeTransmision(datosCombi.getTransmision());
        combi.setPatente(datosCombi.getPatente());
        combi.setMarca(datosCombi.getMarca());
        combi.setModelo(datosCombi.getModelo());
        this.repositorioCombi.guardar(combi);

return combi;
    }

    /*@Override
    public List<Combi> obtenerFlota() {
        return repositorioCombi.obtenerTodasLasCombis();
    }*/
}
