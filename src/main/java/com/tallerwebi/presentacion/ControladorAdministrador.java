package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Combi;
import com.tallerwebi.dominio.Conductor;
import com.tallerwebi.dominio.EstadoDeCombi;
import com.tallerwebi.dominio.ReporteFalla;
import com.tallerwebi.dominio.ServicioAdministrador;
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



   /* @RequestMapping("/admin/combis")
    public ModelAndView listarCombis(@RequestParam(name = "criterio", required = false) String criterio) {

        ModelMap model = new ModelMap();
        List<Combi> listado;

        if (criterio != null && !criterio.isEmpty()) {
            listado = servicioAdministrador.obtenerCombis(criterio);
        } else {
            listado = servicioAdministrador.obtenerCombis();
        }


        List<Combi> combisDisponibles = servicioAdministrador.obtenerCombisDisponibles();
        Long cantidadCombis = servicioAdministrador.obtenerCantidadCombis();
        List<ReporteFalla> reportes = servicioAdministrador.obtenerFallasDeCombis();
        model.put("combisDisponibles", combisDisponibles);
        model.put("cantidadCombis", cantidadCombis);
        model.put("listaCombis", listado);
        model.put("reportes", reportes);

        return new ModelAndView("admin/combis-listas", model);
    }*/
   @RequestMapping(value = "/admin/combis", method = RequestMethod.GET)
   public ModelAndView listarCombis(@ModelAttribute DatosFiltro datosFiltro) {

       ModelMap model = new ModelMap();

       // 1. Buscamos las combis pasándole el DTO completo.
       // Si no se hizo click en nada, datosFiltro se crea vacío y trae todas.
       List<Combi> listado = servicioAdministrador.obtenerCombisFiltradas(datosFiltro);

       // 2. Mantenemos el resto de tu lógica intacta
       List<Combi> combisDisponibles = servicioAdministrador.obtenerCombisDisponibles();
       Long cantidadCombis = servicioAdministrador.obtenerCantidadCombis();
       List<ReporteFalla> reportes = servicioAdministrador.obtenerFallasDeCombis();

       model.put("combisDisponibles", combisDisponibles);
       model.put("cantidadCombis", cantidadCombis);
       model.put("listaCombis", listado);
       model.put("reportes", reportes);

       // 3. IMPORTANTE: Mandamos el DTO de vuelta a la vista para que la
       // botonera sepa qué botón pintar como "activo"
       model.put("filtroActual", datosFiltro);

       return new ModelAndView("admin/combis-listas", model);
   }


    @RequestMapping(path = "/admin/combis/cambiar-estado", method = RequestMethod.POST)
    public ModelAndView cambiarEstadoCombi(@RequestParam("idCombi") Long idCombi,
                                           @RequestParam("nuevoEstado") String nuevoEstado) {
        EstadoDeCombi estado = EstadoDeCombi.valueOf(nuevoEstado);

        servicioAdministrador.actualizarEstadoCombi(idCombi, estado);

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

        List<Conductor> conductores = servicioAdministrador.obtenerConductores(true);
        List<Conductor> conductoresPendientes = servicioAdministrador.obtenerConductores(false);
        Long pendientes = (long) servicioAdministrador.obtenerConductores(false).size();
        List<Combi> combisDisponibles = servicioAdministrador.obtenerCombisDisponibles();

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
