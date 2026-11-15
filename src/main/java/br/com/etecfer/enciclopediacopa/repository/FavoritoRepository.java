package br.com.etecfer.enciclopediacopa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.etecfer.enciclopediacopa.model.Favorito;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    boolean existsByUsuarioIdAndJogadorId(Long usuarioId, Long jogadorId);

    @Query("select f from Favorito f join fetch f.jogador where f.usuario.id = :usuarioId order by f.id desc")
    List<Favorito> listarComJogador(@Param("usuarioId") Long usuarioId);

    @Query("""
            select f.jogador.id, count(distinct f.usuario.id)
            from Favorito f
            where f.jogador.id in :jogadorIds
            group by f.jogador.id
            """)
    List<Object[]> contarPorJogador(@Param("jogadorIds") List<Long> jogadorIds);

    void deleteByUsuarioIdAndJogadorId(Long usuarioId, Long jogadorId);

    void deleteByJogadorId(Long jogadorId);
}
