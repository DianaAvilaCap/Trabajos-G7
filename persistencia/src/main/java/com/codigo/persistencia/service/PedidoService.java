package com.codigo.persistencia.service;

import com.codigo.persistencia.entity.PedidoEntity;

import java.util.List;

public interface PedidoService {
    PedidoEntity guardarPedido(Long clienteId, PedidoEntity pedido);
    List<PedidoEntity> guardarPedidos(Long clienteId, List<PedidoEntity> pedidos);
    PedidoEntity obtenerPedidoPorId(Long id);
    List<PedidoEntity> obtenerTodosLosPedidos(PedidoEntity.Estado estado);
    PedidoEntity actualizarPedido(Long id, Long idCliente ,PedidoEntity pedido);
    void eliminarPedido(Long id);
}