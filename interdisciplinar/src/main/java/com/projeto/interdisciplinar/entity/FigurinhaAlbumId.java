package com.projeto.interdisciplinar.model;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class FigurinhaAlbumId implements Serializable {

    private Long idAlbum;
    private Long idFigurinha;
}
