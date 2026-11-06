package br.com.etecfer.enciclopediacopa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.etecfer.enciclopediacopa.model.Favorito;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    boolean existsByUsuarioIdAndJogadorId(Long usuarioId, Long jogadorId);

    List<Favorito> findByUsuarioIdOrderByIdDesc(Long usuarioId);

    void deleteByUsuarioIdAndJogadorId(Long usuarioId, Long jogadorId);

    void deleteByJogadorId(Long jogadorId);
}
