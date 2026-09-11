package com.projeto.interdisciplinar.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "figurinhas")
@Getter
@Setter
public class Figurinha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_figurinha")
    private Long idFigurinha;

    @NotNull(message = "O número da figurinha é obrigatório")
    @Column(nullable = false)
    private Integer numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jogador", nullable = false)
    private Jogador jogador;
}
