package br.com.etecfer.enciclopediacopa.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.etecfer.enciclopediacopa.model.Avaliacao;
import br.com.etecfer.enciclopediacopa.model.Usuario;
import br.com.etecfer.enciclopediacopa.repository.AvaliacaoRepository;
import br.com.etecfer.enciclopediacopa.repository.FavoritoRepository;
import br.com.etecfer.enciclopediacopa.service.JogadorService;

@Controller
public class JogadorController {

    private final JogadorService jogadorService;
    private final FavoritoRepository favoritoRepository;
    private final AvaliacaoRepository avaliacaoRepository;

    public JogadorController(JogadorService jogadorService,
                             FavoritoRepository favoritoRepository,
                             AvaliacaoRepository avaliacaoRepository) {
        this.jogadorService = jogadorService;
        this.favoritoRepository = favoritoRepository;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    @GetMapping("/jogadores/{id}")
    public String detalhes(@PathVariable Long id,
                           @AuthenticationPrincipal(errorOnInvalidType = false) Usuario usuario,
                           Model model) {
        var jogador = jogadorService.buscarPorId(id);
        model.addAttribute("jogador", jogador);
        model.addAttribute("rotulosFavoritos", jogadorService.rotulosDeFavoritos(List.of(jogador)));

        var avaliacoes = avaliacaoRepository.listarComUsuario(id);
        model.addAttribute("avaliacoes", avaliacoes);
        model.addAttribute("media", avaliacoes.stream().mapToInt(Avaliacao::getNota).average().orElse(0));
        model.addAttribute("favoritado", usuario != null
                && favoritoRepository.existsByUsuarioIdAndJogadorId(usuario.getId(), id));
        if (usuario != null) {
            var minhaNota = avaliacoes.stream()
                    .filter(avaliacao -> avaliacao.getUsuario().getId().equals(usuario.getId()))
                    .map(Avaliacao::getNota)
                    .findFirst().orElse(null);
            model.addAttribute("minhaNota", minhaNota);
        }
        return "jogadores/detalhe";
    }
}
