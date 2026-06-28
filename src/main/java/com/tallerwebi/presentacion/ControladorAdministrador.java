package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.ViajeException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
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

   @RequestMapping(value = "/admin/combis", method = RequestMethod.GET)
   public ModelAndView listarCombis(@ModelAttribute DatosFiltro datosFiltro) {

       ModelMap model = new ModelMap();

       List<Combi> listado = servicioAdministrador.obtenerCombisFiltradas(datosFiltro);
       List<Combi> combisDisponibles = servicioAdministrador.obtenerCombisPorEstado(EstadoDeCombi.DISPONIBLE);
       List<Combi> combisEnMantenimiento = servicioAdministrador.obtenerCombisPorEstado(EstadoDeCombi.EN_MANTENIMIENTO);
       List<Combi> combisEnViaje = servicioAdministrador.obtenerCombisPorEstado(EstadoDeCombi.EN_VIAJE);
       Long cantidadCombis = (long) (combisDisponibles.size() + combisEnMantenimiento.size() + combisEnViaje.size());
       List<ReporteFalla> reportes = servicioAdministrador.obtenerFallasDeCombis();

       model.put("combisDisponibles", combisDisponibles);
       model.put("combisEnMantenimiento", combisEnMantenimiento);
       model.put("combisEnViaje", combisEnViaje);
       model.put("cantidadCombis", cantidadCombis);
       model.put("listaCombis", listado);
       model.put("reportes", reportes);
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

    @RequestMapping(path = "/resolver-falla", method = RequestMethod.POST)
    public ModelAndView resolverFalla(@RequestParam("idReporte") Long idReporte){

        servicioAdministrador.resolverFalla(idReporte);
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

        List<Conductor> conductores = servicioAdministrador.obtenerConductores(true, null);
        List<Conductor> conductoresPendientes = servicioAdministrador.obtenerConductores(false, null);
        List<Combi> combisDisponibles = servicioAdministrador.obtenerCombisPorEstado(EstadoDeCombi.DISPONIBLE);
        Long pendientes = (long) conductoresPendientes.size();

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
    public ModelAndView viajes() { return new ModelAndView("redirect:/admin/viajes");}

    @RequestMapping( path = "/admin/viajes")
    public ModelAndView vistaViajes() {
        ModelMap model = new ModelMap();

        List<Parada> paradas = servicioAdministrador.obtenerParadas();
        List<Viaje> viajes = servicioAdministrador.obtenerViajes();

        model.put("datosCrearViaje", new DatosCrearViaje());
        model.put("paradas", paradas);
        model.put("tiposDeViaje", TipoDeViaje.values());
        model.put("viajes", viajes);

        return new ModelAndView("admin/viajes", model);
    }

    @RequestMapping(path = "/crear-viaje", method = RequestMethod.POST)
    public ModelAndView crearNuevoViaje(
            @ModelAttribute("datosCrearViaje") DatosCrearViaje datos) {

        try {
            servicioAdministrador.crearNuevoViaje(datos);
            return new ModelAndView("redirect:/admin/viajes");

        } catch (ViajeException e) {

            ModelMap model = new ModelMap();
            model.put("error", e.getMessage());
            model.put("datosCrearViaje", datos);
            model.put("paradas", servicioAdministrador.obtenerParadas());
            model.put("tiposDeViaje", TipoDeViaje.values());

            return new ModelAndView("admin/viajes", model);
        }
    }


}
