package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.CantidadDeAsientosInvalidaException;
import com.tallerwebi.dominio.excepcion.TipoDeCombiInvalidaException;
import com.tallerwebi.dominio.excepcion.TipoDeTransmisionInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorCrearCombi {


    private ServicioCombi servicioCombi;

    @Autowired
    public ControladorCrearCombi(ServicioCombi servicioCombi) {

        this.servicioCombi = servicioCombi;
    }


    @RequestMapping("/crear-combi")
    public ModelAndView crearCombi() {
        ModelMap modelo = new ModelMap();
        modelo.put("combi", new DatosCombi());
        return new ModelAndView("crear-combi", modelo);

    }

    @RequestMapping(value = "/crear-combi", method = RequestMethod.POST)
    public ModelAndView crearCombi(@ModelAttribute("combi") DatosCombi datosCombi) {
        ModelMap modelo = new ModelMap();

        if (datosCombi.getTipoDeCombi() == null || datosCombi.getTipoDeCombi().toString().isEmpty()) {
            modelo.put("error", "La combi debe tener elegida el tipo de combi");

            return new ModelAndView("crear-combi", modelo);
        }
        if ( datosCombi.getTransmision() == null || datosCombi.getTransmision().isEmpty()) {
            modelo.put("error", "La combi debe tener elegida el tipo de transmision");

            return new ModelAndView("crear-combi", modelo);
        }
        if (datosCombi.getCantidadAsientos() == null || datosCombi.getCantidadAsientos().toString().isEmpty()) {

            modelo.put("error", "La cantidad de asientos debe ser elegida");

            return new ModelAndView("crear-combi", modelo);
        }

        try {
            servicioCombi.crearCombi(datosCombi.getCantidadAsientos(), datosCombi.getTipoDeCombi(), datosCombi.getTransmision());
        } catch (CantidadDeAsientosInvalidaException ex) {
            modelo.put("error", "La cantidad de asientos debe estar entre 10 y 20");
            return new ModelAndView("crear-combi", modelo);
        } catch (TipoDeTransmisionInvalidaException e) {
            modelo.put("error", "El tipo de transmision es incorrecta");
            return new ModelAndView("crear-combi", modelo);
        }
        catch (TipoDeCombiInvalidaException ext) {
            modelo.put("error", "El tipo de combi es incorrecta");
            return new ModelAndView("crear-combi", modelo);
        }
            modelo.put("combi", datosCombi);
            modelo.put("mensaje", "La creacion fue exitosa");
            return new ModelAndView("combi-registrada", modelo);

    }
}
