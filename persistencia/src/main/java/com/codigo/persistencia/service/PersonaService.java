package com.codigo.persistencia.service;

import com.codigo.persistencia.entity.PersonaEntity;

import java.util.List;

public interface PersonaService {
    PersonaEntity guardarPersona(PersonaEntity persona);
    PersonaEntity obtenerPersonaPorNumDocumento(String numeroDocumento);
    List<PersonaEntity> obtenerTodosLosPersonas();
    PersonaEntity actualizarPersona(Long id, PersonaEntity persona);
    void eliminarPersona(Long id);
}
