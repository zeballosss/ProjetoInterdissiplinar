package com.projeto.interdisciplinar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "albums")
@Getter
@Setter
public class Album {

    @Id
    @SequenceGenerator(name = "album_seq", sequenceName = "albums_id_album_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "album_seq")
    @Column(name = "id_album")
    private Long idAlbum;

    @NotBlank(message = "O nome do álbum é obrigatório")
    @Column(nullable = false)
    private String nomeAlbum;

    @NotNull(message = "A data de criação é obrigatória")
    @Column(nullable = false)
    private LocalDate dataCriacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}
