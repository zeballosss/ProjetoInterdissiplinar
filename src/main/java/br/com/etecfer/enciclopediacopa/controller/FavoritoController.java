package br.com.etecfer.enciclopediacopa.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.etecfer.enciclopediacopa.model.Favorito;
import br.com.etecfer.enciclopediacopa.model.Usuario;
import br.com.etecfer.enciclopediacopa.repository.FavoritoRepository;
import br.com.etecfer.enciclopediacopa.service.JogadorService;

@Controller
public class FavoritoController {

    private final FavoritoRepository favoritoRepository;

    private final JogadorService jogadorService;

    public FavoritoController(FavoritoRepository favoritoRepository, JogadorService jogadorService) {
        this.favoritoRepository = favoritoRepository;
        this.jogadorService = jogadorService;
    }

    @GetMapping("/favoritos")
    public String listar(@AuthenticationPrincipal Usuario usuario, Model model) {
        var jogadores = favoritoRepository.listarComJogador(usuario.getId()).stream()
                .map(Favorito::getJogador).distinct().toList();
        model.addAttribute("jogadores", jogadores);
        model.addAttribute("rotulosFavoritos", jogadorService.rotulosDeFavoritos(jogadores));
        return "favoritos/lista";
    }
}
