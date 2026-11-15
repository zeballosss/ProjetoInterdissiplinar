package br.com.etecfer.enciclopediacopa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.etecfer.enciclopediacopa.model.Avaliacao;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    Optional<Avaliacao> findByUsuarioIdAndJogadorId(Long usuarioId, Long jogadorId);

    @Query("""
            select a from Avaliacao a join fetch a.usuario
            where a.jogador.id = :jogadorId
            order by a.atualizadaEm desc
            """)
    List<Avaliacao> listarComUsuario(@Param("jogadorId") Long jogadorId);

    void deleteByJogadorId(Long jogadorId);
}
