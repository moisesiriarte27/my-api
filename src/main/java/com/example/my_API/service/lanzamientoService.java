package com.example.my_API.service;

import org.springframework.beans.BeanUtils;
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
		validarPersona(lanzamiento);
		return lanzamientoRepository.save(lanzamiento);
	}

    public lanzamiento actualizar(Long codigo, @Valid lanzamiento lanzamiento) {
        lanzamiento lanzamientoGuardado = buscarLanzamientoExistente(codigo);
        validarPersona(lanzamiento);
        BeanUtils.copyProperties(lanzamiento, lanzamientoGuardado, "codigo");
        return lanzamientoRepository.save(lanzamientoGuardado);
    }

    private void validarPersona(lanzamiento lanzamiento) {
        Optional<personas> optionalPersona = personasRepository.findById(lanzamiento.getCodigo_personas());
        personas persona = optionalPersona.orElse(null); 
	    if (persona == null || persona.isInactivo()) {
	    	throw new PersonasInexistenteoinactivo();
	    }
    }

    private lanzamiento buscarLanzamientoExistente(Long codigo) {
        return lanzamientoRepository.findById(codigo)
            .orElseThrow(() -> new IllegalArgumentException("Lanzamiento no encontrado"));
    }
}