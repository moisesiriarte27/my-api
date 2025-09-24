package com.example.my_API.repository.projection;


import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.my_API.model.Tipolanzamiento;

public class ResumoLanzamiento {

    private Long codigo;
    private String descripcion;
    private LocalDate fechavencimiento;
    private LocalDate fechapago;
    private BigDecimal valor;
    private Tipolanzamiento tipo;
    private String categoria;
    private String persona;

    public ResumoLanzamiento(Long codigo, String descripcion, LocalDate fechavencimiento, LocalDate fechapago,
                             BigDecimal valor, Tipolanzamiento tipo, String categoria, String persona) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fechavencimiento = fechavencimiento;
        this.fechapago = fechapago;
        this.valor = valor;
        this.tipo = tipo;
        this.categoria = categoria;
        this.persona = persona;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechavencimiento() {
        return fechavencimiento;
    }

    public void setFechavencimiento(LocalDate fechavencimiento) {
        this.fechavencimiento = fechavencimiento;
    }

    public LocalDate getFechapago() {
        return fechapago;
    }

    public void setFechapago(LocalDate fechapago) {
        this.fechapago = fechapago;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Tipolanzamiento getTipo() {
        return tipo;
    }

    public void setTipo(Tipolanzamiento tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }
}
