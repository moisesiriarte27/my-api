package com.example.my_API.resource;

import java.awt.List;
import java.net.URI;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.my_API.event.RecursoCreadoEvent;
import com.example.my_API.model.personas;
import com.example.my_API.repository.personasRepository;
import com.example.my_API.service.personasService;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/personas")
public class personasResource {

    // CORRECCIÓN: Se agrega @Autowired para inyectar la dependencia correctamente
    @Autowired
    private personasService personasService;

    @Autowired
    private personasRepository personasRepository;
    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping
    public ResponseEntity<personas> crear(@Validated @RequestBody personas personas, HttpServletResponse response){
    	personas personaguardada=personasRepository.save(personas);
    	
    	publisher.publishEvent(new RecursoCreadoEvent(this,response,personaguardada.getCodigo()));
    
    	  return ResponseEntity.status(HttpStatus.CREATED).body(personaguardada);
    }
    
    @GetMapping("/{codigo}")
    public ResponseEntity<personas> buscarporcodigo(@PathVariable Long codigo){
    	Optional<personas> personas=personasRepository.findById(codigo);
    	return personas.isPresent()? ResponseEntity.ok(personas.get()): ResponseEntity.notFound().build();
    }
    
    @GetMapping
    public ResponseEntity<Iterable<personas>> listarTodas() {
        Iterable<personas> lista = personasRepository.findAll();
        return ResponseEntity.ok(lista);
    }
    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long codigo) {
    	personasRepository.deleteById(codigo);
    }
    
    @PutMapping({"/{codigo}"})
    public ResponseEntity<personas> actualizar(@PathVariable Long codigo,@Validated @RequestBody personas personas){
    	personas personaguardada=personasRepository.findById(codigo)
    			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no encontrada con ID: " + codigo));
    	BeanUtils.copyProperties(personas, personaguardada,"codigo");
    	personasRepository.save(personaguardada);
    	return ResponseEntity.ok(personaguardada);
    }
    @PutMapping("/{codigo}/activo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void actualizarpropiedadactivo(@PathVariable Long codigo, @RequestBody Boolean activo){
    	personasService.actualizarpropiedadactivo(codigo,activo);
    }
    
    
}
