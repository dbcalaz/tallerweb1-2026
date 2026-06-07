package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ReporteFalla;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/combis")
    public ModelAndView combis() {
        return new ModelAndView("redirect:/admin/combis");
    }

    @GetMapping("/vista-conductores")
    public ModelAndView vistaConductores() { return new ModelAndView("/conductores"); }

    @GetMapping("/viajes")
    public ModelAndView viajes() {
        return new ModelAndView("admin/viajes");
    }

    @RequestMapping(path = "/admin/combis")
    public ModelAndView conductores() {
        ModelMap model = new ModelMap();

        List<ReporteFalla> reportes = servicioAdministrador.obtenerFallasDeCombis();
        model.put("reportes", reportes);
        System.out.println("REPORTES: " + reportes);
        return new ModelAndView("admin/combis",model);
    }
}
