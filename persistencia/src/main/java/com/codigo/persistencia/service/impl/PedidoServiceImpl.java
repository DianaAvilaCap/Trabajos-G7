package com.codigo.persistencia.service.impl;

import com.codigo.persistencia.entity.PersonaEntity;
import com.codigo.persistencia.entity.PedidoEntity;
import com.codigo.persistencia.repository.PersonaRepository;
import com.codigo.persistencia.repository.PedidoRepository;
import com.codigo.persistencia.service.PedidoService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PersonaRepository personaRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository, PersonaRepository personaRepository) {
        this.pedidoRepository = pedidoRepository;
        this.personaRepository = personaRepository;
    }

    @Override
    public PedidoEntity guardarPedido(Long personaId, PedidoEntity pedido) {
        PersonaEntity personaExistente = personaRepository.findById(personaId)
                .orElseThrow(() -> new NoSuchElementException("Error Persona no existe"));

        //Asignar estado iniciar y guardar datos de auditoria
        pedido.setEstado(PedidoEntity.Estado.PENDIENTE);
        pedido.setCreated_by("ADMIN");
        pedido.setCreated_date(new Timestamp(System.currentTimeMillis()));

        return pedidoRepository.save(pedido);
    }

    @Override
    public List<PedidoEntity> guardarPedidos(Long personaId, List<PedidoEntity> pedidos) {
        PersonaEntity personaExistente = personaRepository.findById(personaId)
                .orElseThrow(() -> new NoSuchElementException("Error Persona no existe"));

        for(PedidoEntity pedido : pedidos){
            //Enlazar el cliente a los pedidos
            pedido.setPersona(personaExistente);

            //Asignar estado iniciar y guardar datos de auditoria
            pedido.setEstado(PedidoEntity.Estado.PENDIENTE);
            pedido.setCreated_by("ADMIN");
            pedido.setCreated_date(new Timestamp(System.currentTimeMillis()));
        }

        return pedidoRepository.saveAll(pedidos);
    }

    @Override
    public PedidoEntity obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("Pedido no encontrado"));
    }

    @Override
    public List<PedidoEntity> obtenerTodosLosPedidos(PedidoEntity.Estado estado) {
        return pedidoRepository.findAllByEstado(estado);
    }

    @Override
    public PedidoEntity actualizarPedido(Long id, Long idPersona, PedidoEntity pedido) {
        PedidoEntity pedidoExistente = obtenerPedidoPorId(id);
        PersonaEntity personaExistente = personaRepository.findById(idPersona)
                .orElseThrow(()-> new NoSuchElementException("No se encontró persona"));

        pedidoExistente.setDescripcion(pedido.getDescripcion());
        pedidoExistente.setCantidad(pedido.getCantidad());
        pedidoExistente.setEstado(pedido.getEstado());
        pedidoExistente.setPersona(personaExistente);

        //Actualizar datos de auditoria
        pedidoExistente.setUpdate_by("ADMIN");
        pedidoExistente.setUpdate_date(new Timestamp(System.currentTimeMillis()));

        return pedidoRepository.save(pedidoExistente);
    }

    @Override
    public void eliminarPedido(Long id) {
        PedidoEntity pedidoExistente = obtenerPedidoPorId(id);

        //Actualizar estado y guardar datos de auditoria
        pedidoExistente.setEstado(PedidoEntity.Estado.ELIMINADO);
        pedidoExistente.setDelete_by("ADMIN");
        pedidoExistente.setDelete_date(new Timestamp(System.currentTimeMillis()));

        pedidoRepository.save(pedidoExistente);
    }
}
