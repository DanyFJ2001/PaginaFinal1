package com.itsqmet.app_hotel.Servicio;

import org.springframework.stereotype.Component;

@Component
public class AdminInitializer {
    private final ClienteServicio clienteServicio;

    public AdminInitializer(ClienteServicio clienteServicio) {
        this.clienteServicio = clienteServicio;
    }


    public void run(String... args) {
        clienteServicio.crearAdmin("Administrador", "admin@gmail.com", "admin", "admin123");
    }
}
