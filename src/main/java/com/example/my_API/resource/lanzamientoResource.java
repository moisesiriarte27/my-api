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
import com.example.my_API.service.lanzamientoService;
import com.example.my_API.service.exception.PersonasInexistenteoinactivo;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

// Nota: He quitado las importaciones de Page, Pageable, etc., que ya no se usan.

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

    // --- MODIFICACIÓN #1 ---
    // Corregido para usar el método findAll() que es compatible con la nueva entidad.
    // Renombrado de 'buscar' a 'listar' por convención.
    @GetMapping
    public List<lanzamiento> listar() {
        return lanzamientoRepository.findAll();
    }
    
    // --- MODIFICACIÓN #2 ---
    // El método 'resumir' ha sido comentado porque su consulta personalizada en el repositorio
    // seguramente se rompió al eliminar las relaciones. Para arreglarlo, necesitaríamos
    // ver y corregir la consulta en 'lanzamientoRepository.java'.
    /*
    @GetMapping(params = "resumo")
    @PreAuthorize("hasAuthority('ROLE_CONSULTAR_LANZAMIENTO')")
    public Page<ResumoLanzamiento> resumir(lanzamientoFilter lanzamientoFilter, Pageable pageable) {
        return lanzamientoRepository.resumir(lanzamientoFilter, pageable);
    }
    */

    // GET por código (Este método ya era correcto)
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

    // DELETE por código (Este método ya era correcto)
    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long codigo) {
        lanzamientoRepository.deleteById(codigo);
    }

    // Manejo de excepción (Este método ya era correcto)
    @ExceptionHandler({ PersonasInexistenteoinactivo.class })
    public ResponseEntity<Object> handlePersonasInexistenteoinactivo(PersonasInexistenteoinactivo ex) {
        String mensagemUsuario = messageSource.getMessage("persona.inexistente", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
        return ResponseEntity.badRequest().body(erros);
    }
}

