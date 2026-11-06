package br.com.etecfer.enciclopediacopa.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.etecfer.enciclopediacopa.model.Avaliacao;
import br.com.etecfer.enciclopediacopa.model.Favorito;
import br.com.etecfer.enciclopediacopa.model.Jogador;
import br.com.etecfer.enciclopediacopa.model.Usuario;
import br.com.etecfer.enciclopediacopa.repository.AvaliacaoRepository;
import br.com.etecfer.enciclopediacopa.repository.FavoritoRepository;
import br.com.etecfer.enciclopediacopa.repository.JogadorRepository;
import br.com.etecfer.enciclopediacopa.repository.UsuarioRepository;

@Service
public class InteracaoService {

    private final FavoritoRepository favoritoRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final JogadorRepository jogadorRepository;
    private final UsuarioRepository usuarioRepository;

    public InteracaoService(FavoritoRepository favoritoRepository,
                            AvaliacaoRepository avaliacaoRepository,
                            JogadorRepository jogadorRepository,
                            UsuarioRepository usuarioRepository) {
        this.favoritoRepository = favoritoRepository;
        this.avaliacaoRepository = avaliacaoRepository;
        this.jogadorRepository = jogadorRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public boolean alternarFavorito(Long usuarioId, Long jogadorId) {
        Usuario usuario = buscarUsuario(usuarioId);
        Jogador jogador = buscarJogador(jogadorId);

        if (favoritoRepository.existsByUsuarioIdAndJogadorId(usuarioId, jogadorId)) {
            favoritoRepository.deleteByUsuarioIdAndJogadorId(usuarioId, jogadorId);
            return false;
        }

        favoritoRepository.save(new Favorito(usuario, jogador));
        return true;
    }

    @Transactional
    public void avaliar(Long usuarioId, Long jogadorId, Integer nota, String comentario) {
        if (nota == null || nota < 1 || nota > 5) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 5.");
        }
        if (comentario != null && comentario.length() > 500) {
            throw new IllegalArgumentException("O comentario deve ter no maximo 500 caracteres.");
        }

        Usuario usuario = buscarUsuario(usuarioId);
        Jogador jogador = buscarJogador(jogadorId);
        Avaliacao avaliacao = avaliacaoRepository.findByUsuarioIdAndJogadorId(usuarioId, jogadorId)
                .orElseGet(() -> new Avaliacao(usuario, jogador, nota, comentario));

        avaliacao.setNota(nota);
        avaliacao.setComentario(comentario == null || comentario.isBlank() ? null : comentario.trim());
        avaliacaoRepository.save(avaliacao);
    }

    private Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado."));
    }

    private Jogador buscarJogador(Long id) {
        return jogadorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Jogador nao encontrado."));
    }
}
