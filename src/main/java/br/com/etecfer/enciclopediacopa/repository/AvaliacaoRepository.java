package br.com.etecfer.enciclopediacopa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.etecfer.enciclopediacopa.model.Avaliacao;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    Optional<Avaliacao> findByUsuarioIdAndJogadorId(Long usuarioId, Long jogadorId);

    long countByJogadorId(Long jogadorId);

    List<Avaliacao> findByJogadorIdOrderByAtualizadaEmDesc(Long jogadorId);

    @Query("select avg(a.nota) from Avaliacao a where a.jogador.id = :jogadorId")
    Double calcularMediaDoJogador(@Param("jogadorId") Long jogadorId);

    void deleteByJogadorId(Long jogadorId);
}
