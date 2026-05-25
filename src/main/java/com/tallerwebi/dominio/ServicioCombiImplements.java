package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioCombiImplements implements ServicioCombi {



    @Override
    public Combi crearCombi(Integer cantidadAsientos, TipoDeCombi tipoDeCombi, String transmision) {

        if(cantidadAsientos<10 || cantidadAsientos>20){
            throw new CantidadDeAsientosInvalidaException() ;
        }
        if (!("MANUAL".equals(transmision) || "AUTOMATICA".equals(transmision))) {
            throw new TipoDeTransmisionInvalidaException();
        }
        if(!(tipoDeCombi == TipoDeCombi.ESTANDAR || tipoDeCombi == TipoDeCombi.TURISTICA)){
            throw new TipoDeCombiInvalidaException();
        }

        return new Combi(cantidadAsientos,tipoDeCombi,transmision);
    }
}
