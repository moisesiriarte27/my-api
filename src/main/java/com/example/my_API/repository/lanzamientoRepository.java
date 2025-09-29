package com.example.my_API.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.my_API.model.lanzamiento;
import com.example.my_API.repository.projection.ResumoLanzamiento;
import java.util.List;

public interface lanzamientoRepository extends JpaRepository<lanzamiento, Long> {

    // --- NUEVA CONSULTA ---
    // Esta consulta une las tablas y devuelve una lista de ResumoLanzamiento.
    // Nota: Asegúrate que tus entidades se llamen 'Categoria' y 'personas' y sus campos 'nome' y 'nombre'
    @Query("SELECT new com.example.my_API.repository.projection.ResumoLanzamiento(" +
           "l.codigo, l.descripcion, l.fechavencimiento, l.fechapago, l.valor, l.tipo, c.nome, p.nombre) " +
           "FROM lanzamiento l, Categoria c, personas p " +
           "WHERE l.codigo_categoria = c.codigo AND l.codigo_personas = p.codigo")
    List<ResumoLanzamiento> findAllResumo();
}

