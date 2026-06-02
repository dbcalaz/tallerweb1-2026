package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import com.tallerwebi.dominio.excepcion.CantidadDeAsientosInvalidaException;
import com.tallerwebi.dominio.excepcion.TipoDeCombiInvalidaException;
import com.tallerwebi.dominio.excepcion.TipoDeTransmisionInvalidaException;
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
    public Combi crearCombi(Integer cantidadAsientos, TipoDeCombi tipoDeCombi, String transmision, String patente,String  marca,String modelo) {

        if(cantidadAsientos<10 || cantidadAsientos>20){
            throw new CantidadDeAsientosInvalidaException() ;
        }
        if (!("MANUAL".equals(transmision) || "AUTOMATICA".equals(transmision))) {
            throw new TipoDeTransmisionInvalidaException();
        }
        if(!(tipoDeCombi == TipoDeCombi.ESTANDAR || tipoDeCombi == TipoDeCombi.TURISTICA)){
            throw new TipoDeCombiInvalidaException();
        }
        if (this.repositorioCombi.buscarPorPatente(patente)!=null) {
            throw new CombiExistenteException();
        }

        Combi combi = new Combi();
        combi.setTipoDeCombi(tipoDeCombi);
        combi.setCantidadDeAsientos(cantidadAsientos);
        combi.setTipoDeTransmision(transmision);
        combi.setPatente(patente);
        combi.setMarca(marca);
        combi.setModelo(modelo);
        this.repositorioCombi.guardar(combi);

return combi;
    }
}
