package com.example.my_API.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(lanzamiento.class)
public abstract class lanzamiento_ {

    public static volatile SingularAttribute<lanzamiento, Long> codigo;
    public static volatile SingularAttribute<lanzamiento, String> observacion;
    public static volatile SingularAttribute<lanzamiento, Tipolanzamiento> tipo;
    public static volatile SingularAttribute<lanzamiento, LocalDate> fechaPago;
    public static volatile SingularAttribute<lanzamiento, Persona> persona;
    public static volatile SingularAttribute<lanzamiento, LocalDate> fechaVencimiento;
    public static volatile SingularAttribute<lanzamiento, Categoria> categoria;
    public static volatile SingularAttribute<lanzamiento, BigDecimal> valor;
    public static volatile SingularAttribute<lanzamiento, String> descripcion;

}
