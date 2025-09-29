package com.example.my_API.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.my_API.model.lanzamiento;
import com.example.my_API.model.personas;
import com.example.my_API.repository.lanzamientoRepository;
import com.example.my_API.repository.personasRepository;
import com.example.my_API.service.exception.PersonasInexistenteoinactivo;

import java.util.Optional;

import jakarta.validation.Valid;

@Service
public class lanzamientoService {
	
	@Autowired
	private personasRepository personasRepository;
	
	@Autowired 
	private lanzamientoRepository lanzamientoRepository;
	
	public lanzamiento guardar(@Valid lanzamiento lanzamiento) {
		// --- MODIFICACIÓN AQUÍ ---
		// En lugar de obtener el objeto anidado, usamos el ID directamente desde el lanzamiento.
		Optional<personas> optionalPersona = personasRepository.findById(lanzamiento.getCodigo_personas());
	    
	    personas persona = optionalPersona.orElse(null); 
	    
	    // La validación sigue siendo la misma: la persona debe existir y estar activa.
	    if (persona == null || persona.isInactivo()) {
	    	throw new PersonasInexistenteoinactivo();
	    }
	    
		return lanzamientoRepository.save(lanzamiento);
	}

    // Puedes añadir aquí la lógica para actualizar, que sería muy similar
    /*
    public lanzamiento actualizar(Long codigo, @Valid lanzamiento lanzamiento) {
        // Primero, busca el lanzamiento existente
        lanzamiento lanzamientoExistente = lanzamientoRepository.findById(codigo)
            .orElseThrow(() -> new IllegalArgumentException("Lanzamiento no encontrado"));

        // Valida la persona, igual que en el método guardar
        Optional<personas> optionalPersona = personasRepository.findById(lanzamiento.getCodigo_personas());
        personas persona = optionalPersona.orElse(null); 
        if (persona == null || persona.isInactivo()) {
            throw new PersonasInexistenteoinactivo();
        }

        // Copia las propiedades del lanzamiento nuevo al existente
        // (Puedes usar BeanUtils.copyProperties para simplificar esto)
        lanzamientoExistente.setDescripcion(lanzamiento.getDescripcion());
        lanzamientoExistente.setFechavencimiento(lanzamiento.getFechavencimiento());
        lanzamientoExistente.setFechapago(lanzamiento.getFechapago());
        lanzamientoExistente.setValor(lanzamiento.getValor());
        lanzamientoExistente.setObservacion(lanzamiento.getObservacion());
        lanzamientoExistente.setTipo(lanzamiento.getTipo());
        lanzamientoExistente.setCodigo_categoria(lanzamiento.getCodigo_categoria());
        lanzamientoExistente.setCodigo_personas(lanzamiento.getCodigo_personas());
        
        return lanzamientoRepository.save(lanzamientoExistente);
    }
    */
}