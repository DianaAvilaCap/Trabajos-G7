package com.codigo.persistencia.repository;

import com.codigo.persistencia.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonaRepository extends JpaRepository<PersonaEntity,Long> {
    List<PersonaEntity> findAllByEstado(PersonaEntity.Estado estado);
    @Query("SELECT P FROM PersonaEntity P WHERE P.numero_documento=:numDocumento")
    PersonaEntity findByNumeroDocumento(@Param("numDocumento") String numDocumento);
}
