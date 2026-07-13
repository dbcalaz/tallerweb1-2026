package com.tallerwebi.infraestructura; // Ajustá al paquete que corresponda

import com.tallerwebi.dominio.Conductor;
import com.tallerwebi.dominio.ServicioPuntuacion;
import com.tallerwebi.dominio.ServicioAdministrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SincronizadorCalificacionesInicial implements ApplicationListener<ContextRefreshedEvent> {

    private final ServicioAdministrador servicioAdministrador;
    private final ServicioPuntuacion servicioPuntuacion;

    @Autowired
    public SincronizadorCalificacionesInicial(ServicioAdministrador servicioAdministrador,
                                              ServicioPuntuacion servicioPuntuacion) {
        this.servicioAdministrador = servicioAdministrador;
        this.servicioPuntuacion = servicioPuntuacion;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Nos aseguramos de que sea el contexto raíz de Spring y no un contexto hijo (como el del DispatcherServlet)
        if (event.getApplicationContext().getParent() == null) {
            try {
                System.out.println(">> [Sincronizador Spring MVC] Buscando conductores para recalcular promedios...");

                // Traemos todos los conductores
                List<Conductor> conductores = servicioAdministrador.obtenerConductores(null, null);

                if (conductores != null && !conductores.isEmpty()) {
                    System.out.println(">> [Sincronizador] Sincronizando promedios de " + conductores.size() + " conductores...");
                    for (Conductor conductor : conductores) {
                        servicioPuntuacion.actualizarPromedioConductor(conductor);
                    }
                    System.out.println(">> [Sincronizador] ¡Sincronización inicial completada con éxito!");
                }
            } catch (Exception e) {
                // Evitamos romper la inicialización de la app si hay algún inconveniente con la DB
                System.err.println(">> [Sincronizador] Error al sincronizar calificaciones al inicio: " + e.getMessage());
            }
        }
    }
}