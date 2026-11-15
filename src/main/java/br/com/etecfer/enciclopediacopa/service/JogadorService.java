package br.com.etecfer.enciclopediacopa.service;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.etecfer.enciclopediacopa.model.Jogador;
import br.com.etecfer.enciclopediacopa.repository.AvaliacaoRepository;
import br.com.etecfer.enciclopediacopa.repository.FavoritoRepository;
import br.com.etecfer.enciclopediacopa.repository.JogadorRepository;

@Service
public class JogadorService {

    private final JogadorRepository jogadorRepository;
    private final FavoritoRepository favoritoRepository;
    private final AvaliacaoRepository avaliacaoRepository;

    public JogadorService(JogadorRepository jogadorRepository,
                          FavoritoRepository favoritoRepository,
                          AvaliacaoRepository avaliacaoRepository) {
        this.jogadorRepository = jogadorRepository;
        this.favoritoRepository = favoritoRepository;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    public List<Jogador> buscar(String termo) {
        if (termo == null || termo.isBlank()) {
            return jogadorRepository.findAllByOrderByNomeAsc();
        }
        String busca = termo.trim();
        return jogadorRepository.findByNomeContainingIgnoreCaseOrSelecaoContainingIgnoreCaseOrderByNomeAsc(
                busca, busca);
    }

    public Jogador buscarPorId(Long id) {
        return jogadorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Jogador nao encontrado."));
    }

    public Jogador salvar(Jogador jogador) {
        if (jogador.getClube() == null || jogador.getClube().isBlank()) {
            jogador.setClube("Nao informado");
        }
        return jogadorRepository.save(jogador);
    }

    @Transactional
    public void excluir(Long id) {
        Jogador jogador = buscarPorId(id);
        avaliacaoRepository.deleteByJogadorId(id);
        favoritoRepository.deleteByJogadorId(id);
        jogadorRepository.delete(jogador);
    }

    public Map<Long, String> rotulosDeFavoritos(List<Jogador> jogadores) {
        Map<Long, String> rotulos = new HashMap<>();
        if (jogadores.isEmpty()) {
            return rotulos;
        }

        var jogadorIds = jogadores.stream().map(Jogador::getId).toList();
        var formato = NumberFormat.getCompactNumberInstance(Locale.forLanguageTag("pt-BR"),
                NumberFormat.Style.SHORT);
        formato.setMaximumFractionDigits(1);

        // Jogadores sem favoritos ficam fora do GROUP BY e recebem zero no template.
        for (Object[] linha : favoritoRepository.contarPorJogador(jogadorIds)) {
            Long jogadorId = (Long) linha[0];
            long total = (Long) linha[1];
            rotulos.put(jogadorId, formato.format(total) + (total == 1 ? " favorito" : " favoritos"));
        }
        return rotulos;
    }
}
