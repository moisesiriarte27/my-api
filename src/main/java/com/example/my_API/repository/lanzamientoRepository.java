package com.example.my_API.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.my_API.model.lanzamiento;

// No necesitas importar List ni @Query si no tienes métodos personalizados complejos

public interface lanzamientoRepository extends JpaRepository<lanzamiento, Long> {

    // Ya no necesitamos el método findAllWithRelations(), porque no hay relaciones que cargar.
    // El método findAll() que nos da JpaRepository por defecto es suficiente.
    
    // Si necesitas el método para otra cosa, debes eliminar los JOIN FETCH:
    /*
    @Query("SELECT l FROM lanzamiento l")
    List<lanzamiento> findAllWithRelations(); // El nombre ya no tendría sentido, mejor renombrarlo a findAllLanzamientos()
    */
}