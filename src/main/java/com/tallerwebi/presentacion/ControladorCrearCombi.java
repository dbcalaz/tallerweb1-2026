package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Combi;
import com.tallerwebi.dominio.ServicioCombi;
import com.tallerwebi.dominio.ServicioCombiImplements;
import com.tallerwebi.dominio.TipoDeCombi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorCrearCombi {


    private ServicioCombi servicioCombi = new ServicioCombiImplements();
    @Autowired
    public ControladorCrearCombi(ServicioCombi servicioCombi) {
        this.servicioCombi = servicioCombi;
    }

    public ControladorCrearCombi() {

    }


    @RequestMapping("/crear-combi")
    public ModelAndView crearCombi() {
        ModelMap modelo = new ModelMap();
        modelo.put("combi", new Combi());
        modelo.put("tipo de combi", TipoDeCombi.values() );
        return new ModelAndView("crear-combi",modelo);

    }

    @RequestMapping(value = "/crear-combi", method = RequestMethod.POST)
    public ModelAndView crearCombi(@ModelAttribute("combi") Combi combi){
        ModelMap modelo = new ModelMap();

        if(combi.getTipoDeCombi()==null  ){
            modelo.put("combi", combi);
            modelo.put("error","La combi debe tener elegida el tipo de combi");

            return new ModelAndView("crear-combi",modelo);
        }
        if(combi.getTipoDeTransmision().isEmpty() ){
            modelo.put("combi", combi);
            modelo.put("error","La combi debe tener elegida el tipo de transmision");

            return new ModelAndView("crear-combi",modelo);
        }
        if( combi.getCantidadDeAsientos()==0){
            modelo.put("combi", combi);
            modelo.put("error","La cantidad de asientos elegidos no es posible");

            return new ModelAndView("crear-combi",modelo);
        }

        modelo.put("combi", combi);
        return new ModelAndView("combi-registrada", modelo);
    }
}
