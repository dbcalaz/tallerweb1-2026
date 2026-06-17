package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Combi;
import com.tallerwebi.dominio.Conductor;
import com.tallerwebi.dominio.EstadoDeCombi;
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





    @RequestMapping("/admin/combis") // Usamos la URL más limpia y directa
    public ModelAndView listarCombis(@RequestParam(name = "criterio", required = false) String criterio) {

        ModelMap model = new ModelMap();
        List<Combi> listado;
        List<ReporteFalla> reportes = servicioAdministrador.obtenerFallasDeCombis();

        // Lógica de filtrado: sirve tanto para buscar por estado como para traer todas
        if (criterio != null && !criterio.isEmpty()) {
            listado = servicioAdministrador.obtenerCombis(criterio);
        } else {
            listado = servicioAdministrador.obtenerCombis();
        }

        // Asegurate de que el nombre "listaCombis" sea el que usás en tu HTML con th:each
        model.put("listaCombis", listado);
        model.put("reportes", reportes);

        // Poné el nombre exacto de tu archivo HTML (sin la extensión .html)
        return new ModelAndView("admin/combis-listas", model);
    }

    @RequestMapping(path = "/admin/combis/cambiar-estado", method = RequestMethod.POST)
    public ModelAndView cambiarEstadoCombi(@RequestParam("idCombi") Long idCombi,
                                           @RequestParam("nuevoEstado") String nuevoEstado) {

        // 1. Conviertes el String que viene del HTML al Enum real
        EstadoDeCombi estado = EstadoDeCombi.valueOf(nuevoEstado);

        // 2. Llamas a tu servicio para que actualice la base de datos.
        // (Asegúrate de tener o crear este método en tu Servicio/Repositorio)
        servicioAdministrador.actualizarEstadoCombi(idCombi, estado);

        // 3. Rediriges de vuelta a la lista de combis para ver los cambios actualizados
        return new ModelAndView("redirect:/admin/combis");
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
