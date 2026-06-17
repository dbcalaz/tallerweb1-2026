package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.CantidadDeAsientosInvalidaException;
import com.tallerwebi.dominio.excepcion.CombiExistenteException;
import com.tallerwebi.dominio.excepcion.TipoDeCombiInvalidaException;
import com.tallerwebi.dominio.excepcion.TipoDeTransmisionInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorCrearCombi {


    private ServicioCombi servicioCombi;

    @Autowired
    public ControladorCrearCombi(ServicioCombi servicioCombi) {

        this.servicioCombi = servicioCombi;
    }


    @RequestMapping("/crear-combi")
    public ModelAndView crearCombi() {
        ModelMap modelo = new ModelMap();
        modelo.put("combi", new DatosCombi());
        return new ModelAndView("admin/crear-combi", modelo);

    }

    @RequestMapping(path = "/crear-combi", method = RequestMethod.POST)
    public ModelAndView crearCombi(@ModelAttribute("combi") DatosCombi datosCombi) {
        ModelMap modelo = new ModelMap();

        ModelAndView modelo1 = capturarInputVaciosDelDto(datosCombi, modelo);
        if (modelo1 != null) return modelo1;
        Combi combiGuardada;
        try {
           combiGuardada = servicioCombi.crearCombi(datosCombi);
        } catch (BondiWayException ex) {
            modelo.put("error", ex.getMessage());
            return new ModelAndView("crear-combi", modelo);
        }
            modelo.put("combi", combiGuardada);
            modelo.put("mensaje", "La creacion fue exitosa");
            return new ModelAndView("admin/combi-registrada", modelo);

    }
    private static ModelAndView capturarInputVaciosDelDto(DatosCombi datosCombi, ModelMap modelo) {
        List<String> camposFaltantes = new ArrayList<>();

        // 1. Evaluamos cada campo y si está vacío, agregamos su nombre a la lista
        if (datosCombi.getTipoDeCombi() == null) {
            camposFaltantes.add("Tipo de combi");
        }
        if (datosCombi.getTipoDeTransmision() == null || datosCombi.getTipoDeTransmision().trim().isEmpty()) {
            camposFaltantes.add("Transmisión");
        }
        if (datosCombi.getCantidadDeAsientos() == null) {
            camposFaltantes.add("Cantidad de asientos");
        }
        if (datosCombi.getPatente() == null || datosCombi.getPatente().trim().isEmpty()) {
            camposFaltantes.add("Patente");
        }
        if (datosCombi.getMarca() == null || datosCombi.getMarca().trim().isEmpty()) {
            camposFaltantes.add("Marca");
        }
        if (datosCombi.getModelo() == null || datosCombi.getModelo().trim().isEmpty()) {
            camposFaltantes.add("Modelo");
        }
        if (datosCombi.getKilometros() == null ) {
            camposFaltantes.add("Kilometros");
        }

        // 2. Si la lista NO está vacía, significa que faltaron datos
        if (!camposFaltantes.isEmpty()) {

            String mensaje = "Por favor, complete los siguientes campos obligatorios: " + String.join(", ", camposFaltantes);

            modelo.put("error", mensaje);
            return new ModelAndView("admin/crear-combi", modelo);
        }

        // 3. Si llega hasta aquí, todos los campos estaban llenos
        return null;
    }



}
