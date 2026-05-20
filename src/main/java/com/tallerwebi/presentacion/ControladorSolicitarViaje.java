package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioViaje;
import com.tallerwebi.dominio.Viaje;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorSolicitarViaje {

    ServicioViaje servicioViaje;

    public ControladorSolicitarViaje() {
    }

    public ControladorSolicitarViaje(ServicioViaje servicioViaje) {
        this.servicioViaje = servicioViaje;
    }

    @RequestMapping("/solicitarViaje")
    public ModelAndView solicitarViaje() {
        DatosViaje datosViaje = new DatosViaje();
        ModelMap model = new ModelMap();
        model.put("datosViaje", datosViaje);
        return new ModelAndView("solicitarViaje", model);
    }

    @RequestMapping(path = "/viajeEnCurso", method = RequestMethod.POST)
    public ModelAndView solicitarViaje(@ModelAttribute("datosViaje") DatosViaje datosViaje) {
        ModelMap modelo = new ModelMap();
        if (datosViaje.getDestino().isEmpty() && datosViaje.getOrigen().isEmpty()) {
            modelo.put("error", "Los campos son obligatorios");
            modelo.put("datosViaje", datosViaje);
            return new ModelAndView("solicitarViaje", modelo);
        }
        if (datosViaje.getOrigen().isEmpty()) {
            modelo.put("error", "El punto de origen es obligatorio");
            modelo.put("datosViaje", datosViaje);
            return new ModelAndView("solicitarViaje", modelo);
        }
        if (datosViaje.getDestino().isEmpty()) {
            modelo.put("error", "El punto de destino es obligatorio");
            modelo.put("datosViaje", datosViaje);
            return new ModelAndView("solicitarViaje", modelo);
        }
        modelo.put("datosViaje", datosViaje);
        return new ModelAndView("viajeEnCurso", modelo);
    }

    @RequestMapping("/viajeEnCurso")
    public ModelAndView viajeEnCurso() {
        ModelMap modelo = new ModelMap();
        modelo.put("datosViaje", new DatosViaje());
        return new ModelAndView("viajeEnCurso", modelo);
    }

    @RequestMapping("/cancelarViaje")
    public ModelAndView cancelarViaje() {
        ModelMap modelo = new ModelMap();
        modelo.put("mensaje", "El viaje fue cancelado correctamente");
        return new ModelAndView("home", modelo);
    }

    @RequestMapping(value = "/confirmarViaje", method = RequestMethod.POST)
    public ModelAndView confirmarViaje(DatosViaje datosViaje) {
        ModelMap modelo = new ModelMap();

        if (datosViaje.getDestino().isEmpty() && datosViaje.getOrigen().isEmpty()) {
            modelo.put("error", "Error al asignar el viaje");
        }

        Viaje viaje = new Viaje();
        viaje.setOrigen(datosViaje.getOrigen());
        viaje.setDestino(datosViaje.getDestino());

        modelo.put("mensaje", "El viaje fue asignado correctamente");
        return new ModelAndView("home", modelo);

        /*try {
            if (servicioViaje != null) {
                servicioViaje.confirmarViaje(viaje);
                modelo.put("mensaje", "El viaje fue asignado correctamente");
                return new ModelAndView("home", modelo);
            }
            modelo.put("mensaje", "Probando el flujo a ver que onda");
            return new ModelAndView("home", modelo);

        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return new ModelAndView("home", modelo);
        }*/
    }
}
