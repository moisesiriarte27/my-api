package com.example.my_API.repository.lanzamiento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.my_API.model.lanzamiento;
import com.example.my_API.repository.filter.lanzamientoFilter;
import com.example.my_API.repository.projection.ResumoLanzamiento;

public interface lanzamientoRepositoryQuery {

    Page<lanzamiento> filtrar(lanzamientoFilter lanzamientoFilter, Pageable pageable);

    Page<ResumoLanzamiento> resumir(lanzamientoFilter lanzamientoFilter, Pageable pageable);
}
