package com.example.my_API.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.my_API.event.RecursoCreadoEvent;
import com.example.my_API.model.Erro;
import com.example.my_API.model.lanzamiento;
import com.example.my_API.repository.lanzamientoRepository;
import com.example.my_API.repository.projection.ResumoLanzamiento; // <-- Importación necesaria
import com.example.my_API.service.lanzamientoService;
import com.example.my_API.service.exception.PersonasInexistenteoinactivo;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/lanzamientos")
public class lanzamientoResource {

    @Autowired
    private lanzamientoRepository lanzamientoRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private lanzamientoService lanzamientoService;

    // --- MODIFICADO ---
    // Este endpoint ahora devuelve la lista de resúmenes enriquecidos con nombres
    @GetMapping
    public List<ResumoLanzamiento> listar() {
        return lanzamientoRepository.findAllResumo();
    }
    
    // GET por código (Devuelve la entidad completa, lo cual es correcto para editar)
    @GetMapping("/{codigo}")
    public ResponseEntity<lanzamiento> buscarPeloCodigo(@PathVariable Long codigo) {
        Optional<lanzamiento> lanzamiento = lanzamientoRepository.findById(codigo);
        return lanzamiento.isPresent() ? ResponseEntity.ok(lanzamiento.get()) : ResponseEntity.notFound().build();
    }

    // POST para crear lanzamiento (Este método ya era correcto)
    @PostMapping
    public ResponseEntity<lanzamiento> crear(@Valid @RequestBody lanzamiento lanzamiento, HttpServletResponse response) {
        lanzamiento lanzamientoguardado = lanzamientoService.guardar(lanzamiento);
        publisher.publishEvent(new RecursoCreadoEvent(this, response, lanzamientoguardado.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lanzamientoguardado);
    }

    // ... (El resto de tus métodos 'remover' y 'handlePersonasInexistenteoinactivo' no cambian) ...
    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long codigo) {
        lanzamientoRepository.deleteById(codigo);
    }

    @ExceptionHandler({ PersonasInexistenteoinactivo.class })
    public ResponseEntity<Object> handlePersonasInexistenteoinactivo(PersonasInexistenteoinactivo ex) {
        String mensagemUsuario = messageSource.getMessage("persona.inexistente", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
        return ResponseEntity.badRequest().body(erros);
    }
}

