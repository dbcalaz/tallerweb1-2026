package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.ConductorExistente;
import com.tallerwebi.dominio.excepcion.CuentaNoHabilitadaException;
import com.tallerwebi.dominio.excepcion.CuentaSuspendidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorConductor {

    private final ServicioConductor servicioConductor;

    @Autowired
    public ControladorConductor(ServicioConductor servicioConductor) {
        this.servicioConductor = servicioConductor;
    }

    @RequestMapping(path = "/login-conductor")
    public ModelAndView loginConductor(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        model.put("datosLogin", new DatosLogin());

        String error = (String) request.getSession().getAttribute("errorLoginConductor");
        if (error != null) {
            model.put("error", error);
            request.getSession().removeAttribute("errorLoginConductor");
        }

        return new ModelAndView("login-conductor", model);
    }

    @RequestMapping(path = "/validar-login-conductor", method = RequestMethod.POST)
    public ModelAndView validarLoginConductor(@ModelAttribute("datosLogin") DatosLogin datosLogin,
                                              HttpServletRequest request) {
        ModelMap model = new ModelMap();

        try {
            Conductor conductorEncontrado = servicioConductor.consultarConductor(
                    datosLogin.getEmail(),
                    datosLogin.getPassword()
            );

            if (conductorEncontrado != null) {
                request.getSession().setAttribute("conductor", conductorEncontrado);
                return new ModelAndView("redirect:/home-conductor");
            }

            model.put("error", "Las credenciales no son correctas");
            model.put("datosLogin", new DatosLogin());
            return new ModelAndView("login-conductor", model);

        } catch (CuentaNoHabilitadaException | CuentaSuspendidaException e) {
            model.put("error", e.getMessage());
            model.put("datosLogin", new DatosLogin());
            return new ModelAndView("login-conductor", model);
        }
    }

    /*Registro*/
    @RequestMapping(path = "/nuevo-conductor", method = RequestMethod.GET)
    public ModelAndView nuevoConductor() {
        ModelMap model = new ModelMap();
        model.put("conductor", new Conductor());
        model.put("tiposLicencias", TipoDeLicencia.values());
        return new ModelAndView("nuevo-conductor", model);
    }

    @RequestMapping(path = "/registrar-conductor", method = RequestMethod.POST)
    public ModelAndView registrarConductor(@ModelAttribute("conductor") Conductor conductor) {
        ModelMap model = new ModelMap();

        try {
            servicioConductor.registrarConductor(conductor);
            return new ModelAndView("redirect:/login-conductor");
        } catch (ConductorExistente e) {
            model.put("error", "El conductor ya existe");
        } catch (Exception e) {
            model.put("error", "Error al registrar conductor");
        }

        model.put("conductor", conductor);
        model.put("tiposLicencias", TipoDeLicencia.values());
        return new ModelAndView("nuevo-conductor", model);
    }

    @RequestMapping(path = "/home-conductor")
    public ModelAndView homeConductor(HttpServletRequest request) {
        Conductor conductorBuscado = obtenerConductorActivo(request);

        if (conductorBuscado == null) {
            return redirigirLoginConSesionCerrada(request);
        }

        Conductor conductor = servicioConductor.buscarPorId(conductorBuscado.getId());
        request.getSession().setAttribute("conductor", conductor);

        Combi combi = servicioConductor.buscarCombiActivePorIdConductor(conductor.getId());

        List<Viaje> viajesDisponibles = servicioConductor.obtenerViajesDisponibles();
        List<Viaje> viajesAsignados = servicioConductor.obtenerViajesDelConductorPorEstado(conductor.getId(), EstadoDeViaje.ASIGNADO);
        List<Viaje> viajesFinalizados = servicioConductor.obtenerViajesDelConductorPorEstado(conductor.getId(), EstadoDeViaje.FINALIZADO);
        Viaje viajeEnCurso = servicioConductor.obtenerViajeEnCursoDelConductor(conductor.getId());

        boolean tieneViajeAsignado = viajesAsignados != null && !viajesAsignados.isEmpty();

        ModelMap model = new ModelMap();
        model.put("conductor", conductor);
        model.put("combi", combi);
        model.put("reporteFalla", new ReporteFalla());

        model.put("viajesDisponibles", viajesDisponibles);
        model.put("viajesAsignados", viajesAsignados);
        model.put("viajesFinalizados", viajesFinalizados);
        model.put("viajeEnCurso", viajeEnCurso);
        model.put("tieneViajeAsignado", tieneViajeAsignado);

        String error = (String) request.getSession().getAttribute("errorConductor");
        String mensaje = (String) request.getSession().getAttribute("mensajeConductor");

        if (error != null) {
            model.put("error", error);
            request.getSession().removeAttribute("errorConductor");
        }

        if (mensaje != null) {
            model.put("mensaje", mensaje);
            request.getSession().removeAttribute("mensajeConductor");
        }

        return new ModelAndView("home-conductor", model);
    }

    @RequestMapping(path = "/conductor/aceptar-viaje", method = RequestMethod.POST)
    public ModelAndView aceptarViaje(@RequestParam("idViaje") Long idViaje,
                                     HttpServletRequest request) {
        Conductor conductor = obtenerConductorActivo(request);

        if (conductor == null) {
            return redirigirLoginConSesionCerrada(request);
        }

        try {
            servicioConductor.aceptarViaje(idViaje, conductor.getId());
            request.getSession().setAttribute("mensajeConductor", "Viaje aceptado correctamente.");
        } catch (Exception e) {
            request.getSession().setAttribute("errorConductor", e.getMessage());
        }

        return new ModelAndView("redirect:/home-conductor");
    }

    @RequestMapping(path = "/conductor/empezar-viaje", method = RequestMethod.POST)
    public ModelAndView empezarViaje(@RequestParam("idViaje") Long idViaje,
                                     HttpServletRequest request) {
        Conductor conductor = obtenerConductorActivo(request);

        if (conductor == null) {
            return redirigirLoginConSesionCerrada(request);
        }

        try {
            servicioConductor.iniciarViaje(idViaje, conductor.getId());
            request.getSession().setAttribute("mensajeConductor", "Viaje iniciado correctamente.");
        } catch (Exception e) {
            request.getSession().setAttribute("errorConductor", e.getMessage());
        }

        return new ModelAndView("redirect:/home-conductor");
    }

    @RequestMapping(path = "/conductor/finalizar-viaje", method = RequestMethod.POST)
    public ModelAndView finalizarViaje(@RequestParam("idViaje") Long idViaje,
                                       HttpServletRequest request) {
        Conductor conductor = obtenerConductorActivo(request);

        if (conductor == null) {
            return redirigirLoginConSesionCerrada(request);
        }

        try {
            servicioConductor.finalizarViaje(idViaje, conductor.getId());
            request.getSession().setAttribute("mensajeConductor", "Viaje finalizado correctamente.");
        } catch (Exception e) {
            request.getSession().setAttribute("errorConductor", e.getMessage());
        }

        return new ModelAndView("redirect:/home-conductor");
    }

    @RequestMapping(path = "/reportar-falla", method = RequestMethod.POST)
    public ModelAndView reportarFalla(@ModelAttribute("reporteFalla") ReporteFalla reporteFalla,
                                      HttpServletRequest request) {
        Conductor conductor = obtenerConductorActivo(request);

        if (conductor == null) {
            return redirigirLoginConSesionCerrada(request);
        }

        try {
            Combi combi = servicioConductor.buscarCombiActivePorIdConductor(conductor.getId());

            reporteFalla.setConductor(conductor);
            reporteFalla.setCombi(combi);

            servicioConductor.registrarFalla(reporteFalla);
            request.getSession().setAttribute("mensajeConductor", "La falla se reportó correctamente.");
        } catch (Exception e) {
            request.getSession().setAttribute("errorConductor", "La falla no se pudo registrar correctamente");
        }
        return new ModelAndView("redirect:/home-conductor");
    }

    private Conductor obtenerConductorActivo(HttpServletRequest request) {
        Conductor conductorSession = (Conductor) request.getSession().getAttribute("conductor");

        if (conductorSession == null) {
            return null;
        }

        Conductor conductorActualizado = servicioConductor.buscarPorId(conductorSession.getId());

        if (conductorActualizado == null || conductorActualizado.isSuspendido()) {
            return null;
        }

        request.getSession().setAttribute("conductor", conductorActualizado);
        return conductorActualizado;
    }

    private ModelAndView redirigirLoginConSesionCerrada(HttpServletRequest request) {
        request.getSession().invalidate();

        request.getSession(true).setAttribute(
                "errorLoginConductor",
                "Tu cuenta fue suspendida. Contactate con un administrador."
        );

        return new ModelAndView("redirect:/login-conductor");
    }
}