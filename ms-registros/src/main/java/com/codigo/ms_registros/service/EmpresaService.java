package com.codigo.ms_registros.service;

import com.codigo.ms_registros.entity.EmpresaEntity;

import java.util.List;

public interface EmpresaService {
    EmpresaEntity guardar(String ruc);
    EmpresaEntity obtenerEmpresaPorId(Long id);
    List<EmpresaEntity> obtenerTodasLasEmpresas();
    EmpresaEntity actualizarEmpresa(Long id, EmpresaEntity empresa);
    void eliminarEmpresa(Long id);
}
