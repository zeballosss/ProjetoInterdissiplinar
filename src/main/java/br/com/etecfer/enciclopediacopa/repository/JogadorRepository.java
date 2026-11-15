package br.com.etecfer.enciclopediacopa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.etecfer.enciclopediacopa.model.Jogador;

public interface JogadorRepository extends JpaRepository<Jogador, Long> {

    List<Jogador> findAllByOrderByNomeAsc();

    List<Jogador> findByNomeContainingIgnoreCaseOrSelecaoContainingIgnoreCaseOrderByNomeAsc(
            String nome, String selecao);
}
