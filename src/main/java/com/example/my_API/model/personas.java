package com.example.my_API.model;



import java.util.Objects;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="personas")
public class personas {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long codigo;
	@NonNull
	private String nombre;
	@Embedded
	private Direccion direccion;
	@NonNull
	private Boolean activo;
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Direccion getDireccion() {
		return direccion;
	}
	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	public boolean isInactivo() {
        return this.activo != null && !this.activo;
    }
	@Override
	public int hashCode() {
		return Objects.hash(activo, codigo, direccion, nombre);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		personas other = (personas) obj;
		return Objects.equals(activo, other.activo) && Objects.equals(codigo, other.codigo)
				&& Objects.equals(direccion, other.direccion) && Objects.equals(nombre, other.nombre);
	}
	
	
	
	
}
