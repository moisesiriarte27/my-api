package com.example.my_API.Controller;

import com.example.my_API.model.lanzamiento;
import com.example.my_API.repository.lanzamientoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LanzamientoController {

    private final lanzamientoRepository lanzamientoRepository;

    public LanzamientoController(lanzamientoRepository lanzamientoRepository) {
        this.lanzamientoRepository = lanzamientoRepository;
    }

    @GetMapping("/lanzamiento")
    public ResponseEntity<List<lanzamiento>> getAllLanzamientos() {
        List<lanzamiento> lanzamientos = lanzamientoRepository.findAllWithRelations();
        return ResponseEntity.ok(lanzamientos);
    }
}
