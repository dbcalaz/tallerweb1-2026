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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorConductor {

    ServicioConductor servicioConductor;

    @Autowired
    public ControladorConductor(ServicioConductor servicioConductor) {
        this.servicioConductor = servicioConductor;
    }

    /*Login*/
    @RequestMapping(path = "/login-conductor")
    public ModelAndView loginConductor() {
        DatosLogin datosLogin = new DatosLogin();
        ModelMap model = new ModelMap();
        model.put("datosLogin", datosLogin);
        return new ModelAndView("login-conductor", model);
    }

    @RequestMapping(path = "/validar-login-conductor", method = RequestMethod.POST)
    public ModelAndView validarLoginConductor(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        try {
            Conductor conductorEncontrado =
                    servicioConductor.consultarConductor(
                            datosLogin.getEmail(),
                            datosLogin.getPassword()
                    );
            if (conductorEncontrado != null) {
                request.getSession().setAttribute("conductor", conductorEncontrado);
                return new ModelAndView("redirect:/home-conductor");
            }
            model.put("error", "Las credenciales no son correctas");
            return new ModelAndView("login-conductor", model);
        } catch (CuentaNoHabilitadaException | CuentaSuspendidaException e) {
            model.put("error", e.getMessage());
            return new ModelAndView("login-conductor", model);
        }
    }

    /*Registro*/
    @RequestMapping(path = "/nuevo-conductor", method = RequestMethod.GET)
    public ModelAndView nuevoConductor() {
        Conductor conductor = new Conductor();
        ModelMap model = new ModelMap();
        model.put("conductor", conductor);
        model.put("tiposLicencias", TipoDeLicencia.values());
        return new ModelAndView("nuevo-conductor", model);
    }

    @RequestMapping(path = "/registrar-conductor", method = RequestMethod.POST)
    public ModelAndView registrarConductor(@ModelAttribute("conductor") Conductor conductor) {
        ModelMap model = new ModelMap();

        try {
            servicioConductor.registrarConductor(conductor);
        } catch (ConductorExistente e) {
            model.put("error", "El conductor ya existe");
            return new ModelAndView("nuevo-conductor", model);
        } catch (Exception e) {
            model.put("error", "Error al registrar conductor");
            return new ModelAndView("nuevo-conductor", model);
        }
        model.put("mensaje", "Conductor registrado correctamente");
        return new ModelAndView("redirect:/login-conductor", model);
    }

    @RequestMapping(path = "/home-conductor")
    public ModelAndView homeConductor(HttpServletRequest request) {
        Conductor conductor = (Conductor) request.getSession().getAttribute("conductor");

        if (conductor == null) {
            return new ModelAndView("redirect:/login-conductor");
        }

        Combi combi = servicioConductor.buscarCombiActivePorIdConductor(conductor.getId());
        List<Viaje> viajesPendientes = servicioConductor.obtenerViajesPendientesDelConductor(conductor.getId());
        List<Viaje> viajesFinalizados = servicioConductor.obtenerViajesFinalizadosDelConductor(conductor.getId());

        ModelMap model = new ModelMap();
        model.put("conductor", conductor);
        model.put("combi", combi);
        model.put("reporteFalla", new ReporteFalla());
        model.put("viajesPendientes", viajesPendientes);
        model.put("viajesFinalizados", viajesFinalizados);
        return new ModelAndView("home-conductor", model);
    }

    @RequestMapping(path = "/reportar-falla", method = RequestMethod.POST)
    public ModelAndView reportarFalla(@ModelAttribute("reporteFalla") ReporteFalla reporteFalla, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        Conductor conductor = (Conductor) request.getSession().getAttribute("conductor");
        Combi combi = servicioConductor.buscarCombiActivePorIdConductor(conductor.getId());

        reporteFalla.setConductor(conductor);
        reporteFalla.setCombi(combi);

        try {
            servicioConductor.registrarFalla(reporteFalla);
        } catch (Exception e) {
            model.put("error", "La falla no se pudo registrar correctamente");
            return new ModelAndView("home-conductor", model);
        }
        return new ModelAndView("redirect:/home-conductor");
    }

    /*Otras cosas*/
    /*
     * Validar nueva cuenta o recupero de contraseña con email (librería - JavaMailSender + Jakarta Mail (o Javax Mail))
     * */
}