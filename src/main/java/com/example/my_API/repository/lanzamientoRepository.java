package com.example.my_API.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.my_API.model.lanzamiento;
import com.example.my_API.repository.filter.lanzamientoFilter;
import com.example.my_API.repository.projection.ResumoLanzamiento;

import java.util.List;

@Repository
public interface lanzamientoRepository extends JpaRepository<lanzamiento, Long> {

    @Query("SELECT l FROM lanzamiento l " +
           "JOIN FETCH l.categoria " +
           "JOIN FETCH l.personas")
    List<lanzamiento> findAllWithRelations();

	Page<ResumoLanzamiento> resumir(lanzamientoFilter lanzamientoFilter, Pageable pageable);
}
