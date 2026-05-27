package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioViaje;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.Viaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
public class ControladorBusqueda {

    private ServicioViaje servicioViaje;

    @Autowired
    public ControladorBusqueda(ServicioViaje servicioViaje) {
        this.servicioViaje = servicioViaje;
    }

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

        List<Viaje> viajesEncontrados = servicioViaje.buscarViajes(
                datosBusqueda.getOrigen(),
                datosBusqueda.getDestino(),
                datosBusqueda.getFecha()
        );

        modelo.put("viajes", viajesEncontrados);
        modelo.put("origen", datosBusqueda.getOrigen());
        modelo.put("destino", datosBusqueda.getDestino());

        if (viajesEncontrados.isEmpty()) {
            modelo.put("sinResultados", true);
        }

        return new ModelAndView("listadoViajes", modelo);
    }

    @RequestMapping(path = "/seleccionar-asiento", method = RequestMethod.GET)
    public ModelAndView irASeleccionarAsiento(@RequestParam("idViaje") Long idViaje) {
        ModelMap modelo = new ModelMap();
        modelo.put("idViaje", idViaje);
        return new ModelAndView("seleccionarAsiento", modelo);
    }

    @RequestMapping(path = "/confirmar-asiento", method = RequestMethod.POST)
    public ModelAndView confirmarAsiento(@RequestParam("idViaje") Long idViaje) {
        ModelMap modelo = new ModelMap();

        try {
            Usuario usuarioTemporal = new Usuario();
            usuarioTemporal.setId(1L);
            servicioViaje.reservarAsiento(idViaje, usuarioTemporal);

            Viaje viajeConfirmado = servicioViaje.buscarPorId(idViaje);

            if (viajeConfirmado == null) {
                modelo.put("error", "Hubo un problema. No se encontró el viaje en la base de datos.");
                modelo.put("idViaje", idViaje);
                return new ModelAndView("seleccionarAsiento", modelo);
            }

            modelo.put("datosViaje", viajeConfirmado);
            modelo.put("mensaje", "¡Asiento confirmado con éxito!");
            return new ModelAndView("viajeEnCurso", modelo);

        } catch (Exception e) {
            modelo.put("error", "Ocurrió un error: " + e.getMessage());
            modelo.put("idViaje", idViaje);
            return new ModelAndView("seleccionarAsiento", modelo);
        }
    }
}