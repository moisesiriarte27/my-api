package com.example.my_API.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.my_API.event.RecursoCreadoEvent;
import com.example.my_API.model.Erro;
import com.example.my_API.model.lanzamiento;
import com.example.my_API.repository.lanzamientoRepository;
import com.example.my_API.repository.filter.lanzamientoFilter;
import com.example.my_API.service.lanzamientoService;
import com.example.my_API.service.exception.PersonasInexistenteoinactivo;
import com.example.my_API.repository.projection.ResumoLanzamiento;
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

    // GET todos los lanzamientos con categoria y personas
    @GetMapping
    public ResponseEntity<List<lanzamiento>> buscar() {
        List<lanzamiento> lanzamientos = lanzamientoRepository.findAllWithRelations();
        return ResponseEntity.ok(lanzamientos);
    }
    
    @GetMapping(params = "resumo")
    @PreAuthorize("hasAuthority('ROLE_CONSULTAR_LANZAMIENTO')")
    public Page<ResumoLanzamiento> resumir(lanzamientoFilter lanzamientoFilter, Pageable pageable) {
        return lanzamientoRepository.resumir(lanzamientoFilter, pageable);
    }


    // GET por código
    @GetMapping("/{codigo}")
    public ResponseEntity<lanzamiento> buscarPeloCodigo(@PathVariable Long codigo) {
        Optional<lanzamiento> lanzamiento = lanzamientoRepository.findById(codigo);
        return lanzamiento.isPresent() ? ResponseEntity.ok(lanzamiento.get()) : ResponseEntity.notFound().build();
    }

    // POST para crear lanzamiento
    @PostMapping
    public ResponseEntity<lanzamiento> crear(@Valid @RequestBody lanzamiento lanzamiento, HttpServletResponse response) {
        lanzamiento lanzamientoguardado = lanzamientoService.guardar(lanzamiento);
        publisher.publishEvent(new RecursoCreadoEvent(this, response, lanzamientoguardado.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lanzamientoguardado);
    }

    // DELETE por código
    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long codigo) {
        lanzamientoRepository.deleteById(codigo);
    }

    // Manejo de excepción PersonasInexistenteoinactivo
    @ExceptionHandler({ PersonasInexistenteoinactivo.class })
    public ResponseEntity<Object> handlePersonasInexistenteoinactivo(PersonasInexistenteoinactivo ex) {
        String mensagemUsuario = messageSource.getMessage("persona.inexistente", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
        return ResponseEntity.badRequest().body(erros);
    }
}

