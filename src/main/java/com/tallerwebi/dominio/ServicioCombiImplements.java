package com.tallerwebi.dominio;


import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.presentacion.DatosCombi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

        if(datosCombi.getCantidadDeAsientos()<10 || datosCombi.getCantidadDeAsientos()>20){
                throw new CantidadDeAsientosInvalidaException() ;
        }
        if (!("MANUAL".equals(datosCombi.getTipoDeTransmision()) || "AUTOMATICA".equals(datosCombi.getTipoDeTransmision()))) {
            throw new TipoDeTransmisionInvalidaException();
        }
        if(!(datosCombi.getTipoDeCombi()  == TipoDeCombi.ESTANDAR || datosCombi.getTipoDeCombi() == TipoDeCombi.TURISTICA)){
            throw new TipoDeCombiInvalidaException();
        }
        if(datosCombi.getKilometros()<0){
            throw new CantidadDeKilometrosException();
        }
        if (this.repositorioCombi.buscarPorPatente(datosCombi.getPatente())!=null) {
            throw new CombiExistenteException(datosCombi.getPatente());
        }
        Combi combi = new Combi(datosCombi);
        this.repositorioCombi.guardar(combi);

        return combi;
    }


}
