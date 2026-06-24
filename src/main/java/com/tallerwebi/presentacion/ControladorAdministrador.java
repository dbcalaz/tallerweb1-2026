package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.ServicioAdministrador;
import com.tallerwebi.dominio.excepcion.BondiWayException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorAdministrador {

    private ServicioAdministrador servicioAdministrador;
    private ServicioViaje servicioViaje;

    public ControladorAdministrador(ServicioAdministrador servicioAdministrador, ServicioViaje servicioViaje) {
        this.servicioAdministrador = servicioAdministrador;
        this.servicioViaje = servicioViaje;
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

       List<Combi> combisDisponibles = servicioAdministrador.obtenerCombisDisponibles();
       Long cantidadCombis = servicioAdministrador.obtenerCantidadCombis();
       List<ReporteFalla> reportes = servicioAdministrador.obtenerFallasDeCombis();

       model.put("combisDisponibles", combisDisponibles);
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




    @RequestMapping(path = "/viajes/crear-viaje")
    public ModelAndView irACrearViaje() {
        ModelMap model = new ModelMap();


        model.put("datosCrearViaje", new DatosCrearViaje());
        List<Parada> paradasDisponibles= servicioViaje.obtenerTodasLasParadas();

        // Enviamos las listas para armar los <select> <option> en el HTML
        model.put("combisDisponibles", servicioAdministrador.obtenerCombisDisponibles());
        model.put("conductoresDisponibles", servicioAdministrador.obtenerConductores());
        model.put("paradasDisponibles", paradasDisponibles);

        return new ModelAndView("admin/crear-viaje", model);
    }



    @RequestMapping(path = "/viajes/crear-viaje",method = RequestMethod.POST)
    public ModelAndView crearViajes(@ModelAttribute  DatosCrearViaje datosCrearViaje) throws BondiWayException {
        ModelMap modelo = new ModelMap();

        ModelAndView modelo1 = capturarInputVaciosDelDto(datosCrearViaje, modelo);
        if (modelo1 != null) return modelo1;

        try {
            servicioAdministrador.guardarViaje(datosCrearViaje);
        } catch (BondiWayException ex) {
            modelo.put("error", ex.getMessage());
            return new ModelAndView("admin/crear-viaje", modelo);
        }

        modelo.put("mensaje", "La creacion fue exitosa");
        return new ModelAndView("admin/viaje-creado", modelo);



    }

    private static ModelAndView capturarInputVaciosDelDto(DatosCrearViaje datosCrearViaje, ModelMap modelo) {
        List<String> camposFaltantes = new ArrayList<>();

        if (datosCrearViaje.getOrigen()== null || datosCrearViaje.getOrigen().trim().isEmpty()) {
            camposFaltantes.add("Origen");
        }
        if (datosCrearViaje.getDestino() == null || datosCrearViaje.getDestino().trim().isEmpty()) {
            camposFaltantes.add("Destino");
        }
        if (datosCrearViaje.getFecha() == null) {
            camposFaltantes.add("Fecha");
        }
        if (datosCrearViaje.getHorario() == null || datosCrearViaje.getHorario().trim().isEmpty()) {
            camposFaltantes.add("Horario");
        }
        if (datosCrearViaje.getDistancia() == null || datosCrearViaje.getDistancia()==0d) {
            camposFaltantes.add("Distancia");
        }
        if (datosCrearViaje.getValorPorKm() == null || datosCrearViaje.getValorPorKm()==0d) {
            camposFaltantes.add("ValorPorKm");
        }


        // 2. Si la lista NO está vacía, significa que faltaron datos
        if (!camposFaltantes.isEmpty()) {

            String mensaje = "Por favor, complete los siguientes campos obligatorios: " + String.join(", ", camposFaltantes);

            modelo.put("error", mensaje);
            return new ModelAndView("admin/crear-viaje", modelo);
        }


        return null;

    }


}
