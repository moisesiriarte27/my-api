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
		Optional<personas> optionalPersona = personasRepository.findById(lanzamiento.getPersonas().getCodigo());
	    personas personas = optionalPersona.orElse(null); 
	    if (personas==null || personas.isInactivo()) {
	    	throw new PersonasInexistenteoinactivo();
	    }
		return lanzamientoRepository.save(lanzamiento);
	}

}
