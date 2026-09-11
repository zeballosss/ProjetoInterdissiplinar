package com.projeto.interdisciplinar.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "jogadores")
@Getter
@Setter
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jogador")
    private Long idJogador;

    @NotBlank(message = "O nome do jogador é obrigatório")
    @Column(nullable = false)
    private String nomeJogador;

    @NotNull(message = "O número da camisa é obrigatório")
    @Min(value = 1, message = "O número da camisa deve ser maior que zero")
    @Column(nullable = false)
    private Integer numeroCamisa;

    @NotBlank(message = "A posição é obrigatória")
    @Column(nullable = false)
    private String posicao;

    @Column(nullable = false)
    private Integer gols = 0;

    @Column(nullable = false)
    private Integer assistencias = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_selecao", nullable = false)
    private Selecao selecao;
}
