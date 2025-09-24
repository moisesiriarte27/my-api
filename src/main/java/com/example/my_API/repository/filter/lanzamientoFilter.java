package com.example.my_API.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class lanzamientoFilter {

	private String descripcion;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate fechavencimientoDe;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate fechavencimientoAte;
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public LocalDate getFechavencimientoDe() {
		return fechavencimientoDe;
	}
	public void setFechavencimientoDe(LocalDate fechavencimientoDe) {
		this.fechavencimientoDe = fechavencimientoDe;
	}
	public LocalDate getFechavencimientoAte() {
		return fechavencimientoAte;
	}
	public void setFechavencimientoAte(LocalDate fechavencimientoAte) {
		this.fechavencimientoAte = fechavencimientoAte;
	}
}
