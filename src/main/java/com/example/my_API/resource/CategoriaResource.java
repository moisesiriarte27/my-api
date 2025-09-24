package com.example.my_API.resource;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.example.my_API.MyApiApplication;
import com.example.my_API.event.RecursoCreadoEvent;
import com.example.my_API.model.Categoria;
import com.example.my_API.repository.CategoriaRepository;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    private final MyApiApplication myApiApplication;
    
    private final CategoriaRepository categoriaRepository;
    @Autowired
    private ApplicationEventPublisher publisher;

    public CategoriaResource(CategoriaRepository categoriaRepository, MyApiApplication myApiApplication) {
        this.categoriaRepository = categoriaRepository;
        this.myApiApplication = myApiApplication;
    }

    
    @GetMapping
    public ResponseEntity<?> listar(){
    	List<Categoria> categorias= categoriaRepository.findAll();
        return !categorias.isEmpty() ? ResponseEntity.ok(categorias): ResponseEntity.noContent().build();
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Categoria> crear(@RequestBody Categoria categoria, HttpServletResponse response) {
    	Categoria categoriaguardada=categoriaRepository.save(categoria);
    	
    	publisher.publishEvent(new RecursoCreadoEvent(this,response,categoriaguardada.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaguardada);
    }
    @GetMapping("/{codigo}")
    public Categoria buscarporcodigo(@PathVariable Long codigo) {
    	return categoriaRepository.findById(codigo).orElse(null);
    }
    
}
