package com.projeto.interdisciplinar.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "figurinha_album")
@Getter
@Setter
public class FigurinhaAlbum {

    @EmbeddedId
    private FigurinhaAlbumId id;

    @MapsId("idAlbum")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_album")
    private Album album;

    @MapsId("idFigurinha")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_figurinha")
    private Figurinha figurinha;

    @Column(nullable = false)
    private Boolean possui = false;

    @Column(nullable = false)
    private Integer quantidade = 0;
}
