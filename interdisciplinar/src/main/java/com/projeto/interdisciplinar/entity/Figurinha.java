package com.projeto.interdisciplinar.entity;

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
    @SequenceGenerator(name = "figurinha_seq", sequenceName = "figurinhas_id_figurinha_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "figurinha_seq")
    @Column(name = "id_figurinha")
    private Long idFigurinha;

    @NotNull(message = "O número da figurinha é obrigatório")
    @Column(nullable = false)
    private Integer numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jogador", nullable = false)
    private Jogador jogador;
}
