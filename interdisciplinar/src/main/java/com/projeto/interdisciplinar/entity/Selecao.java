package com.projeto.interdisciplinar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "selecoes")
@Getter
@Setter
public class Selecao {

    @Id
    @SequenceGenerator(name = "selecao_seq", sequenceName = "selecoes_id_selecao_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "selecao_seq")
    @Column(name = "id_selecao")
    private Long idSelecao;

    @NotBlank(message = "O nome da seleção é obrigatório")
    @Column(nullable = false)
    private String nomeSelecao;

    @NotBlank(message = "O país é obrigatório")
    @Column(nullable = false)
    private String pais;

    @NotBlank(message = "O grupo é obrigatório")
    @Column(nullable = false)
    private String grupo;
}
