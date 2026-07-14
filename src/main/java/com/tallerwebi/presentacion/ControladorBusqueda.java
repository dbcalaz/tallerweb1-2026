package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        modelo.put("paradas", servicioViaje.obtenerTodasLasParadas());
        return new ModelAndView("buscarViajes", modelo);
    }

    @RequestMapping(path = "/procesar-busqueda", method = RequestMethod.POST)
    public ModelAndView procesarBusqueda(@ModelAttribute("datosBusqueda") DatosBusqueda datosBusqueda) {
        ModelMap modelo = new ModelMap();

        if (datosBusqueda.getIdOrigen() == null || datosBusqueda.getIdDestino() == null || datosBusqueda.getFecha().isEmpty()) {
            modelo.put("error", "Debe ingresar Origen, Destino, Fecha y Pasajeros");
            modelo.put("paradas", servicioViaje.obtenerTodasLasParadas());
            return new ModelAndView("buscarViajes", modelo);
        }

        List<Viaje> viajesEncontrados = servicioViaje.buscarViajes(datosBusqueda);
        List<ViajeDisponible> viajesCalculados = new ArrayList<>();

        String nombreOrigenBuscado = "";
        String nombreDestinoBuscado = "";

        for (Viaje v : viajesEncontrados) {
            ViajeParada vpOrigen = null;
            ViajeParada vpDestino = null;

            for (ViajeParada vp : v.getParadas()) {
                if (vp.getParada().getId().equals(datosBusqueda.getIdOrigen())) {
                    vpOrigen = vp;
                    nombreOrigenBuscado = vp.getParada().getNombre();
                }
                if (vp.getParada().getId().equals(datosBusqueda.getIdDestino())) {
                    vpDestino = vp;
                    nombreDestinoBuscado = vp.getParada().getNombre();
                }
            }

            if (vpOrigen != null && vpDestino != null) {
                // Se invoca directamente usando el servicio inyectado
                LocalTime horarioCalculado = servicioViaje.calcularHorarioParada(v, vpOrigen);
                String horarioFormateado = horarioCalculado.format(DateTimeFormatter.ofPattern("HH:mm"));

                // Se invoca directamente usando el servicio inyectado
                double precioCalculado = servicioViaje.calcularPrecioPorTramo(v, datosBusqueda.getIdOrigen(), datosBusqueda.getIdDestino());

                String nombreConductor = v.getConductor() != null ?
                        (v.getConductor().getApellido() != null ? v.getConductor().getNombre() + " " + v.getConductor().getApellido() : v.getConductor().getNombre())
                        : "Sin Conductor";

                double calificacion = v.getConductor() != null ? v.getConductor().getCalificacion() : 5.0;
                String combiDetalle = v.getCombi() != null ? (v.getCombi().getMarca() + " " + v.getCombi().getModelo()) : "Unidad estándar";

                ViajeDisponible vd = new ViajeDisponible(
                        v.getId(),
                        vpOrigen.getParada().getNombre(),
                        vpDestino.getParada().getNombre(),
                        horarioFormateado,
                        precioCalculado,
                        v.getAsientosDisponibles(),
                        v.getTipoDeViaje().name(),
                        calificacion,
                        nombreConductor,
                        combiDetalle,
                        v.getFecha().toString(),
                        vpOrigen.getId(),
                        vpDestino.getId()
                );
                viajesCalculados.add(vd);
            }
        }

        modelo.put("viajes", viajesCalculados);
        modelo.put("pasajeros", datosBusqueda.getPasajeros());
        modelo.put("origen", nombreOrigenBuscado.isEmpty() ? "Origen" : nombreOrigenBuscado);
        modelo.put("destino", nombreDestinoBuscado.isEmpty() ? "Destino" : nombreDestinoBuscado);
        modelo.put("sinResultados", viajesCalculados.isEmpty());
        return new ModelAndView("listadoViajes", modelo);
    }

    @RequestMapping(path = "/solicitar-espera", method = RequestMethod.POST)
    public ModelAndView solicitarViajeEnEspera() {
        ModelMap modelo = new ModelMap();
        modelo.put("mensaje", "¡Solicitud registrada! Quedas en lista de espera para esta ruta.");
        return new ModelAndView("home", modelo);
    }

    @RequestMapping(path = "/seleccionar-asiento", method = RequestMethod.GET)
    public ModelAndView irASeleccionarAsiento(@RequestParam("idViaje") Long idViaje,
                                              @RequestParam("pasajeros") Integer pasajeros,
                                              @RequestParam(value = "idParadaOrigen", required = false) Long idParadaOrigen,
                                              @RequestParam(value = "idParadaDestino", required = false) Long idParadaDestino,
                                              HttpServletRequest request) {

        Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuario");
        if (usuarioLogueado == null) return new ModelAndView("redirect:/login");

        ModelMap modelo = new ModelMap();
        Viaje viaje = servicioViaje.buscarPorId(idViaje);

        double precioPorPasajero;
        if (idParadaOrigen != null && idParadaDestino != null) {
            Long idParadaOrigenReal = viaje.getParadas().stream()
                    .filter(p -> p.getId().equals(idParadaOrigen))
                    .findFirst().get().getParada().getId();
            Long idParadaDestinoReal = viaje.getParadas().stream()
                    .filter(p -> p.getId().equals(idParadaDestino))
                    .findFirst().get().getParada().getId();

            precioPorPasajero = servicioViaje.calcularPrecioPorTramo(viaje, idParadaOrigenReal, idParadaDestinoReal);
        } else {
            precioPorPasajero = viaje.getPrecio();
        }

        Integer cantidadAsientos = viaje.getCombi().getCantidadDeAsientos();
        Integer filasAsientos = (cantidadAsientos + 1) / 2; // ceil(cantidadAsientos / 2) con enteros

        modelo.put("idViaje", idViaje);
        modelo.put("pasajeros", pasajeros);
        modelo.put("idParadaOrigen", idParadaOrigen);
        modelo.put("idParadaDestino", idParadaDestino);
        modelo.put("cantidadAsientos", cantidadAsientos);
        modelo.put("filasAsientos", filasAsientos);
        modelo.put("asientosOcupados", servicioViaje.obtenerAsientosOcupados(idViaje));
        modelo.put("usuarioLogueado", usuarioLogueado);
        modelo.put("precioViaje", precioPorPasajero);
        return new ModelAndView("seleccionarAsiento", modelo);
    }

    @RequestMapping(path = "/confirmar-asiento", method = RequestMethod.POST)
    public ModelAndView confirmarAsiento(@RequestParam("idViaje") Long idViaje,
                                         @RequestParam("pasajerosCount") Integer pasajerosCount,
                                         @RequestParam("asientosSeleccionados") String asientosSeleccionados,
                                         @RequestParam("nombres") List<String> nombres,
                                         @RequestParam("apellidos") List<String> apellidos,
                                         @RequestParam("dnis") List<String> dnis,
                                         @RequestParam("emails") List<String> emails,
                                         @RequestParam(value = "idParadaOrigen", required = false) Long idParadaOrigen,
                                         @RequestParam(value = "idParadaDestino", required = false) Long idParadaDestino,
                                         HttpServletRequest request) {

        Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuario");
        if (usuarioLogueado == null) return new ModelAndView("redirect:/login");

        try {
            Viaje viaje = servicioViaje.buscarPorId(idViaje);
            String[] arrayAsientos = asientosSeleccionados.split(",");
            Reserva reserva = new Reserva();
            reserva.setUsuario(usuarioLogueado);
            reserva.setViaje(viaje);
            reserva.setEstadoReserva(EstadoReserva.CONFIRMADA);

            if (idParadaOrigen != null && idParadaDestino != null) {
                ViajeParada vpOrigen = new ViajeParada();
                vpOrigen.setId(idParadaOrigen);
                reserva.setParadaOrigen(vpOrigen);

                ViajeParada vpDestino = new ViajeParada();
                vpDestino.setId(idParadaDestino);
                reserva.setParadaDestino(vpDestino);

                // Se invoca el calculo utilizando la interfaz
                double precioPorTramo = servicioViaje.calcularPrecioPorTramo(viaje,
                        viaje.getParadas().stream().filter(p -> p.getId().equals(idParadaOrigen)).findFirst().get().getParada().getId(),
                        viaje.getParadas().stream().filter(p -> p.getId().equals(idParadaDestino)).findFirst().get().getParada().getId()
                );
                reserva.setPrecioTotal(precioPorTramo * pasajerosCount);
            } else {
                reserva.setPrecioTotal(viaje.getPrecio() * pasajerosCount);
            }

            reserva.setPasajeros(new java.util.ArrayList<>());

            for (int i = 0; i < pasajerosCount; i++) {
                Pasajero p = new Pasajero();
                p.setNombre(nombres.get(i));
                p.setApellido(apellidos.get(i));
                p.setDni(dnis.get(i));
                p.setEmail(emails.get(i));
                p.setNumeroAsiento(Integer.parseInt(arrayAsientos[i]));
                p.setReserva(reserva);

                reserva.getPasajeros().add(p);
                servicioViaje.reservarAsiento(idViaje, usuarioLogueado);
            }

            servicioViaje.guardarReserva(reserva);

            // AHORA REDIRIGIMOS AL PERFIL Y GUARDAMOS EL MENSAJE DE ÉXITO EN SESIÓN
            request.getSession().setAttribute("mensajeReserva", "¡El viaje fue reservado con éxito!");
            return new ModelAndView("redirect:/perfilUsuario");

        } catch (Exception e) {
            System.err.println("ERROR AL GUARDAR RESERVA: ");
            e.printStackTrace();
            return new ModelAndView("redirect:/buscar-viaje");
        }
    }

    @RequestMapping(path = "/viajeEnCurso", method = RequestMethod.GET)
    public ModelAndView verMisViajes(HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuario");
        if (usuarioLogueado == null) return new ModelAndView("redirect:/login");

        // AHORA SOLO BUSCAMOS RESERVAS CUANDO EL ESTADO SEA 'EN_CURSO'
        List<Reserva> misReservas = servicioViaje.buscarReservasPorEstado(usuarioLogueado.getId(), EstadoReserva.EN_CURSO);

        modelo.put("misReservas", misReservas);
        modelo.put("sinViajes", misReservas.isEmpty());

        return new ModelAndView("viajeEnCurso", modelo);
    }

    @RequestMapping(path = "/cancelar-viaje", method = RequestMethod.POST)
    public ModelAndView cancelarViaje(@RequestParam("idViaje") Long idViaje, HttpServletRequest request) {
        Usuario usuarioLogueado = (Usuario) request.getSession().getAttribute("usuario");
        if (usuarioLogueado == null) return new ModelAndView("redirect:/login");

        // CANCELAMOS BUSCANDO EN LAS CONFIRMADAS
        List<Reserva> misReservas = servicioViaje.buscarReservasPorEstado(usuarioLogueado.getId(), EstadoReserva.CONFIRMADA);

        Reserva reservaACancelar = misReservas.stream()
                .filter(reserva -> reserva.getViaje().getId().equals(idViaje))
                .findFirst()
                .orElse(null);

        if (reservaACancelar != null) {
            int cantidadPasajeros = reservaACancelar.getPasajeros().size();
            for (int i = 0; i < cantidadPasajeros; i++) {
                servicioViaje.liberarAsiento(idViaje);
            }

            if (reservaACancelar.getId() != null) {
                servicioViaje.eliminarReserva(reservaACancelar.getId());
            }
        }
        // VUELVE A PERFIL LUEGO DE CANCELAR
        return new ModelAndView("redirect:/perfilUsuario");
    }
}