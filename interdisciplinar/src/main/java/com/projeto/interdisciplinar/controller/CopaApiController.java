package com.projeto.interdisciplinar.controller;

import com.projeto.interdisciplinar.entity.Copa;
import com.projeto.interdisciplinar.entity.Jogador;
import com.projeto.interdisciplinar.entity.Selecao;
import com.projeto.interdisciplinar.repository.CopaRepository;
import com.projeto.interdisciplinar.repository.JogadorRepository;
import com.projeto.interdisciplinar.repository.SelecaoRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CopaApiController {

    private final CopaRepository copaRepository;
    private final SelecaoRepository selecaoRepository;
    private final JogadorRepository jogadorRepository;

    public CopaApiController(CopaRepository copaRepository, SelecaoRepository selecaoRepository,
                             JogadorRepository jogadorRepository) {
        this.copaRepository = copaRepository;
        this.selecaoRepository = selecaoRepository;
        this.jogadorRepository = jogadorRepository;
    }

    @GetMapping("/copas")
    public List<Copa> listarCopas() {
        return copaRepository.findAll();
    }

    @PostMapping("/copas")
    public Copa criarCopa(@Valid @RequestBody Copa copa) {
        return copaRepository.save(copa);
    }

    @GetMapping("/copas/{id}")
    public ResponseEntity<Copa> buscarCopa(@PathVariable Long id) {
        return copaRepository.findById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

        @GetMapping("/copas/{id}/detalhes")
        @Transactional(readOnly = true)
        public ResponseEntity<CopaDetalhesResponse> detalhesDaCopa(@PathVariable Long id) {
        return copaRepository.findById(id)
            .map(copa -> {
                List<SelecaoDetalhesResponse> selecoes = copa.getSelecoes().stream()
                    .map(selecao -> new SelecaoDetalhesResponse(
                        selecao.getIdSelecao(), selecao.getNomeSelecao(), selecao.getPais(),
                        selecao.getGrupo(), jogadorRepository
                            .findBySelecaoIdSelecaoOrderByGolsDescNomeJogadorAsc(selecao.getIdSelecao())
                            .stream()
                            .map(jogador -> new JogadorResumoResponse(
                                jogador.getIdJogador(), jogador.getNomeJogador(),
                                jogador.getNumeroCamisa(), jogador.getPosicao(),
                                jogador.getGols(), jogador.getAssistencias()))
                            .toList()))
                    .toList();
                return ResponseEntity.ok(new CopaDetalhesResponse(
                    copa.getIdCopa(), copa.getNome(), copa.getAno(), copa.getSede(), selecoes));
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
        }

        @PostMapping("/copas/{copaId}/selecoes/{selecaoId}")
        @Transactional
        public ResponseEntity<Void> associarSelecao(@PathVariable Long copaId, @PathVariable Long selecaoId) {
        var copa = copaRepository.findById(copaId);
        var selecao = selecaoRepository.findById(selecaoId);
        if (copa.isEmpty() || selecao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        copa.get().getSelecoes().add(selecao.get());
        copaRepository.save(copa.get());
        return ResponseEntity.noContent().build();
        }

    @GetMapping("/selecoes")
    public List<Selecao> listarSelecoes() {
        return selecaoRepository.findAll();
    }

    @PostMapping("/selecoes")
    public Selecao criarSelecao(@Valid @RequestBody Selecao selecao) {
        return selecaoRepository.save(selecao);
    }

    @GetMapping("/jogadores")
    public List<Jogador> listarJogadores() {
        return jogadorRepository.findAll();
    }

    @PostMapping("/jogadores")
    public ResponseEntity<Jogador> criarJogador(@Valid @RequestBody Jogador jogador) {
        if (jogador.getSelecao() == null || jogador.getSelecao().getIdSelecao() == null
                || !selecaoRepository.existsById(jogador.getSelecao().getIdSelecao())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(jogadorRepository.save(jogador));
    }

    @GetMapping("/jogadores/{id}")
    public ResponseEntity<Jogador> buscarJogador(@PathVariable Long id) {
        return jogadorRepository.findById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/jogadores/{id}")
    public ResponseEntity<Jogador> atualizarJogador(@PathVariable Long id,
                                                      @Valid @RequestBody Jogador dados) {
        return jogadorRepository.findById(id).map(jogador -> {
            jogador.setNomeJogador(dados.getNomeJogador());
            jogador.setNumeroCamisa(dados.getNumeroCamisa());
            jogador.setPosicao(dados.getPosicao());
            jogador.setGols(dados.getGols());
            jogador.setAssistencias(dados.getAssistencias());
            if (dados.getSelecao() != null && dados.getSelecao().getIdSelecao() != null
                    && selecaoRepository.existsById(dados.getSelecao().getIdSelecao())) {
                jogador.setSelecao(dados.getSelecao());
            }
            return ResponseEntity.ok(jogadorRepository.save(jogador));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/jogadores/{id}")
    public ResponseEntity<Void> excluirJogador(@PathVariable Long id) {
        if (!jogadorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        jogadorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public record CopaDetalhesResponse(Long idCopa, String nome, Integer ano, String sede,
                                       List<SelecaoDetalhesResponse> selecoes) {
    }

    public record SelecaoDetalhesResponse(Long idSelecao, String nome, String pais, String grupo,
                                          List<JogadorResumoResponse> jogadores) {
    }

    public record JogadorResumoResponse(Long idJogador, String nome, Integer numeroCamisa,
                                        String posicao, Integer gols, Integer assistencias) {
    }
}
