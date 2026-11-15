package br.com.etecfer.enciclopediacopa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "favoritos", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_id", "jogador_id"})
})
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favorito_seq_generator")
    @SequenceGenerator(name = "favorito_seq_generator", sequenceName = "favorito_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "jogador_id", nullable = false)
    private Jogador jogador;

    public Favorito() {
    }

    public Favorito(Usuario usuario, Jogador jogador) {
        this.usuario = usuario;
        this.jogador = jogador;
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Jogador getJogador() {
        return jogador;
    }
}
