package com.example.my_API.model;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Persona.class)
public abstract class Persona_ {

    public static volatile SingularAttribute<Persona, Long> codigo;
    public static volatile SingularAttribute<Persona, Boolean> activo;
    public static volatile SingularAttribute<Persona, Direccion> direccion;
    public static volatile SingularAttribute<Persona, String> nombre;

}

