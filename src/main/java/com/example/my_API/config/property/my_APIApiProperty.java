package com.example.my_API.config.property;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("my-api")
public class my_APIApiProperty {

    // Origen permitido para CORS
    private String origenPermitido = "http://localhost:8000";

    // Configuración de seguridad
    private final Seguridad seguridad = new Seguridad();

    public Seguridad getSeguridad() {
        return seguridad;
    }

    public String getOrigenPermitido() {
        return origenPermitido;
    }

    public void setOrigenPermitido(String origenPermitido) {
        this.origenPermitido = origenPermitido;
    }

    // Clase interna para opciones de seguridad
    public static class Seguridad {

        private boolean habilitarHttps;

        public boolean isHabilitarHttps() {
            return habilitarHttps;
        }

        public void setHabilitarHttps(boolean habilitarHttps) {
            this.habilitarHttps = habilitarHttps;
        }

    }

}
