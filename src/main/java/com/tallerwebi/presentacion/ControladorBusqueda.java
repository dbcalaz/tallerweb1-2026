package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorBusqueda {

    @RequestMapping(path = "/buscar-viaje", method = RequestMethod.GET)
    public ModelAndView irABuscarViaje() {
        ModelMap modelo = new ModelMap();
        modelo.put("datosBusqueda", new DatosBusqueda());
        return new ModelAndView("buscarViajes", modelo);
    }

    @RequestMapping(path = "/procesar-busqueda", method = RequestMethod.POST)
    public ModelAndView procesarBusqueda(@ModelAttribute("datosBusqueda") DatosBusqueda datosBusqueda) {
        ModelMap modelo = new ModelMap();

        if (datosBusqueda.getOrigen() == null || datosBusqueda.getOrigen().trim().isEmpty() ||
                datosBusqueda.getDestino() == null || datosBusqueda.getDestino().trim().isEmpty()) {
            modelo.put("error", "Debe ingresar obligatoriamente Origen y Destino");
            return new ModelAndView("buscarViajes", modelo);
        }

        List<ViajeDisponible> viajesSimulados = new ArrayList<>();
        viajesSimulados.add(new ViajeDisponible(datosBusqueda.getOrigen(), datosBusqueda.getDestino(), "10:30", 4500.0, 4));
        viajesSimulados.add(new ViajeDisponible(datosBusqueda.getOrigen(), datosBusqueda.getDestino(), "14:00", 3800.0, 2));
        viajesSimulados.add(new ViajeDisponible(datosBusqueda.getOrigen(), datosBusqueda.getDestino(), "18:30", 5200.0, 6));

        modelo.put("viajes", viajesSimulados);
        modelo.put("origen", datosBusqueda.getOrigen());
        modelo.put("destino", datosBusqueda.getDestino());

        return new ModelAndView("listadoViajes", modelo);
    }

    @RequestMapping(path = "/seleccionar-asiento", method = RequestMethod.GET)
    public ModelAndView irASeleccionarAsiento() {
        return new ModelAndView("seleccionarAsiento");
    }
}