package com.codigo.persistencia.repository;

import com.codigo.persistencia.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {
    List<PedidoEntity> findAllByEstado(PedidoEntity.Estado estado);
}
