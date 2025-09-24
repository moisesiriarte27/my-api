package com.example.my_API.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Direccion {
    private String lugarpublico;
    private String numero ;
    private String complemento; 
    private String barrio;
    private String cep;
    private String ciudad;
    private String estado;

    public String getlugarpublico() {
        return lugarpublico;
    }
    public void setlugarpublico(String lugarpublico) {
        this.lugarpublico = lugarpublico;
    }

    public String getnumero() {
        return numero;
    }
    public void setnumero(String numero) {
        this.numero = numero;
    }

    public String getcomplemento() {
        return complemento;
    }
    public void setcomplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getbarrio() {
        return barrio;
    }
    public void setbarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getcep() {
        return cep;
    }
    public void setcep(String cep) {
        this.cep = cep;
    }

    public String getciudad() {
        return ciudad;
    }
    public void setciudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getestado() {
        return estado;
    }
    public void setestado(String estado) {
        this.estado = estado;
    }
}
