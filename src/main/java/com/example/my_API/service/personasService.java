package com.example.my_API.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.my_API.model.personas;
import com.example.my_API.repository.personasRepository;

@Service
public class personasService {

    @Autowired
    private personasRepository personaRepository;

    public void actualizarpropiedadactivo(Long codigo, Boolean activo) {
        personas personaGuardada = buscarPersonaPorCodigo(codigo);
        personaGuardada.setActivo(activo);
        personaRepository.save(personaGuardada);
    }

    public personas buscarPersonaPorCodigo(Long codigo) {
        return personaRepository.findById(codigo)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }
    
}
