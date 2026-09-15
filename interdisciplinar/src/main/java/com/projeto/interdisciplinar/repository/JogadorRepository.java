package com.projeto.interdisciplinar.repository;

import com.projeto.interdisciplinar.entity.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JogadorRepository extends JpaRepository<Jogador, Long> {

	List<Jogador> findBySelecaoIdSelecaoOrderByGolsDescNomeJogadorAsc(Long idSelecao);
}
