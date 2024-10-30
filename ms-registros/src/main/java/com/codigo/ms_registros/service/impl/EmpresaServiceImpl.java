package com.codigo.ms_registros.service.impl;

import com.codigo.ms_registros.aggregates.constants.Constants;
import com.codigo.ms_registros.aggregates.response.ResponseSunat;
import com.codigo.ms_registros.client.ClientSunat;
import com.codigo.ms_registros.entity.EmpresaEntity;
import com.codigo.ms_registros.repository.EmpresaRepository;
import com.codigo.ms_registros.service.EmpresaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final ClientSunat clientSunat;

    @Value("${token.api}")
    private String token;

    public EmpresaServiceImpl(EmpresaRepository empresaRepository, ClientSunat clientSunat) {
        this.empresaRepository = empresaRepository;
        this.clientSunat = clientSunat;
    }

    @Override
    public EmpresaEntity guardar(String ruc) {
        //Validar si existe empresa ya registrada
        EmpresaEntity empresa = empresaRepository.findByNumeroDocumento(ruc);

        if(Objects.isNull(empresa)){
            empresa = getEntity(ruc);
            if(Objects.nonNull(empresa)){
                return empresaRepository.save(empresa);
            }
        }
        return null;
    }

    @Override
    public EmpresaEntity obtenerEmpresaPorId(Long id) {
        return empresaRepository.findById(id).orElseThrow(()->new NoSuchElementException("Empresa no encontrada"));
    }

    @Override
    public List<EmpresaEntity> obtenerTodasLasEmpresas() {
        return empresaRepository.findAll();
    }

    @Override
    public EmpresaEntity actualizarEmpresa(Long id, EmpresaEntity empresa) {
        EmpresaEntity empresaExistente = obtenerEmpresaPorId(id);
        empresaExistente.setRazonSocial(empresa.getRazonSocial());
        empresaExistente.setTipoDocumento(empresa.getTipoDocumento());
        empresaExistente.setNumeroDocumento(empresa.getNumeroDocumento());
        empresaExistente.setEstadoSunat(empresa.getEstadoSunat());
        empresaExistente.setCondicion(empresa.getCondicion());
        empresaExistente.setDireccion(empresa.getDireccion());
        empresaExistente.setUbigeo(empresa.getUbigeo());
        empresaExistente.setViaTipo(empresa.getViaTipo());
        empresaExistente.setViaNombre(empresa.getViaNombre());
        empresaExistente.setZonaCodigo(empresa.getZonaCodigo());
        empresaExistente.setZonaTipo(empresa.getZonaTipo());
        empresaExistente.setNumero(empresa.getNumero());
        empresaExistente.setInterior(empresa.getInterior());
        empresaExistente.setLote(empresa.getLote());
        empresaExistente.setDpto(empresa.getDpto());
        empresaExistente.setManzana(empresa.getManzana());
        empresaExistente.setKilometro(empresa.getKilometro());
        empresaExistente.setDistrito(empresa.getDistrito());
        empresaExistente.setProvincia(empresa.getProvincia());
        empresaExistente.setDepartamento(empresa.getDepartamento());
        empresaExistente.setEsAgenteRetencion(empresa.isEsAgenteRetencion());
        empresaExistente.setTipo(empresa.getTipo());
        empresaExistente.setActividadEconomica(empresa.getActividadEconomica());
        empresaExistente.setNumeroTrabajadores(empresa.getNumeroTrabajadores());
        empresaExistente.setTipoFacturacion(empresa.getTipoFacturacion());
        empresaExistente.setTipoContabilidad(empresa.getTipoContabilidad());
        empresaExistente.setComercioExterior(empresa.getComercioExterior());

        //Datos de auditoria
        empresaExistente.setUserUpdated(Constants.USER_UPDATED);
        empresaExistente.setDateUpdated(new Timestamp(System.currentTimeMillis()));

        return empresaRepository.save(empresaExistente);
    }

    @Override
    public void eliminarEmpresa(Long id) {
        EmpresaEntity empresaExistente = obtenerEmpresaPorId(id);

        //Actualizar auditoria para eliminacion
        empresaExistente.setEstado(Constants.ESTADO_INACTIVO);
        empresaExistente.setUserDeleated(Constants.USER_DELETED);
        empresaExistente.setDateDeleated(new Timestamp(System.currentTimeMillis()));

        empresaRepository.save(empresaExistente);
    }

    private EmpresaEntity getEntity(String ruc){
        EmpresaEntity empresaEntity = new EmpresaEntity();
        //Ejecuto api externa
        ResponseSunat datosSunat = exetionSunat(ruc);
        if(Objects.nonNull(datosSunat)){
            //Asignación de datos sunat a entidad
            empresaEntity.setRazonSocial(datosSunat.getRazonSocial());
            empresaEntity.setTipoDocumento(datosSunat.getTipoDocumento());
            empresaEntity.setNumeroDocumento(datosSunat.getNumeroDocumento());
            empresaEntity.setEstadoSunat(datosSunat.getEstado());
            empresaEntity.setCondicion(datosSunat.getCondicion());
            empresaEntity.setDireccion(datosSunat.getDireccion());
            empresaEntity.setUbigeo(datosSunat.getUbigeo());
            empresaEntity.setViaTipo(datosSunat.getViaTipo());
            empresaEntity.setViaNombre(datosSunat.getViaNombre());
            empresaEntity.setZonaCodigo(datosSunat.getZonaCodigo());
            empresaEntity.setZonaTipo(datosSunat.getZonaTipo());
            empresaEntity.setNumero(datosSunat.getNumero());
            empresaEntity.setInterior(datosSunat.getInterior());
            empresaEntity.setLote(datosSunat.getLote());
            empresaEntity.setDpto(datosSunat.getDpto());
            empresaEntity.setManzana(datosSunat.getManzana());
            empresaEntity.setKilometro(datosSunat.getKilometro());
            empresaEntity.setDistrito(datosSunat.getDistrito());
            empresaEntity.setProvincia(datosSunat.getProvincia());
            empresaEntity.setDepartamento(datosSunat.getDepartamento());
            empresaEntity.setEsAgenteRetencion(datosSunat.isEsAgenteRetencion());
            empresaEntity.setTipo(datosSunat.getTipo());
            empresaEntity.setActividadEconomica(datosSunat.getActividadEconomica());
            empresaEntity.setNumeroTrabajadores(datosSunat.getNumeroTrabajadores());
            empresaEntity.setTipoFacturacion(datosSunat.getTipoFacturacion());
            empresaEntity.setTipoContabilidad(datosSunat.getTipoContabilidad());
            empresaEntity.setComercioExterior(datosSunat.getComercioExterior());
            //Datos de auditoria
            empresaEntity.setEstado(Constants.ESTADO_ACTIVO);
            empresaEntity.setUserCreated(Constants.USER_CREATED);
            empresaEntity.setDateCreated(new Timestamp(System.currentTimeMillis()));
        }

        return empresaEntity;
    }

    private ResponseSunat exetionSunat(String ruc){
        String tokenOK = Constants.BEARER + token;
        return clientSunat.getEmpresaSunat(ruc, tokenOK);
    }

}
