package com.codigo.persistencia.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "direccion")
@Getter
@Setter
public class DireccionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "avenida", nullable = false)
    private String avenida;

    @Column(name = "numero", nullable = false)
    private Integer numero;

    @Column(name = "distrito", nullable = false)
    private String distrito;

    @Column(name = "provincia", nullable = false)
    private String provincia;

    @Column(name = "departamento", nullable = false)
    private String departamento;
}
