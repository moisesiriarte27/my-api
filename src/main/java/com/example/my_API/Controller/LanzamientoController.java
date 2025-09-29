package com.example.my_API.Controller;

import com.example.my_API.model.lanzamiento;
import com.example.my_API.repository.lanzamientoRepository; // Puedes mantenerla si la usas en otros métodos, si no, se puede quitar
import com.example.my_API.service.lanzamientoService; // Importamos el servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lanzamientos") // 1. Cambiado a plural, es una convención REST para colecciones
public class LanzamientoController {

    // 2. Inyectamos el servicio en lugar del repositorio directamente
    @Autowired
    private lanzamientoService lanzamientoService;

    @Autowired
    private lanzamientoRepository lanzamientoRepository; // Mantenemos por si lo necesitas para algo más

    // 3. Método para listar todos los lanzamientos (CORREGIDO)
    @GetMapping
    public List<lanzamiento> listar() {
        // Usamos el método findAll() que viene por defecto y es compatible con la nueva entidad
        return lanzamientoRepository.findAll();
    }

    // 4. Método para buscar un lanzamiento por su código
    @GetMapping("/{codigo}")
    public ResponseEntity<lanzamiento> buscarPorCodigo(@PathVariable Long codigo) {
        return lanzamientoRepository.findById(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. Método para crear un nuevo lanzamiento
    @PostMapping
    public ResponseEntity<lanzamiento> crear(@Valid @RequestBody lanzamiento lanzamiento) {
        lanzamiento lanzamientoGuardado = lanzamientoService.guardar(lanzamiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(lanzamientoGuardado);
    }
    
    // 6. Puedes añadir aquí los métodos para actualizar (PUT) y eliminar (DELETE) si los necesitas
    /*
    @PutMapping("/{codigo}")
    public ResponseEntity<lanzamiento> actualizar(@PathVariable Long codigo, @Valid @RequestBody lanzamiento lanzamiento) {
        lanzamiento lanzamientoActualizado = lanzamientoService.actualizar(codigo, lanzamiento);
        return ResponseEntity.ok(lanzamientoActualizado);
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long codigo) {
        lanzamientoRepository.deleteById(codigo);
    }
    */
}