package com.example.my_API.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "lanzamiento")
public class lanzamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotBlank(message = "Descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "Fecha de vencimiento es obligatoria")
    @Column(name = "fecha_vencimiento")
    private LocalDate fechavencimiento;

    @Column(name = "fecha_pago")
    private LocalDate fechapago;

    @NotNull(message = "Valor es obligatorio")
    private BigDecimal valor;

    private String observacion;

    @NotNull(message = "Tipo de lanzamiento es obligatorio")
    @Enumerated(EnumType.STRING)
    private Tipolanzamiento tipo;

    @NotNull(message = "Categoria es obligatoria")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigo_categoria")
    @JsonIgnoreProperties({"lanzamientos"})
    private Categoria categoria;

    @NotNull(message = "Persona es obligatoria")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigo_personas")
    @JsonIgnoreProperties({"lanzamientos"})
    private personas personas;

    // hashCode y equals
    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        lanzamiento other = (lanzamiento) obj;
        return Objects.equals(codigo, other.codigo);
    }

    // Getters y Setters
    public Long getCodigo() { return codigo; }
    public void setCodigo(Long codigo) { this.codigo = codigo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFechavencimiento() { return fechavencimiento; }
    public void setFechavencimiento(LocalDate fechavencimiento) { this.fechavencimiento = fechavencimiento; }

    public LocalDate getFechapago() { return fechapago; }
    public void setFechapago(LocalDate fechapago) { this.fechapago = fechapago; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }

    public Tipolanzamiento getTipo() { return tipo; }
    public void setTipo(Tipolanzamiento tipo) { this.tipo = tipo; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public personas getPersonas() { return personas; }
    public void setPersonas(personas personas) { this.personas = personas; }
}

