// Archivo: src/main/java/com/example/my_API/Controller/CategoriasController.java

package com.example.my_API.Controller;

import com.example.my_API.model.Categoria;
import com.example.my_API.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import jakarta.validation.Valid; // o jakarta.validation.Valid

@RestController
@RequestMapping("/categorias")
public class CategoriasController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Endpoint para LISTAR todas las categorías (GET /categorias)
    @GetMapping
    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    // Endpoint para CREAR una nueva categoría (POST /categorias)
    @PostMapping
    public ResponseEntity<Categoria> crear(@Valid @RequestBody Categoria categoria) {
        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaGuardada);
    }
}