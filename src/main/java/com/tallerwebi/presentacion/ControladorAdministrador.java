package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Combi;
import com.tallerwebi.dominio.Conductor;
import com.tallerwebi.dominio.ReporteFalla;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorAdministrador {

    private ServicioAdministrador servicioAdministrador;

    public ControladorAdministrador(ServicioAdministrador servicioAdministrador) {
        this.servicioAdministrador = servicioAdministrador;
    }

    @GetMapping("/admin")
    public ModelAndView dashboard() {
        return new ModelAndView("admin/panel-administrador");
    }

    //Combis
    @GetMapping("/combis")
    public ModelAndView vistaCombis() {
        return new ModelAndView("redirect:/admin/combis");
    }

    @RequestMapping(path = "/admin/combis")
    public ModelAndView combis() {
        ModelMap model = new ModelMap();

        List<Combi> combis = servicioAdministrador.obtenerCombis();
        Long cantidadCombis = servicioAdministrador.obtenerCantidadCombis();
        List<ReporteFalla> reportes = servicioAdministrador.obtenerFallasDeCombis();
        model.put("combis", combis);
        model.put("cantidadCombis", cantidadCombis);
        model.put("reportes", reportes);
        return new ModelAndView("admin/combis", model);
    }

    @RequestMapping( path = "/nueva-asignacion", method =  RequestMethod.POST)
    public ModelAndView asignarNuevaCombiAConductor(
            @RequestParam Long idReporte,
            @RequestParam Long idCombi) {

        servicioAdministrador.asignarNuevaCombiAConductor(idReporte, idCombi);

        return new ModelAndView("redirect:/admin/combis");
    }

    //Conductores
    @GetMapping("/conductores")
    public ModelAndView vistaConductores() {
        return new ModelAndView("redirect:/admin/conductores");
    }

    @RequestMapping(path = "/admin/conductores")
    public ModelAndView conductores() {
        ModelMap model = new ModelMap();

        List<Conductor> conductores = servicioAdministrador.obtenerConductores();
        List<Conductor> conductoresPendientes = servicioAdministrador.obtenerConductoresPendientes();
        Long pendientes = servicioAdministrador.obtenerCantidadDeConductoresPendientes();
        List<Combi> combisDisponibles = servicioAdministrador.obtenerCombis();

        model.put("conductores", conductores);
        model.put("conductoresPendientes", conductoresPendientes);
        model.put("pendientes", pendientes);
        model.put("combisDisponibles", combisDisponibles);
        return new ModelAndView("admin/conductores", model);
    }

    @RequestMapping(path = "/habilitacion-asignacion", method =  RequestMethod.POST)
    public ModelAndView asignarCombiHabilitacionConductor(@RequestParam Long idConductor, @RequestParam Long idCombi) {
        servicioAdministrador.habilitarConductor(idConductor, idCombi);
        return vistaConductores();
    }

    @RequestMapping(path = "/suspender", method =  RequestMethod.POST)
    public ModelAndView suspenderConductor(@RequestParam Long idConductor) {
        servicioAdministrador.suspenderConductor(idConductor);
        return vistaConductores();
    }

    @RequestMapping(path = "/reactivar", method =  RequestMethod.POST)
    public ModelAndView reactivarConductor(@RequestParam Long idConductor) {
        servicioAdministrador.reactivarConductor(idConductor);
        return vistaConductores();
    }

    /* Viajes*/
    @GetMapping("/viajes")
    public ModelAndView viajes() {
        return new ModelAndView("admin/viajes");
    }


}
