package com.example.my_API.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.my_API.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria,Long> {

}
