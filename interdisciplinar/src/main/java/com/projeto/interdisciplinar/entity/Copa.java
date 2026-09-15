package com.projeto.interdisciplinar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "copas")
@Getter
@Setter
public class Copa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_copa")
    private Long idCopa;

    @NotBlank(message = "O nome da copa é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "O ano da copa é obrigatório")
    @Column(nullable = false)
    private Integer ano;

    private String sede;
}
