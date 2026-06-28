package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ViajeException;
import com.tallerwebi.presentacion.DatosCrearViaje;
import com.tallerwebi.presentacion.DatosFiltro;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioAdministradorImpl implements ServicioAdministrador {

    RepositorioAdministrador repositorioAdministrador;
    RepositorioCombi repositorioCombi;

    public ServicioAdministradorImpl(RepositorioAdministrador repositorioAdministrador, RepositorioCombi repositorioCombi) {
        this.repositorioAdministrador = repositorioAdministrador;
        this.repositorioCombi = repositorioCombi;
    }

    /*Combis*/
    @Override
    public List<ReporteFalla> obtenerFallasDeCombis() {
        return repositorioAdministrador.getFallas();
    }

    @Override
    public void resolverFalla(Long idReporte) {
        ReporteFalla reporte = repositorioAdministrador.getReporteFallePorIdReporte(idReporte);
        Combi combi = reporte.getCombi();

        reporte.setEstadoReporte(EstadoReporteFalla.RESUELTO);
        combi.setEstadoDeCombi(EstadoDeCombi.DISPONIBLE);

        repositorioAdministrador.updateFalla(reporte);
        repositorioAdministrador.actualizarCombi(combi);
    }

    @Override
    public void asignarNuevaCombiAConductor(Long idReporte, Long idCombi) {
        repositorioAdministrador.updateCombiConductor(idReporte, idCombi);
    }

    @Override
    public List<Combi> obtenerCombisFiltradas(DatosFiltro datosFiltro) {
        return repositorioAdministrador.getCombisFiltradas(datosFiltro);
    }

    @Override
    public List<Combi> obtenerCombisPorEstado(EstadoDeCombi estado) {
        return repositorioAdministrador.getCombisPorEstado(estado);
    }

    @Override
    public void actualizarEstadoCombi(Long idCombi, EstadoDeCombi estado) {
        Combi combiExiste = repositorioCombi.buscarPorId(idCombi);
        if (combiExiste != null) {
            combiExiste.setEstadoDeCombi(estado);
            repositorioAdministrador.actualizarCombi(combiExiste);
        }
    }

    /*Conductor*/
    @Override
    public List<Conductor> obtenerConductores(Boolean estadoCuenta, String estado) {
        return repositorioAdministrador.getConductores(estadoCuenta, estado);
    }

    @Override
    public void habilitarConductor(Long idConductor, Long idCombi) {
        Conductor conductor = repositorioAdministrador.buscarConductorPorId(idConductor);

        if (conductor == null) {
            throw new RuntimeException("No existe el conductor seleccionado");
        }

        Combi combi = repositorioAdministrador.buscarCombiPorId(idCombi);

        if (combi == null) {
            throw new RuntimeException("No existe la combi seleccionada");
        }

        if (conductor.isCuentaHabilitada()) {
            throw new RuntimeException("El conductor ya fue habilitado");
        }

        conductor.setCuentaHabilitada(true);
        repositorioAdministrador.actualizarConductor(conductor);

        AsignacionCombiConductor asignacion = new AsignacionCombiConductor();

        asignacion.setConductor(conductor);
        asignacion.setCombi(combi);
        asignacion.setCombiActiva(true);

        repositorioAdministrador.guardarAsignacion(asignacion);
    }

    @Override
    public void suspenderConductor(Long idConductor) {
        Conductor conductor = repositorioAdministrador.buscarConductorPorId(idConductor);

        if (conductor == null) {
            throw new RuntimeException("No existe el conductor seleccionado");
        }

        repositorioAdministrador.suspenderConductor(conductor);
    }

    @Override
    public void reactivarConductor(Long idConductor) {
        Conductor conductor = repositorioAdministrador.buscarConductorPorId(idConductor);

        if (conductor == null) {
            throw new RuntimeException("No existe el conductor seleccionado");
        }

        repositorioAdministrador.reactivarConductor(conductor);
    }

    /*Viajes*/
    @Override
    public List<Parada> obtenerParadas() {
        return repositorioAdministrador.getParadas();
    }

    @Override
    public List<Viaje> obtenerViajes() {
        return repositorioAdministrador.getViajes();
    }

    @Override
    @Transactional
    public void crearNuevoViaje(DatosCrearViaje datos) {

        if (datos == null) {
            throw new ViajeException("No se recibieron los datos del viaje.");
        }

        if (datos.getFecha() == null) {
            throw new ViajeException("Debe seleccionar una fecha.");
        }

        if (datos.getHorario() == null) {
            throw new ViajeException("Debe seleccionar un horario.");
        }

        if (datos.getTipoDeViaje() == null) {
            throw new ViajeException("Debe seleccionar un tipo de viaje.");
        }

        if (datos.getPrecio() == null || datos.getPrecio() <= 0) {
            throw new ViajeException("Debe ingresar un precio válido.");
        }

        if (datos.getOrigenId() == null) {
            throw new ViajeException("Debe seleccionar una parada de origen.");
        }

        if (datos.getDestinoId() == null) {
            throw new ViajeException("Debe seleccionar una parada de destino.");
        }

        if (datos.getOrigenId().equals(datos.getDestinoId())) {
            throw new ViajeException("La parada de origen y destino no pueden ser iguales.");
        }

        Viaje viaje = new Viaje();

        viaje.setFecha(datos.getFecha());
        viaje.setHorario(datos.getHorario());
        viaje.setPrecio(datos.getPrecio());

        viaje.setTipoDeViaje(datos.getTipoDeViaje());
        viaje.setEstadoDeViaje(EstadoDeViaje.DISPONIBLE);

        viaje.setCombi(null);
        viaje.setConductor(null);

        repositorioAdministrador.insertNuevoViaje(viaje, datos);
    }

}
