package com.projeto.interdisciplinar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
    @SequenceGenerator(name = "copa_seq", sequenceName = "copas_id_copa_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "copa_seq")
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
