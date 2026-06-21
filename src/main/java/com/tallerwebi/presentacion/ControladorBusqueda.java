package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EstadoReserva;
import com.tallerwebi.dominio.Reserva;
import com.tallerwebi.dominio.ServicioViaje;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.Viaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorBusqueda {

    private final ServicioViaje servicioViaje;

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

        if (esBusquedaInvalida(datosBusqueda)) {
            modelo.put("error", "Debe ingresar obligatoriamente Origen, Destino, Fecha y cantidad de Pasajeros");
            return new ModelAndView("buscarViajes", modelo);
        }

        List<Viaje> viajesEncontrados = servicioViaje.buscarViajes(datosBusqueda);

        modelo.put("viajes", viajesEncontrados);
        modelo.put("origen", datosBusqueda.getOrigen());
        modelo.put("destino", datosBusqueda.getDestino());
        modelo.put("pasajeros", datosBusqueda.getPasajeros());
        modelo.put("sinResultados", viajesEncontrados.isEmpty());

        return new ModelAndView("listadoViajes", modelo);
    }

    @RequestMapping(path = "/solicitar-espera", method = RequestMethod.POST)
    public ModelAndView solicitarViajeEnEspera(@RequestParam("origen") String origen, @RequestParam("destino") String destino) {
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
        modelo.put("asientosOcupados", servicioViaje.obtenerAsientosOcupados(idViaje));
        return new ModelAndView("seleccionarAsiento", modelo);
    }

    @RequestMapping(path = "/confirmar-asiento", method = RequestMethod.POST)
    public ModelAndView confirmarAsiento(@RequestParam("idViaje") Long idViaje,
                                         @RequestParam(value = "pasajeros", defaultValue = "1") Integer pasajeros,
                                         @RequestParam(value = "asientosSeleccionados", required = false) String asientosSeleccionados,
                                         HttpServletRequest request) {

        Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuario");
        if (usuarioLogueado == null) {
            return new ModelAndView("redirect:/login");
        }

        try {
            Viaje viajeConfirmado = servicioViaje.buscarPorId(idViaje);
            List<Reserva> misReservas = obtenerReservasDeSesion(request);

            registrarNuevasReservas(pasajeros, asientosSeleccionados, usuarioLogueado, viajeConfirmado, misReservas);

            request.getSession().setAttribute("misReservas", misReservas);
            return new ModelAndView("redirect:/viajeEnCurso");

        } catch (Exception e) {
            ModelMap modelo = new ModelMap();
            modelo.put("error", "Ocurrió un error: " + e.getMessage());
            modelo.put("idViaje", idViaje);
            return new ModelAndView("seleccionarAsiento", modelo);
        }
    }

    @RequestMapping(path = "/viajeEnCurso", method = RequestMethod.GET)
    public ModelAndView verMisViajes(HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        List<Reserva> misReservas = obtenerReservasDeSesion(request);

        modelo.put("misReservas", misReservas);
        modelo.put("sinViajes", misReservas.isEmpty());

        return new ModelAndView("viajeEnCurso", modelo);
    }

    @RequestMapping(path = "/cancelar-viaje", method = RequestMethod.POST)
    public ModelAndView cancelarViaje(@RequestParam("idViaje") Long idViaje, HttpServletRequest request) {
        List<Reserva> misReservas = obtenerReservasDeSesion(request);

        Reserva reservaACancelar = misReservas.stream()
                .filter(reserva -> reserva.getViaje().getId().equals(idViaje))
                .findFirst()
                .orElse(null);

        if (reservaACancelar != null) {
            misReservas.remove(reservaACancelar);
            servicioViaje.liberarAsiento(idViaje);

            if (reservaACancelar.getId() != null) {
                servicioViaje.eliminarReserva(reservaACancelar.getId());
            }

            request.getSession().setAttribute("misReservas", misReservas);
        }

        return new ModelAndView("redirect:/viajeEnCurso");
    }

    @RequestMapping(path = "/inicio-exito", method = RequestMethod.GET)
    public ModelAndView volverAlInicioConExito() {
        ModelMap modelo = new ModelMap();
        modelo.put("mensaje", "¡Tu viaje ha sido confirmado exitosamente!");
        return new ModelAndView("home", modelo);
    }

    private boolean esBusquedaInvalida(DatosBusqueda datos) {
        return datos.getOrigen() == null || datos.getOrigen().trim().isEmpty() ||
                datos.getDestino() == null || datos.getDestino().trim().isEmpty() ||
                datos.getFecha() == null || datos.getFecha().trim().isEmpty() ||
                datos.getPasajeros() == null;
    }

    @SuppressWarnings("unchecked")
    private List<Reserva> obtenerReservasDeSesion(HttpServletRequest request) {
        List<Reserva> reservas = (List<Reserva>) request.getSession().getAttribute("misReservas");
        return (reservas != null) ? reservas : new ArrayList<>();
    }

    private void registrarNuevasReservas(Integer pasajeros, String asientosSeleccionados, Usuario usuario, Viaje viaje, List<Reserva> misReservas) {
        String[] asientosArray = (asientosSeleccionados != null && !asientosSeleccionados.isEmpty())
                ? asientosSeleccionados.split(",") : new String[0];

        for (int i = 0; i < pasajeros; i++) {
            servicioViaje.reservarAsiento(viaje.getId(), usuario);

            Reserva nuevaReserva = new Reserva();
            nuevaReserva.setUsuario(usuario);
            nuevaReserva.setViaje(viaje);
            nuevaReserva.setEstadoReserva(EstadoReserva.CONFIRMADA);

            if (i < asientosArray.length) {
                nuevaReserva.setNumeroAsiento(Integer.parseInt(asientosArray[i].trim()));
            }
            servicioViaje.guardarReserva(nuevaReserva);
            misReservas.add(nuevaReserva);
        }
    }
}