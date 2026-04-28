package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorRegistro {


    public ModelAndView registrar(DatosRegistroDTO datosRegistro) {
        ModelMap model = new ModelMap();
        if (datosRegistro.getMail().isEmpty()){
            model.put("error", "El email es obligatorio");
            return new ModelAndView("registro", model);
        }
        if (datosRegistro.getPassword().isEmpty()){
            model.put("error", "El password es obligatorio");
            return new ModelAndView("registro", model);
        }
        if (!datosRegistro.getRepitePassword().equals(datosRegistro.getPassword())){
            model.put("error", "El password debe coincidir");
            return new ModelAndView("registro", model);
        }
        model.put("mensaje", "el registro fue exitoso");
        return new ModelAndView("login", model);
    }
}
