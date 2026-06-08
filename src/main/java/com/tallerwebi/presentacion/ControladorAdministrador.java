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
        List<ReporteFalla> reportes = servicioAdministrador.obtenerFallasDeCombis();
        model.put("combis", combis);
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
        model.put("conductores", conductores);
        return new ModelAndView("admin/conductores", model);
    }

    @GetMapping("/viajes")
    public ModelAndView viajes() {
        return new ModelAndView("redirect:/admin/viajes");
    }


}
