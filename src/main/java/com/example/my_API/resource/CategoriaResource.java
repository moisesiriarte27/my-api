package com.example.my_API.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping; // <-- Añadido
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;   // <-- Añadido
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.my_API.MyApiApplication;
import com.example.my_API.event.RecursoCreadoEvent;
import com.example.my_API.model.Categoria;
import com.example.my_API.repository.CategoriaRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid; // <-- Añadido para validaciones

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private ApplicationEventPublisher publisher;
    
    // Inyectar MyApiApplication no es usualmente necesario en un controlador,
    // puedes considerar si realmente lo necesitas. Por ahora lo dejamos.
    private final MyApiApplication myApiApplication;
    public CategoriaResource(CategoriaRepository categoriaRepository, MyApiApplication myApiApplication) {
        this.categoriaRepository = categoriaRepository;
        this.myApiApplication = myApiApplication;
    }

    @GetMapping
    public ResponseEntity<?> listar(){
        List<Categoria> categorias = categoriaRepository.findAll();
        return !categorias.isEmpty() ? ResponseEntity.ok(categorias) : ResponseEntity.noContent().build();
    }
    
    @PostMapping
    public ResponseEntity<Categoria> crear(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        publisher.publishEvent(new RecursoCreadoEvent(this, response, categoriaGuardada.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaGuardada);
    }
    
    @GetMapping("/{codigo}")
    public ResponseEntity<Categoria> buscarPorCodigo(@PathVariable Long codigo) {
        Optional<Categoria> categoria = categoriaRepository.findById(codigo);
        // Es mejor devolver un 404 Not Found si no se encuentra
        return categoria.isPresent() ? ResponseEntity.ok(categoria.get()) : ResponseEntity.notFound().build();
    }
    
    // --- MÉTODO PARA ELIMINAR AÑADIDO ---
    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Devuelve 204 No Content si se elimina correctamente
    public void eliminar(@PathVariable Long codigo) {
        categoriaRepository.deleteById(codigo);
    }

    // --- MÉTODO PARA ACTUALIZAR AÑADIDO ---
    @PutMapping("/{codigo}")
    public ResponseEntity<Categoria> actualizar(@PathVariable Long codigo, @Valid @RequestBody Categoria categoria) {
        // Lógica para actualizar: busca la categoría, actualiza sus datos y guarda.
        // Aquí una versión simple:
        if (!categoriaRepository.existsById(codigo)) {
            return ResponseEntity.notFound().build();
        }
        categoria.setCodigo(codigo); // Aseguramos que el código sea el del path
        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        return ResponseEntity.ok(categoriaGuardada);
    }
}