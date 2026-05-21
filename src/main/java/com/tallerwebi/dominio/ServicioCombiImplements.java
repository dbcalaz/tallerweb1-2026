package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioCombiImplements implements ServicioCombi {

   //@Autowired
  // private RepositorioCombi repositorioCombi;


    @Override
    public Combi crearCombi(Combi combi) {

       // repositorioCombi.save(combi);
        Combi combie1 = new Combi();
        combie1=combi;
        return combie1;
    }
}
