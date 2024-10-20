package com.codigo.persistencia.service.impl;

import com.codigo.persistencia.entity.PersonaEntity;
import com.codigo.persistencia.entity.PedidoEntity;
import com.codigo.persistencia.repository.PersonaRepository;
import com.codigo.persistencia.repository.PedidoRepository;
import com.codigo.persistencia.service.PersonaService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PersonaServiceImpl implements PersonaService {

    private final PersonaRepository personaRepository;
    private final PedidoRepository pedidoRepository;

    public PersonaServiceImpl(PersonaRepository personaRepository, PedidoRepository pedidoRepository) {
        this.personaRepository = personaRepository;
        this.pedidoRepository = pedidoRepository;
    }


    @Override
    public PersonaEntity guardarPersona(PersonaEntity persona) {

        //Guardar con estado inicial de Activo y datos de auditoria
        persona.setEstado(PersonaEntity.Estado.ACTIVO);
        persona.setCreated_by("ADMIN");
        persona.setCreated_date(new Timestamp(System.currentTimeMillis()));

        //En caso de haber pedidos, agregar datos de auditoria
        if(persona.getPedidos() != null){
            for(PedidoEntity pedido : persona.getPedidos()){
                pedido.setEstado(PedidoEntity.Estado.PENDIENTE);
                pedido.setCreated_by("ADMIN");
                pedido.setCreated_date(new Timestamp(System.currentTimeMillis()));
            }
        }

        return personaRepository.save(persona);
    }

    @Override
    public List<PersonaEntity> obtenerTodosLosPersonas() {
        return personaRepository.findAllByEstado(PersonaEntity.Estado.ACTIVO);
    }

    public PersonaEntity obtenerPersonaPorId(Long id) {
        //Lanzar excepción por si el id no es correcto
        return personaRepository.findById(id).orElseThrow(()->
                new NoSuchElementException("Error persona no encontrada"));
    }

    @Override
    public PersonaEntity obtenerPersonaPorNumDocumento(String numeroDocumento) {
        return personaRepository.findByNumeroDocumento(numeroDocumento);
    }



    @Override
    public PersonaEntity actualizarPersona(Long id, PersonaEntity personaActual) {
        PersonaEntity personaExistente = obtenerPersonaPorId(id);

        //Actualizar datos de persona
        personaExistente.setNombres(personaActual.getNombres());
        personaExistente.setApellidos(personaActual.getApellidos());
        personaExistente.setNumero_documento(personaActual.getNumero_documento());

        //Actualizar direccion
        personaExistente.setDireccionEntity(personaActual.getDireccionEntity());

        //Gestionando Pedidos
        if(personaActual.getPedidos()!=null){
            gestionarPedidos(personaExistente, personaActual.getPedidos());
        }

        //datos de auditoria
        personaExistente.setUpdate_by("ADMIN");
        personaExistente.setUpdate_date(new Timestamp(System.currentTimeMillis()));

        return personaRepository.save(personaExistente);
    }

    private void gestionarPedidos(PersonaEntity personaExistente, List<PedidoEntity> pedidosActualizados ){
        //Obtener los pedidos actuales de la persona
        List<PedidoEntity> pedidosExistentes = personaExistente.getPedidos();

        //Limpiamos la lista de pedidos para actualizar
        pedidosExistentes.clear();

        //Buscamos los nuevos pedidos
        for(PedidoEntity pedido : pedidosActualizados){
            if(pedido.getId() != null){
                PedidoEntity pedidoEncontrado = pedidoRepository.findById(pedido.getId())
                        .orElseThrow(() -> new NoSuchElementException("Error pedido no ubicad"));

                //Datos actualizados
                pedidoEncontrado.setDescripcion(pedido.getDescripcion());
                pedidoEncontrado.setCantidad(pedido.getCantidad());
                pedidoEncontrado.setEstado(pedido.getEstado());

                //datos de auditoria
                pedidoEncontrado.setUpdate_by("ADMIN");
                pedidoEncontrado.setUpdate_date(new Timestamp(System.currentTimeMillis()));

                pedidosExistentes.add(pedidoEncontrado);
            }else {
                //Significa que no existe y debe ser añadido
                pedido.setPersona(personaExistente);

                //Agregar estado y datos de auditoria
                pedido.setEstado(PedidoEntity.Estado.PENDIENTE);
                pedido.setCreated_by("ADMIN");
                pedido.setCreated_date(new Timestamp(System.currentTimeMillis()));

                pedidosExistentes.add(pedido);
            }
        }
    }

    @Override
    public void eliminarPersona(Long id) {

        PersonaEntity personaExistente = obtenerPersonaPorId(id);

        //Actualizar estado y guardar datos de auditoria
        personaExistente.setEstado(PersonaEntity.Estado.INACTIVO);
        personaExistente.setDelete_by("ADMIN");
        personaExistente.setDelete_date(new Timestamp(System.currentTimeMillis()));

        personaRepository.save(personaExistente);
    }
}
