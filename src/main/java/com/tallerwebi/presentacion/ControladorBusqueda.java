package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.Reserva;
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
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        Integer pasajeros = (datosBusqueda.getPasajeros() != null) ? datosBusqueda.getPasajeros() : 1;

        List<Viaje> viajesUnicos = new ArrayList<>();
        Set<String> viajesVistos = new HashSet<>();

        for (Viaje v : viajesEncontrados) {

            if (v.getAsientosDisponibles() == null || v.getAsientosDisponibles() < pasajeros) {
                continue;
            }

            String origen = v.getOrigen() != null ? v.getOrigen() : "";
            String destino = v.getDestino() != null ? v.getDestino() : "";
            String fecha = v.getFecha() != null ? v.getFecha() : "";
            String horario = v.getHorario() != null ? v.getHorario() : "";

            String claveUnica = origen + "|" + destino + "|" + fecha + "|" + horario;

            if (!viajesVistos.contains(claveUnica)) {
                viajesVistos.add(claveUnica);
                viajesUnicos.add(v);
            }
        }

        modelo.put("viajes", viajesUnicos);
        modelo.put("origen", datosBusqueda.getOrigen());
        modelo.put("destino", datosBusqueda.getDestino());
        modelo.put("pasajeros", pasajeros);
        modelo.put("sinResultados", viajesUnicos.isEmpty());

        return new ModelAndView("listadoViajes", modelo);
    }

    @RequestMapping(path = "/solicitar-espera", method = RequestMethod.POST)
    public ModelAndView solicitarViajeEnEspera(@RequestParam("origen") String origen,
                                               @RequestParam("destino") String destino) {
        ModelMap modelo = new ModelMap();
        modelo.put("mensaje", "¡Solicitud registrada! Quedas a la espera de que un conductor acepte tu ruta de " + origen + " a " + destino);
        return new ModelAndView("home", modelo);
    }

    @RequestMapping(path = "/seleccionar-asiento", method = RequestMethod.GET)
    public ModelAndView irASeleccionarAsiento(@RequestParam("idViaje") Long idViaje,
                                              @RequestParam(value = "pasajeros", defaultValue = "1") Integer pasajeros) {
        ModelMap modelo = new ModelMap();
        modelo.put("idViaje", idViaje);
        modelo.put("pasajeros", pasajeros);
        return new ModelAndView("seleccionarAsiento", modelo);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(path = "/confirmar-asiento", method = RequestMethod.POST)
    public ModelAndView confirmarAsiento(@RequestParam("idViaje") Long idViaje,
                                         @RequestParam(value = "pasajeros", defaultValue = "1") Integer pasajeros,
                                         @RequestParam(value = "asientosSeleccionados", required = false) String asientosSeleccionados,
                                         HttpServletRequest request) {
        ModelMap modelo = new ModelMap();

        // Usuario REAL de sesion
        Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuario");
        if (usuarioLogueado == null) {
            return new ModelAndView("redirect:/login");
        }


        try {
//            Usuario usuarioTemporal = new Usuario();
//            usuarioTemporal.setId(1L);

            for(int i = 0; i < pasajeros; i++) {
                //servicioViaje.reservarAsiento(idViaje, usuarioTemporal);
                //servicioViaje.reservarAsiento(idViaje, usuarioLogueado);
                servicioViaje.crearReserva(idViaje, usuarioLogueado, asientosSeleccionados);
            }

            modelo.put("mensaje", "¡Asiento(s) confirmado(s) con éxito!");
            return new ModelAndView("redirect:/perfilUsuario");

            //Viaje viajeConfirmado = servicioViaje.buscarPorId(idViaje);

//            if (viajeConfirmado == null) {
//                modelo.put("error", "Hubo un problema. No se encontró el viaje.");
//                modelo.put("idViaje", idViaje);
//                return new ModelAndView("seleccionarAsiento", modelo);
//            }
//
//            Double precioTotal = viajeConfirmado.getPrecio() * pasajeros;

//            List<Reserva> misReservas = (List<Reserva>) request.getSession().getAttribute("misReservas");
//            if(misReservas == null) {
//                misReservas = new ArrayList<>();
//            }
//
//            Reserva nuevaReserva = new Reserva(viajeConfirmado, asientosSeleccionados != null ? asientosSeleccionados : "No especificados", precioTotal);
//            misReservas.add(nuevaReserva);
//
//            request.getSession().setAttribute("misReservas", misReservas);
//
//            modelo.put("misReservas", misReservas);
//            modelo.put("mensaje", "¡Asiento(s) confirmado(s) con éxito!");
//
//            return new ModelAndView("viajeEnCurso", modelo);

        } catch (Exception e) {
            modelo.put("error", "Ocurrió un error: " + e.getMessage());
            modelo.put("idViaje", idViaje);
            return new ModelAndView("seleccionarAsiento", modelo);
        }
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(path = "/viajeEnCurso", method = RequestMethod.GET)
    public ModelAndView verMisViajes(HttpServletRequest request) {
        ModelMap modelo = new ModelMap();

        List<Reserva> misReservas = (List<Reserva>) request.getSession().getAttribute("misReservas");

        if(misReservas != null && !misReservas.isEmpty()) {
            modelo.put("misReservas", misReservas);
        } else {
            modelo.put("sinViajes", true);
        }

        return new ModelAndView("viajeEnCurso", modelo);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(path = "/cancelar-viaje", method = RequestMethod.POST)
    public ModelAndView cancelarViaje(@RequestParam("idViaje") Long idViaje, HttpServletRequest request) {
        ModelMap modelo = new ModelMap();

        List<Reserva> misReservas = (List<Reserva>) request.getSession().getAttribute("misReservas");

        if (misReservas != null) {
            misReservas.removeIf(reserva -> reserva.getViaje().getId().equals(idViaje));
            request.getSession().setAttribute("misReservas", misReservas);
        }

        modelo.put("mensaje", "El viaje ha sido cancelado correctamente.");
        return new ModelAndView("home", modelo);
    }

    @RequestMapping(path = "/inicio-exito", method = RequestMethod.GET)
    public ModelAndView volverAlInicioConExito() {
        ModelMap modelo = new ModelMap();
        modelo.put("mensaje", "¡Tu viaje ha sido confirmado exitosamente!");
        return new ModelAndView("home", modelo);
    }
}