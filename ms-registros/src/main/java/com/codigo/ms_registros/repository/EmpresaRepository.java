package com.codigo.ms_registros.repository;

import com.codigo.ms_registros.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<EmpresaEntity,Long> {
    EmpresaEntity findByNumeroDocumento(String numeroDocumento);
}
