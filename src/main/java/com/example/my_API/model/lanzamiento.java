package com.example.my_API.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

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
    private Tipolanzamiento tipo; // Asumiendo que Tipolanzamiento es un Enum que tienes definido

    // --- MODIFICACIÓN #1 ---
    // Cambiado de un objeto 'Categoria' a un 'Long' para que coincida con la columna de la BD.
    @NotNull(message = "Categoria es obligatoria")
    @Column(name = "codigo_categoria")
    private Long codigo_categoria;

    // --- MODIFICACIÓN #2 ---
    // Cambiado de un objeto 'personas' a un 'Long' para que coincida con la columna de la BD.
    @NotNull(message = "Persona es obligatoria")
    @Column(name = "codigo_personas")
    private Long codigo_personas;


    // --- GETTERS Y SETTERS ---
    // (Actualizados para reflejar los cambios)

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

    public Long getCodigo_categoria() { return codigo_categoria; }
    public void setCodigo_categoria(Long codigo_categoria) { this.codigo_categoria = codigo_categoria; }

    public Long getCodigo_personas() { return codigo_personas; }
    public void setCodigo_personas(Long codigo_personas) { this.codigo_personas = codigo_personas; }

    // hashCode y equals (no cambian)
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
}
