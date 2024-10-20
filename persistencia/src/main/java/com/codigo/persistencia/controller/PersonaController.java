package com.codigo.persistencia.controller;

import com.codigo.persistencia.entity.PersonaEntity;
import com.codigo.persistencia.service.PersonaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personas/v1")
public class PersonaController {
    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @PostMapping("/crearPersona")
    public ResponseEntity<PersonaEntity> crearPersona(@RequestBody PersonaEntity persona) {
        PersonaEntity nuevoPersona = personaService.guardarPersona(persona);
        return new ResponseEntity<>(nuevoPersona, HttpStatus.CREATED);
    }

    @GetMapping("/buscarTodos")
    public ResponseEntity<List<PersonaEntity>> obtenerTodosLosPersonas() {
        return new ResponseEntity<>(personaService.obtenerTodosLosPersonas(), HttpStatus.OK);
    }

    @GetMapping("/buscarPersonaXnumDocumento")
    public ResponseEntity<PersonaEntity> obtenerXNumDocumento(@RequestParam String numeroDocumento) {
        return new ResponseEntity<>(personaService.obtenerPersonaPorNumDocumento(numeroDocumento), HttpStatus.OK);
    }

    @PutMapping("/actualizarPersona/{id}")
    public ResponseEntity<PersonaEntity> actualizarPersona(@PathVariable Long id, @RequestBody PersonaEntity persona) {
        PersonaEntity personaActualizado = personaService.actualizarPersona(id, persona);
        return new ResponseEntity<>(personaActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/eliminarPersona/{id}")
    public ResponseEntity<Void> eliminarPersona(@PathVariable Long id) {
        personaService.eliminarPersona(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
